package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dao.ModuloDao;
import br.gov.ce.pge.gestao.dto.request.ModuloRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.entity.Modulo;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.ModuloRepository;
import br.gov.ce.pge.gestao.service.ModuloService;
import br.gov.ce.pge.gestao.service.PermissaoService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
@Qualifier("moduloServiceImpl")
public class ModuloServiceImpl implements ModuloService {
	
	private static final String NAO_ENCONTRADO = "Modulo não existe";

	private static final long ITENS_POR_PAGINA = 10;

	private final ModuloRepository repository;
	
	private final PermissaoService permissaoService;
	
	private final ModuloDao moduloDao;
	
	public ModuloServiceImpl(ModuloRepository repository, PermissaoService permissaoService,
			ModuloDao moduloDao) {
		this.repository = repository;
		this.permissaoService = permissaoService;
		this.moduloDao = moduloDao;
	}

	@Override
	public Modulo save(@Valid ModuloRequestDto request) {
		
		var moduloModel = new Modulo();
		BeanUtils.copyProperties(request, moduloModel);
		
		var listaPermissaoModel = request.getPermissoes().stream().map(this.permissaoService::findById).collect(Collectors.toList());
		moduloModel.setPermissoes(listaPermissaoModel);
		moduloModel = this.repository.save(moduloModel);
		return moduloModel;
	}

	@Override
	public Modulo update(UUID id, @Valid ModuloRequestDto request) throws JsonProcessingException {
		var moduloModel = this.findById(id);
		CustomRevisionListener.setDadosAntigos(moduloModel.toStringMapper());
		BeanUtils.copyProperties(request, moduloModel);
		var listaPermissaoModel = request.getPermissoes().stream().map(this.permissaoService::findById).collect(Collectors.toList());
		moduloModel.setPermissoes(listaPermissaoModel);
		moduloModel = this.repository.save(moduloModel);
		return moduloModel;
	}

	@Override
	public Modulo findById(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(NAO_ENCONTRADO));
	}

	@Override
	public List<Modulo> findAll() {
		return this.repository.findAll();
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {
		Integer totalHistorys = this.moduloDao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, ITENS_POR_PAGINA);

		Long offset = PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA);

		List<AuditoriaDto> historysUpdates =  this.moduloDao.findHistorysUpdates(id, offset, ITENS_POR_PAGINA);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates, "historico-modulo"), totalHistorys, totalPaginas, page);

	}

	@Override
	public void delete(UUID id) {
		var moduloModel = this.findById(id);
		this.repository.delete(moduloModel);
	}

}
