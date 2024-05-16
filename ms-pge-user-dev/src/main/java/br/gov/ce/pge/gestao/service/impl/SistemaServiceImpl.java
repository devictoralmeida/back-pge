package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dao.SistemaDao;
import br.gov.ce.pge.gestao.dto.request.SistemaRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.SistemaDto;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.SistemaRepository;
import br.gov.ce.pge.gestao.service.ModuloService;
import br.gov.ce.pge.gestao.service.SistemaService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
@Primary
public class SistemaServiceImpl implements SistemaService {

	private final SistemaRepository repository;
	
	private final ModuloService moduloService;
	
	private final SistemaDao sistemaDao;
	
	public SistemaServiceImpl(SistemaRepository repository, ModuloService moduloService,
			SistemaDao sistemaDao) {
		this.repository = repository;
		this.moduloService = moduloService;
		this.sistemaDao = sistemaDao;
	}

	@Override
	public Sistema save(@Valid SistemaRequestDto request) {
		var sistemaModel = new Sistema();
		BeanUtils.copyProperties(request, sistemaModel);
		
		verificaSeSistemaJaFoiCadastrado(request);
		
		if(request.getModulos() != null) {
			var listaModulo = request.getModulos().stream().map(this.moduloService::findById).collect(Collectors.toList());
			sistemaModel.setModulos(listaModulo);
		}
		sistemaModel.setNome(request.getNome().trim());
		sistemaModel = this.repository.save(sistemaModel);
		return sistemaModel;
	}

	private void verificaSeSistemaJaFoiCadastrado(@Valid SistemaRequestDto request) {
		var nome = request.getNome().trim();
		
		var lista = this.repository.findByNome(nome);
		
		if(!lista.isEmpty())
			throw new NegocioException(MessageCommonsContanst.REGISTRO_CADASTRADO);
	}

	@Override
	public Sistema update(UUID id, @Valid SistemaRequestDto request) throws JsonProcessingException {
		var sistemaModel = this.findById(id);
		CustomRevisionListener.setDadosAntigos(sistemaModel.toStringMapper());
		BeanUtils.copyProperties(request, sistemaModel);
		
		var listaModulo = request.getModulos().stream().map(this.moduloService::findById).collect(Collectors.toList());
		sistemaModel.setNome(sistemaModel.getNome().trim());
		sistemaModel.setModulos(listaModulo);
		sistemaModel = this.repository.save(sistemaModel);
		return sistemaModel;
	}

	@Override
	public Sistema findById(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsContanst.SISTEMA_NAO_ENCONTRADO));
	}

	@Override
	public List<Sistema> findAll() {
		return this.repository.findAll();
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {
		Integer totalHistorys = this.sistemaDao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, SharedConstant.ITENS_POR_PAGINA);

		Long offset = PaginacaoUtil.getOffset(page, SharedConstant.ITENS_POR_PAGINA);

		List<AuditoriaDto> historysUpdates =  this.sistemaDao.findHistorysUpdates(id, offset, SharedConstant.ITENS_POR_PAGINA);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates, "historico-sistema"), totalHistorys, totalPaginas, page);

	}

	@Override
	public void delete(UUID id) {
		var sistemaModel = this.findById(id);
		this.repository.delete(sistemaModel);
	}

	@Override
	public List<Sistema> findAllPermissoesBySistema() {
		return this.repository.findAllPermissoesBySistema();
	}

	@Override
	public List<SistemaDto> findAllOrdenados() {
		return this.sistemaDao.findAllOrdenados();
	}

}
