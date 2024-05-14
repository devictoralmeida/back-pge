package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.contantes.MensagemProdutoServico;
import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.ProdutoServicoDao;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.entity.ProdutoServico;
import br.gov.ce.pge.gestao.entity.TipoReceita;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.mappers.todto.ProdutoServicoToDtoMapper;
import br.gov.ce.pge.gestao.mappers.tomodel.ProdutoServicoToModelMapper;
import br.gov.ce.pge.gestao.repository.ProdutoServicoRepository;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.exception.ProdutoServicoNotFoundException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
public class ProdutoServicoServiceImpl implements ProdutoServicoService {
	
	private static final long ITENS_POR_PAGINA = 10;
	
	private final ProdutoServicoRepository repository;
	
	private final TipoReceitaService tipoReceitaService;
	
	private final ProdutoServicoDao dao;

	private AuditoriaDao auditoriaDao;
	
	public ProdutoServicoServiceImpl(ProdutoServicoRepository repository, TipoReceitaService tipoReceitaService,
			ProdutoServicoDao dao, AuditoriaDao auditoriaDao) {
		this.repository = repository;
		this.tipoReceitaService = tipoReceitaService;
		this.dao = dao;
		this.auditoriaDao = auditoriaDao;
	}

	@Override
	public ProdutoServicoResponseDto save(ProdutoServicoRequestDto request) {
		verificaSeProdutoServicoJaFoiCadastrado(request);
		var produtoServicoModel = ProdutoServicoToModelMapper.toModel(request);
		produtoServicoModel.setTipoReceitas(getTipoReceitas(request));
		produtoServicoModel = this.repository.save(produtoServicoModel);
		return ProdutoServicoToDtoMapper.toDto(produtoServicoModel);
	}

	private void verificaSeProdutoServicoJaFoiCadastrado(ProdutoServicoRequestDto request) {
		var produtoServico = this.repository.findByCodigo(request.getCodigo());
		
		if(Objects.nonNull(produtoServico)) 
			throw new NegocioException(MensagemProdutoServico.MENSAGEM_PRODUTO_CADASTRADO);
		
	}

	private List<TipoReceita> getTipoReceitas(ProdutoServicoRequestDto request) {
		return request.getIdsTipoReceitas().stream().map(this.tipoReceitaService::findByIdModel).collect(Collectors.toList());
	}
	
	private List<TipoReceita> getTipoReceitas(ProdutoServicoUpdateRequestDto request) {
		return request.getIdsTipoReceitas().stream().map(this.tipoReceitaService::findByIdModel).collect(Collectors.toList());
	}

	@Override
	public ProdutoServicoResponseDto findById(UUID id) {
		return ProdutoServicoToDtoMapper.toDto(this.findByIdModel(id));
	}

	@Override
	public ProdutoServicoResponseDto update(UUID id, ProdutoServicoUpdateRequestDto request) throws JsonProcessingException {
		var produtoServicoModel = this.findByIdModel(id);

		CustomRevisionListener.setDadosAntigos(produtoServicoModel.toStringMapper());
		var produtoServicoModelAlterado = ProdutoServicoToModelMapper.toModelUpdate(request, produtoServicoModel);
		produtoServicoModelAlterado.setTipoReceitas(getTipoReceitas(request));

		produtoServicoModelAlterado = this.repository.save(produtoServicoModelAlterado);
		return ProdutoServicoToDtoMapper.toDto(produtoServicoModelAlterado);
	}

	@Override
	public List<ProdutoServicoResponseDto> findAll() {
		return this.repository.findAllProdutosServico().stream().map(ProdutoServicoToDtoMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public ProdutoServico findByIdModel(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new ProdutoServicoNotFoundException(MensagemProdutoServico.MENSAGEM_PRODUTO_NAO_ENCONTRADO));
	}

	@Override
	public List<ProdutoServicoConsultaFilterResponseDto> findProdutoServicosByFilter(ProdutoServicoFilterRequestDto request) {
		return this.dao.findTipoReceitasByFilter(request);
	}
	
	@Override
	public void delete(UUID id) {
		var tipo = this.findByIdModel(id);
		this.repository.delete(tipo);
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {
		Integer totalHistorys = this.auditoriaDao.countHistorysUpdatesProdutoServico(id);

		List<AuditoriaDto> historysUpdates = this.auditoriaDao.findHistorysUpdates(id, PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA), ITENS_POR_PAGINA, "ProdutoServico");
		
		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(MensagemProdutoServico.MENSAGEM_PRODUTO_SEM_HISTORICO_EDICAO);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates), totalHistorys, PaginacaoUtil.getTotalPaginas(totalHistorys, ITENS_POR_PAGINA), page);
	}

}
