package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessagePerfilAcessoConstant;
import br.gov.ce.pge.gestao.dao.PerfilAcessoDao;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.Modulo;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import br.gov.ce.pge.gestao.entity.Permissao;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.PerfilAcessoRepository;
import br.gov.ce.pge.gestao.service.PerfilAcessoService;
import br.gov.ce.pge.gestao.service.PermissaoService;
import br.gov.ce.pge.gestao.service.SistemaService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ListaUtil;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PerfilAcessoServiceImpl implements PerfilAcessoService {

	private final PerfilAcessoRepository repository;

	private final SistemaService sistemaService;

	private final PermissaoService permissaoService;

	private final PerfilAcessoDao perfilAcessoDao;

	public PerfilAcessoServiceImpl(PerfilAcessoRepository repository, SistemaService sistemaService,
			PermissaoService permissaoService, PerfilAcessoDao perfilAcessoDao) {
		this.repository = repository;
		this.sistemaService = sistemaService;
		this.permissaoService = permissaoService;
		this.perfilAcessoDao = perfilAcessoDao;
	}

	@Override
	public PerfilAcesso save(@Valid PerfilAcessoRequestDto request, String nomeUsuario) {
		var perfilAcessoModel = new PerfilAcesso();
		BeanUtils.copyProperties(request, perfilAcessoModel);

		verificaSePerfilAcessoJaFoiCadastrado(request);

		var listaSistema = request.getSistemas().stream().map(this.sistemaService::findById).collect(Collectors.toList());
		var listaPermissao = request.getPermissoes().stream().map(this.permissaoService::findById).collect(Collectors.toList());


		verificaSePermissoesSelecionadasFazemParteDosSistemasSelecionados(listaSistema, listaPermissao);
		perfilAcessoModel.setNome(request.getNome().trim());
		perfilAcessoModel.setSistemas(listaSistema);
		perfilAcessoModel.setPermissoes(listaPermissao);

		perfilAcessoModel.setNomeUsuario(nomeUsuario);

		perfilAcessoModel = this.repository.save(perfilAcessoModel);
		return perfilAcessoModel;
	}

	private void verificaSePermissoesSelecionadasFazemParteDosSistemasSelecionados(List<Sistema> listaSistema,
	        List<Permissao> listaPermissao) {

	    for (Permissao permissao : listaPermissao) {
	        boolean permissaoEncontrada = listaSistema.stream()
	                .flatMap(sistema -> sistema.getModulos().stream())
	                .anyMatch(modulo -> modulo.getPermissoes().contains(permissao));

	        if (!permissaoEncontrada) {
	            throw new NegocioException(permissao.getNome() + MessagePerfilAcessoConstant.PERMISSAO_NAO_RELACIONADA);
	        }
	    }
	}

	private void verificaSePerfilAcessoJaFoiCadastrado(@Valid PerfilAcessoRequestDto request) {
		String nome = request.getNome().trim();

		var lista = this.repository.findByNome(nome);

		if(!lista.isEmpty())
			throw new NegocioException(MessagePerfilAcessoConstant.REGISTRO_CADASTRADO);
	}

	@Override
	public PerfilAcesso update(UUID id, @Valid PerfilAcessoRequestDto request, String nomeUsuario) throws JsonProcessingException {
		var perfilAcessoModel = this.findByIdModel(id);

		if (verificaObjetosIguais(request, perfilAcessoModel)) {
	        return perfilAcessoModel;
	    }

		if(!perfilAcessoModel.getNome().equals(request.getNome())) {
			verificaSePerfilAcessoJaFoiCadastrado(request);
		}

		CustomRevisionListener.setDadosAntigos(perfilAcessoModel.toStringMapper());
		CustomRevisionListener.setNomeUsuario(nomeUsuario);
		BeanUtils.copyProperties(request, perfilAcessoModel);

		var listaSistema = request.getSistemas().stream().map(this.sistemaService::findById).collect(Collectors.toList());
		var listaPermissao = request.getPermissoes().stream().map(this.permissaoService::findById).collect(Collectors.toList());

		verificaSePermissoesSelecionadasFazemParteDosSistemasSelecionados(listaSistema, listaPermissao);

		perfilAcessoModel.setNome(perfilAcessoModel.getNome().trim());
		perfilAcessoModel.setSistemas(listaSistema);
		perfilAcessoModel.setPermissoes(listaPermissao);

		perfilAcessoModel.setNomeUsuario(nomeUsuario);

		perfilAcessoModel = this.repository.save(perfilAcessoModel);
		return perfilAcessoModel;
	}

	private boolean verificaObjetosIguais(PerfilAcessoRequestDto request, PerfilAcesso perfilAcessoModel) {
	    return Objects.equals(request.getNome(), perfilAcessoModel.getNome()) &&
	    		ListaUtil.isEquals(request.getPermissoes(), perfilAcessoModel.getPermissoes().stream().map(pa -> pa.getId()).collect(Collectors.toList())) &&
	    		ListaUtil.isEquals(request.getSistemas(), perfilAcessoModel.getSistemas().stream().map(si -> si.getId()).collect(Collectors.toList())) &&
	           Objects.equals(request.getSituacao(), perfilAcessoModel.getSituacao());
	}

	@Override
	public PerfilAcessoResponseDto findById(UUID id) {
		var perfilAcessoResponse = new PerfilAcessoResponseDto();

		var perfil = this.findByIdModel(id);

		BeanUtils.copyProperties(perfil, perfilAcessoResponse);

		List<Sistema> sistemasFiltrados = perfil.getSistemas().stream()
                .map(sistema -> filtraModulos(perfil, sistema))
                .collect(Collectors.toList());

		perfilAcessoResponse.setSistemas(sistemasFiltrados);

		return perfilAcessoResponse;
	}

	private Sistema filtraModulos(PerfilAcesso perfil, Sistema sistema) {
		List<Modulo> modulosFiltrados = sistema.getModulos().stream()
		        .map(modulo -> filtrarPermissoes(perfil, modulo))
		        .collect(Collectors.toList());

		sistema.setModulos(modulosFiltrados);
		return sistema;
	}

	private Modulo filtrarPermissoes(PerfilAcesso perfil, Modulo modulo) {
		List<Permissao> permissoesFiltradas = modulo.getPermissoes().stream()
		        .filter(permissao -> perfil.getPermissoes().contains(permissao))
		        .collect(Collectors.toList());
		modulo.setPermissoes(permissoesFiltradas);
		return modulo;
	}

	@Override
	public List<PerfilAcesso> findAll() {
		return this.repository.findAll();
	}

	@Override
	public PaginacaoResponseDto<List<PerfilAcessoFilterResponseDto>> findByFilter(PerfilAcessoFilterRequestDto request, Integer page, String orderBy, Integer tamanho) {
		Integer totalHistorys = this.perfilAcessoDao.countfindByFilter(request);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, tamanho);
		Long offset = PaginacaoUtil.getOffset(page, tamanho);

		request.setOffset(offset);
		request.setLimit(tamanho);
		request.setOrderBy(orderBy);

		var filters =  this.perfilAcessoDao.findByFilter(request);

		return PaginacaoResponseDto.fromResultado(filters, totalHistorys, totalPaginas, page);
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer tamanho) {
		Integer totalHistorys = this.perfilAcessoDao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, tamanho);

		Long offset = PaginacaoUtil.getOffset(page, tamanho);

		List<AuditoriaDto> historysUpdates = this.perfilAcessoDao.findHistorysUpdates(id, offset, tamanho);

		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(MessagePerfilAcessoConstant.SEM_HISTORICO);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates, "historico-perfil"), totalHistorys, totalPaginas, page);
	}

	@Override
	public void delete(UUID id) {
		var perfilAcessoModel = this.findByIdModel(id);

		try {
			this.repository.delete(perfilAcessoModel);
		} catch(Exception e) {
			String message = e.getMessage();
			if(message.contains(MessagePerfilAcessoConstant.DEPENDENCIA_USUARIO)) {
				throw new NegocioException(MessagePerfilAcessoConstant.MSG_DEPENDENCIA_USUARIO);
			}

			throw new NegocioException(message);
		}
	}

	@Override
	public PerfilAcesso findByIdModel(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(MessagePerfilAcessoConstant.NAO_ENCONTRADO));
	}

}
