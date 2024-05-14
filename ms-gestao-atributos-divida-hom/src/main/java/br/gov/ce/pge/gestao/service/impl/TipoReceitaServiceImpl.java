package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.contantes.MensagemTipoReceita;
import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.TipoReceitaDao;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;
import br.gov.ce.pge.gestao.entity.TipoReceita;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.mappers.todto.TipoReceitaToDtoMapper;
import br.gov.ce.pge.gestao.mappers.tomodel.TipoReceitaToModelMapper;
import br.gov.ce.pge.gestao.repository.TipoReceitaRepository;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.exception.TipoReceitaNotFoundException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
public class TipoReceitaServiceImpl implements TipoReceitaService {
	
	private static final long ITENS_POR_PAGINA = 10;
	
	private final TipoReceitaRepository repository;
	
	private final OrigemDebitoService origemDebitoService;
	
	private final TipoReceitaDao dao;

	private final AuditoriaDao auditoriaDao;
	
	public TipoReceitaServiceImpl(TipoReceitaRepository repository, OrigemDebitoService origemDebitoService,
			TipoReceitaDao dao, AuditoriaDao auditoriaDao) {
		this.repository = repository;
		this.origemDebitoService = origemDebitoService;
		this.dao = dao;
		this.auditoriaDao = auditoriaDao;
	}
	
	@Override
	public TipoReceitaResponseDto save(TipoReceitaRequestDto request) {
		verificaSeTipoReceitaJaFoiCadastrada(request);
		var tipoReceitaModel = TipoReceitaToModelMapper.toModel(request);
		tipoReceitaModel.setOrigemDebitos(getOrigens(request));
		tipoReceitaModel = this.repository.save(tipoReceitaModel);
		return TipoReceitaToDtoMapper.toDto(tipoReceitaModel);
	}

	private void verificaSeTipoReceitaJaFoiCadastrada(TipoReceitaRequestDto request) {
		var tipo = this.repository.findByCodigo(request.getCodigo());
		
		if(Objects.nonNull(tipo)) 
			throw new NegocioException(MensagemTipoReceita.MENSAGEM_TIPORECEITA_CADASTRADA);
	}

	@Override
	public TipoReceitaResponseDto findById(UUID id) {
		var tipoReceita = this.findByIdModel(id);
		return TipoReceitaToDtoMapper.toDto(tipoReceita);
	}
	
	@Override
	public TipoReceita findByIdModel(UUID id) {
		return repository.findById(id).orElseThrow(() -> new TipoReceitaNotFoundException(MensagemTipoReceita.MENSAGEM_TIPORECEITA_NAO_ENCONTRADA));
	}
	
	@Override
	public TipoReceitaResponseDto update(UUID id,@Valid TipoReceitaUpdateRequestDto request) throws JsonProcessingException {
		var tipoReceita = findByIdModel(id);
		CustomRevisionListener.setDadosAntigos(tipoReceita.toStringMapper());
		var tipoReceitaModelAlterada = TipoReceitaToModelMapper.toModelUpdate(request, tipoReceita);
		tipoReceitaModelAlterada.setOrigemDebitos(getOrigens(request));
		tipoReceitaModelAlterada = this.repository.save(tipoReceitaModelAlterada);
		return  TipoReceitaToDtoMapper.toDto(tipoReceitaModelAlterada);
	}
	
	private List<OrigemDebito> getOrigens(TipoReceitaRequestDto request) {
		return request.getOrigemDebitos().stream().map(origemDebitoService::findByIdModel).collect(Collectors.toList());
	}
	
	private List<OrigemDebito> getOrigens(TipoReceitaUpdateRequestDto request) {
		return request.getOrigemDebitos().stream().map(origemDebitoService::findByIdModel).collect(Collectors.toList());
	}
	
	@Override
	public List<TipoReceitaResponseDto> findAll() {
		var listaModel = this.repository.findAllTipos();
		return listaModel.stream().map(TipoReceitaToDtoMapper::toDto).collect(Collectors.toList());
	}
	
	@Override
	public List<TipoReceitaConsultaFilterResponseDto> findTipoReceitasByFilter(TipoReceitaFilterRequestDto request) {
		return this.dao.findTipoReceitasByFilter(request);
	}

	@Override
	public void delete(UUID id) {
		var tipoReceita = this.findByIdModel(id);
		
		try {
			this.repository.delete(tipoReceita);
		} catch (Exception e) {
			String message = e.getMessage();
			if(message.contains("fk_tbprodutoservicoreceita_tbtiporeceita")) {
				throw new NegocioException(MensagemTipoReceita.MENSAGEM_TIPORECEITA_ERRO_DELETE);
			}

			throw new NegocioException(message);
		}
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {
		Integer totalHistorys = this.auditoriaDao.countHistorysUpdatesTipoReceita(id);

		List<AuditoriaDto> historysUpdates = this.auditoriaDao.findHistorysUpdates(id, PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA), ITENS_POR_PAGINA, "TipoReceita");

		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(MensagemTipoReceita.MENSAGEM_TIPORECEITA_SEM_HISTORICO_EDICAO);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates), totalHistorys, PaginacaoUtil.getTotalPaginas(totalHistorys, ITENS_POR_PAGINA), page);
	}

}
