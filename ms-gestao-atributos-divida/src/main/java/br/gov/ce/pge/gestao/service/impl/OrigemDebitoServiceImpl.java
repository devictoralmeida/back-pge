package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.contantes.MensagemOrigemDebito;
import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.OrigemDebitoDao;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.OrigemDebito;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.mappers.todto.OrigemDebitoToDtoMapper;
import br.gov.ce.pge.gestao.mappers.tomodel.OrigemDebitoToModelMapper;
import br.gov.ce.pge.gestao.repository.OrigemDebitoRepository;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.exception.OrigemDebitoNotFoundException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrigemDebitoServiceImpl implements OrigemDebitoService {

	private final OrigemDebitoRepository repository;

	private final OrigemDebitoDao dao;

	private final AuditoriaDao auditoriaDao;

	public OrigemDebitoServiceImpl(OrigemDebitoRepository repository, OrigemDebitoDao dao, AuditoriaDao auditoriaDao) {
		this.repository = repository;
		this.dao = dao;
		this.auditoriaDao = auditoriaDao;
	}

	@Override
	public OrigemDebitoResponseDto save(OrigemDebitoRequestDto request, String nomeUsuario) {
		verificaSeOrigemJaFoiCadastrada(request);
		var origemDebitoModel = OrigemDebitoToModelMapper.toModel(request, nomeUsuario);
		CustomRevisionListener.setNomeUsuario(nomeUsuario);
		origemDebitoModel = this.repository.save(origemDebitoModel);
		return OrigemDebitoToDtoMapper.toDto(origemDebitoModel);
	}

	private void verificaSeOrigemJaFoiCadastrada(OrigemDebitoRequestDto request) {
		var origem = this.repository.findByNome(request.getNome().trim().toUpperCase());

		if(Objects.nonNull(origem)) {
			throw new NegocioException(MensagemOrigemDebito.MENSAGEM_ORIGEM_CADASTRADA);
		}
	}

	@Override
	public OrigemDebitoResponseDto update(UUID id,@Valid OrigemDebitoRequestDto request, String nomeUsuario) throws JsonProcessingException {
		var origemDebito = findByIdModel(id);

		if(!origemDebito.getNome().equalsIgnoreCase(request.getNome())) {
			verificaSeOrigemJaFoiCadastrada(request);
		}

		CustomRevisionListener.setDadosAntigos(origemDebito.toStringMapper());
		CustomRevisionListener.setNomeUsuario(nomeUsuario);
		var origemModelAlterada = OrigemDebitoToModelMapper.toModelUpdate(request, origemDebito, nomeUsuario);
		origemModelAlterada = this.repository.save(origemModelAlterada);
		return  OrigemDebitoToDtoMapper.toDto(origemModelAlterada);
	}

	public OrigemDebito findByIdModel(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new OrigemDebitoNotFoundException(MensagemOrigemDebito.MENSAGEM_ORIGEM_NAO_ENCONTRADA));
	}

	@Override
	public OrigemDebitoResponseDto findById(UUID id) {
		return OrigemDebitoToDtoMapper.toDto(this.findByIdModel(id));
	}

	@Override
	public List<OrigemDebitoResponseDto> findAll() {
		var lista = this.repository.findAllOrigens();

		var listaDto = new ArrayList<OrigemDebitoResponseDto>();

		lista.forEach(model -> listaDto.add(OrigemDebitoToDtoMapper.toDto(model)));

		return listaDto;
	}

	@Override
	public List<OrigemDebitoConsultaResponseDto> findOrigemDebitoByFilter(OrigemDebitoFilterRequestDto request) {
		return this.dao.findOrigemDebitosByFilter(request);
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer tamanho) {

		Integer totalHistorys = this.auditoriaDao.countHistorysUpdates(id);

		List<AuditoriaDto> historysUpdates = this.auditoriaDao.findHistorysUpdates(id, PaginacaoUtil.getOffset(page, tamanho), tamanho, "OrigemDebito");

		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(MensagemOrigemDebito.MENSAGEM_ORIGEM_SEM_HISTORICO_EDICAO);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates), totalHistorys, PaginacaoUtil.getTotalPaginas(totalHistorys, tamanho), page);
	}

	@Override
	public void delete(UUID id, String nomeUsuario) {
		var origemDebito = this.findByIdModel(id);

		try {
			CustomRevisionListener.setNomeUsuario(nomeUsuario);
			this.repository.delete(origemDebito);
		} catch(Exception e) {
			String message = e.getMessage();
			if(message.contains("fk_tbtiporeceitaorigem_tborigemdebito")) {
				throw new NegocioException(MensagemOrigemDebito.MENSAGEM_ORIGEM_ERRO_DELETE);
			}

			throw new NegocioException(message);
		}
	}

}
