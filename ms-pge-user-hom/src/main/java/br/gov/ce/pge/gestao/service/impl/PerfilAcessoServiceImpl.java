package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dao.PerfilAcessoDao;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoResponseDto;
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
import br.gov.ce.pge.gestao.shared.util.HistoricoUtil;
import br.gov.ce.pge.gestao.shared.util.ListaUtil;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
public class PerfilAcessoServiceImpl implements PerfilAcessoService {
	
	private static final String NAO_ENCONTRADO = "Não existe esse Perfil de Acesso";
	private static final String DEPENDENCIA_USUARIO = "fk_tbusuarioperfilacesso_tbperfilacesso";
	private static final String MSG_DEPENDENCIA_USUARIO = "Não foi possível realizar a exclusão! O Perfil de Acesso está sendo utilizado para um ou mais usuários cadastrados.";
	private static final String SEM_HISTORICO = "O Perfil de acesso selecionado não possui histórico de edições.";
	private static final String REGISTRO_CADASTRADO = "O registro já foi cadastrado anteriormente!";
	private static final String PERMISSAO_NAO_RELACIONADA = " não faz parte dos sistemas selecionados";

	private static final long ITENS_POR_PAGINA = 10;

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
	public PerfilAcesso save(@Valid PerfilAcessoRequestDto request) {
		var perfilAcessoModel = new PerfilAcesso();
		BeanUtils.copyProperties(request, perfilAcessoModel);
		
		verificaSePerfilAcessoJaFoiCadastrado(request);
		
		var listaSistema = request.getSistemas().stream().map(this.sistemaService::findById).collect(Collectors.toList());
		var listaPermissao = request.getPermissoes().stream().map(this.permissaoService::findById).collect(Collectors.toList());

		
		verificaSePermissoesSelecionadasFazemParteDosSistemasSelecionados(listaSistema, listaPermissao);
		perfilAcessoModel.setNome(request.getNome().trim());
		perfilAcessoModel.setSistemas(listaSistema);
		perfilAcessoModel.setPermissoes(listaPermissao);
		
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
	            throw new NegocioException(permissao.getNome() + PERMISSAO_NAO_RELACIONADA);
	        }
	    }
	}

	private void verificaSePerfilAcessoJaFoiCadastrado(@Valid PerfilAcessoRequestDto request) {
		String nome = request.getNome().trim();
		
		var lista = this.repository.findByNome(nome);
		
		if(!lista.isEmpty())
			throw new NegocioException(REGISTRO_CADASTRADO);
	}

	@Override
	public PerfilAcesso update(UUID id, @Valid PerfilAcessoRequestDto request) throws JsonProcessingException {
		var perfilAcessoModel = this.findByIdModel(id);
		
		if (verificaObjetosIguais(request, perfilAcessoModel)) {
	        return perfilAcessoModel;
	    }
		
		if(!perfilAcessoModel.getNome().equals(request.getNome())) {
			verificaSePerfilAcessoJaFoiCadastrado(request);
		}
		
		CustomRevisionListener.setDadosAntigos(perfilAcessoModel.toStringMapper());
		BeanUtils.copyProperties(request, perfilAcessoModel);
		
		var listaSistema = request.getSistemas().stream().map(this.sistemaService::findById).collect(Collectors.toList());
		var listaPermissao = request.getPermissoes().stream().map(this.permissaoService::findById).collect(Collectors.toList());
		
		verificaSePermissoesSelecionadasFazemParteDosSistemasSelecionados(listaSistema, listaPermissao);

		perfilAcessoModel.setNome(perfilAcessoModel.getNome().trim());
		perfilAcessoModel.setSistemas(listaSistema);
		perfilAcessoModel.setPermissoes(listaPermissao);
		
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
	public PaginacaoResponseDto<List<PerfilAcessoFilterResponseDto>> findByFilter(PerfilAcessoFilterRequestDto request, Integer page, String orderBy) {
		Integer totalHistorys = this.perfilAcessoDao.countfindByFilter(request);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, ITENS_POR_PAGINA);
		Long offset = PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA);

		request.setOffset(offset);
		request.setLimit(ITENS_POR_PAGINA);
		request.setOrderBy(orderBy);
		
		var filters =  this.perfilAcessoDao.findByFilter(request);
		
		return PaginacaoResponseDto.fromResultado(filters, totalHistorys, totalPaginas, page);
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {
		Integer totalHistorys = this.perfilAcessoDao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, ITENS_POR_PAGINA);

		Long offset = PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA);

		List<AuditoriaDto> historysUpdates =  this.perfilAcessoDao.findHistorysUpdates(id, offset, ITENS_POR_PAGINA);

		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(SEM_HISTORICO);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates, "historico-perfil"), totalHistorys, totalPaginas, page);
	}

	@Override
	public void delete(UUID id) {
		var perfilAcessoModel = this.findByIdModel(id);

		try {
			this.repository.delete(perfilAcessoModel);
		} catch(Exception e) {
			String message = e.getMessage();
			if(message.contains(DEPENDENCIA_USUARIO)) {
				throw new NegocioException(MSG_DEPENDENCIA_USUARIO);
			}

			throw new NegocioException(message);
		}
	}
	
	@Override
	public PerfilAcesso findByIdModel(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(NAO_ENCONTRADO));
	}

}