package br.gov.ce.pge.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.gov.ce.pge.gestao.constants.MessageTermoCondicaoConstant;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dao.TermoCondicaoDao;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDto;
import br.gov.ce.pge.gestao.dto.response.UsuarioTermoResponseDto;
import br.gov.ce.pge.gestao.entity.TermoCondicao;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoTermoCondicaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.TermoCondicaoRepository;
import br.gov.ce.pge.gestao.service.TermoCondicaoService;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
public class TermoCondicaoServiceImpl implements TermoCondicaoService {

	private final TermoCondicaoRepository repository;

	private final TermoCondicaoDao dao;
	
	public TermoCondicaoServiceImpl(TermoCondicaoRepository repository, 
			TermoCondicaoDao dao) {
		this.repository = repository;
		this.dao = dao;
	}

	@Override
	public void updateExistente(UUID id, TermoCondicaoRequestDto request) {
		var termoModel = this.findByIdModel(id);
		BeanUtils.copyProperties(request, termoModel);

		this.repository.save(termoModel);
	}

	@Override
	public void update(UUID id, @Valid TermoCondicaoRequestDto request) {

		var termoModel = this.findByIdModel(id);

		verificaVersaoVigente(termoModel.getId());

		var novoTermo = new TermoCondicao();
		novoTermo.setSistema(termoModel.getSistema());
		novoTermo.setConteudo(request.getConteudo());
		novoTermo.setVersao(updateVersion(termoModel.getVersao()));

		termoModel.getUsuarios().stream().forEach(usuario -> {
			if(usuario.getTermos() != null) {
				var termos = usuario.getTermos().stream().filter(termo -> termoModel.getId().equals(termo.getId())).collect(Collectors.toList());
				if(!termos.isEmpty()) {
					usuario.getTermos().remove(termos.get(0));
					usuario.getTermos().add(novoTermo);
				}
			}
		});

		this.repository.save(novoTermo);

		if(termoModel.getSistema().getNome().equals(MessageTermoCondicaoConstant.PORTAL_DIVIDA_ATIVA)) {
			this.repository.updateDataAceitePortalDivida(termoModel.getSistema().getId());
		}else {
			this.repository.updateDataAceitePortalOrigens(termoModel.getSistema().getId());
		}

	}

	private void verificaVersaoVigente(UUID id) {
		var total = this.repository.countByIdTermoCondicao(id);

		if(total > 1) {
			throw new NegocioException(MessageTermoCondicaoConstant.ALTERACAO_NAO_PERMITIDA);
		}
	}

	private String updateVersion(String versao) {
		String[] versionamento = versao.split(MessageTermoCondicaoConstant.REGEX_SEPARATOR);

		int versaoPrincipal = Integer.parseInt(versionamento[0]);
		int versaoSecundaria = Integer.parseInt(versionamento[1]);

		if(versaoSecundaria < MessageTermoCondicaoConstant.VERSAO_SECUNDARIA_MAXIMA) {
			versaoSecundaria++;
		} else {
			versaoPrincipal++;
			versaoSecundaria = MessageTermoCondicaoConstant.VERSAO_SECUNDARIA_INICIAL;
		}

		return String.format(MessageTermoCondicaoConstant.REGEX_VERSAO_TERMO, versaoPrincipal, versaoSecundaria);
	}

