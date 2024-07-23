package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageTermoCondicaoConstant;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dao.TermoCondicaoDao;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.TermoCondicao;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoTermoCondicaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.TermoCondicaoRepository;
import br.gov.ce.pge.gestao.service.TermoCondicaoService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
		var termoModel = findByIdModel(id);
		BeanUtils.copyProperties(request, termoModel);

		repository.save(termoModel);
	}

	@Override
	public void update(UUID id, @Valid TermoCondicaoRequestDto request, String nomeUsuario) {

		var termoModel = findByIdModel(id);

		verificaVersaoVigente(termoModel.getId());

		var novoTermo = new TermoCondicao();
		novoTermo.setSistema(termoModel.getSistema());
		novoTermo.setConteudo(request.getConteudo());
		novoTermo.setVersao(updateVersion(termoModel.getVersao()));

		CustomRevisionListener.setNomeUsuario(nomeUsuario);
		novoTermo.setNomeUsuario(nomeUsuario);

		termoModel.getUsuarios().stream().forEach(usuario -> {
			if(usuario.getTermos() != null) {
				var termos = usuario.getTermos().stream().filter(termo -> termoModel.getId().equals(termo.getId())).collect(Collectors.toList());
				if(!termos.isEmpty()) {
					usuario.getTermos().remove(termos.get(SharedConstant.INDICE_INICIAL));
					usuario.getTermos().add(novoTermo);
				}
			}
		});

		repository.save(novoTermo);

		if(termoModel.getSistema().getNome().equals(MessageTermoCondicaoConstant.PORTAL_DIVIDA_ATIVA)) {
			repository.updateDataAceitePortalDivida(termoModel.getSistema().getId());
		}else {
			repository.updateDataAceitePortalOrigens(termoModel.getSistema().getId());
		}

	}

	private void verificaVersaoVigente(UUID id) {
		var total = repository.countByIdTermoCondicao(id);

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
		return repository.findById(id).orElseThrow(() -> new NegocioException(MessageTermoCondicaoConstant.NAO_ENCONTRADO));
	}

	@Override
	public List<TermoCondicaoSistemaResponseDto> findBySistema() {

		var termos = findByNomeSistema();

		var lista = new ArrayList<TermoCondicaoSistemaResponseDto>();

		termos.stream().forEach(termo -> {
			var dto = new TermoCondicaoSistemaResponseDto();
			dto.setId(termo.getId().toString());
			dto.setDataCriacao(termo.getDataCriacao());
			dto.setNomeSistema(termo.getSistema().getNome());
			dto.setVersao(termo.getVersao());
			dto.setConteudo(termo.getConteudo());

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
		var termoModel = findByIdModel(id);

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
		return repository.findByNomeSistema();
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer tamanho) {

		Integer totalHistorys = dao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, tamanho);

		Long offset = PaginacaoUtil.getOffset(page, tamanho);

		List<AuditoriaDto> historysUpdates = dao.findHistorysUpdates(id, offset, tamanho + MessageTermoCondicaoConstant.INCREMENTO_PAGINA_PAR);

		if (historysUpdates == null || historysUpdates.isEmpty()) {
			throw new HistoricoAtualizacaoNotFoundException(MessageTermoCondicaoConstant.SEM_HISTORICO);
		}

		return PaginacaoResponseDto.fromResultado(new HistoricoAtualizacaoTermoCondicaoToDtoMapper().processarAuditoria(historysUpdates, page, totalPaginas), totalHistorys, totalPaginas, page);

	}

}