	@Override
	public TermoCondicao findByIdModel(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(MessageTermoCondicaoConstant.NAO_ENCONTRADO));
	}

	@Override
	public List<TermoCondicaoSistemaResponseDto> findBySistema() {

		var termos = this.findByNomeSistema();

		var lista = new ArrayList<TermoCondicaoSistemaResponseDto>();

		termos.stream().forEach(termo -> {
			var dto = new TermoCondicaoSistemaResponseDto();
			dto.setId(termo.getId().toString());
			dto.setDataCriacao(termo.getDataCriacao());
			dto.setNomeSistema(termo.getSistema().getNome());
			dto.setVersao(termo.getVersao());

			dto = termo.getSistema().getNome().equals(MessageTermoCondicaoConstant.PORTAL_DIVIDA_ATIVA) ? setUsuariosPendentesPortalDivida(termo, dto) : setUsuariosPendentesPortalOrigens(termo, dto);

			dto.setDataAtualizacao(termo.getDataAtualizacao());

			lista.add(dto);
		});

		return lista;
	}

	private TermoCondicaoSistemaResponseDto setUsuariosPendentesPortalOrigens(TermoCondicao termo, TermoCondicaoSistemaResponseDto dto) {
		dto.setQtdPendente(getUsuariosPendentesPortalOrigens(termo.getUsuarios()).size());
		dto.setQtdAceite(getUsuariosAceitesPortalOrigens(termo.getUsuarios()).size());
		dto.setUsuariosPendentes(getUsuariosPendentesPortalOrigens(termo.getUsuarios()));
		dto.setUsuariosAceites(getUsuariosAceitesPortalOrigens(termo.getUsuarios()));

		return dto;
	}

	private TermoCondicaoSistemaResponseDto setUsuariosPendentesPortalDivida(TermoCondicao termo, TermoCondicaoSistemaResponseDto dto) {
		dto.setQtdPendente(getUsuariosPendentesPortalDivida(termo.getUsuarios()).size());
		dto.setQtdAceite(getUsuariosAceitesPortalDivida(termo.getUsuarios()).size());
		dto.setUsuariosPendentes(getUsuariosPendentesPortalDivida(termo.getUsuarios()));
		dto.setUsuariosAceites(getUsuariosAceitesPortalDivida(termo.getUsuarios()));

		return dto;
	}

	private List<UsuarioTermoResponseDto> getUsuariosPendentesPortalDivida(List<Usuario> usuarios) {
		return usuarios.stream()
				.filter(usuario -> usuario.getDataAceiteTermoPortalDivida() == null)
				.map(usuario -> new UsuarioTermoResponseDto(
						usuario.getId(),
						usuario.getNome(),
						usuario.getOrgao(),
						usuario.getDataAceiteTermoPortalDivida(),
						usuario.getDataAceiteTermoPortalOrigens()))
				.toList();
	}

	private List<UsuarioTermoResponseDto> getUsuariosAceitesPortalDivida(List<Usuario> usuarios) {
		return usuarios.stream()
				.filter(usuario -> usuario.getDataAceiteTermoPortalDivida() != null)
				.map(usuario -> new UsuarioTermoResponseDto(
						usuario.getId(),
						usuario.getNome(),
						usuario.getOrgao(),
						usuario.getDataAceiteTermoPortalDivida(),
						usuario.getDataAceiteTermoPortalOrigens()))
				.toList();
	}

	private List<UsuarioTermoResponseDto> getUsuariosPendentesPortalOrigens(List<Usuario> usuarios) {
		return usuarios.stream()
				.filter(usuario -> usuario.getDataAceiteTermoPortalOrigens() == null)
				.map(usuario -> new UsuarioTermoResponseDto(
						usuario.getId(),
						usuario.getNome(),
						usuario.getOrgao(),
						usuario.getDataAceiteTermoPortalDivida(),
						usuario.getDataAceiteTermoPortalOrigens()))
				.toList();
	}

	private List<UsuarioTermoResponseDto> getUsuariosAceitesPortalOrigens(List<Usuario> usuarios) {
		return usuarios.stream()
				.filter(usuario -> usuario.getDataAceiteTermoPortalOrigens() != null)
				.map(usuario -> new UsuarioTermoResponseDto(
						usuario.getId(),
						usuario.getNome(),
						usuario.getOrgao(),
						usuario.getDataAceiteTermoPortalDivida(),
						usuario.getDataAceiteTermoPortalOrigens()))
				.toList();
	}

	@Override
	public TermoCondicaoResponseDto findById(UUID id) {
		var termoModel =  this.findByIdModel(id);

		var termoCondicao = new TermoCondicaoResponseDto();
		BeanUtils.copyProperties(termoModel, termoCondicao);
		termoCondicao.setId(termoModel.getId().toString());
		termoCondicao.setNomeSistema(termoModel.getSistema().getNome());
		termoCondicao.setConteudo(termoModel.getConteudo());
		termoCondicao.setNomeUsuario(termoModel.getNomeUsuarioCadastro());
		return termoCondicao;
	}

	@Override
	public List<TermoCondicao> findByNomeSistema() {
		return this.repository.findByNomeSistema();
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {

		Integer totalHistorys = this.dao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, SharedConstant.ITENS_POR_PAGINA);

		Long offset = PaginacaoUtil.getOffset(page, SharedConstant.ITENS_POR_PAGINA);

		List<AuditoriaDto> historysUpdates =  this.dao.findHistorysUpdates(id, offset, SharedConstant.ITENS_POR_PAGINA + MessageTermoCondicaoConstant.INCREMENTO_PAGINA_PAR);

		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(MessageTermoCondicaoConstant.SEM_HISTORICO);

		return PaginacaoResponseDto.fromResultado(new HistoricoAtualizacaoTermoCondicaoToDtoMapper().processarAuditoria(historysUpdates, page, totalPaginas), totalHistorys, totalPaginas, page);

	}

}
