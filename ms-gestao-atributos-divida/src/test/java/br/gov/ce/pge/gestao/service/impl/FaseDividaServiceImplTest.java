package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.contantes.MensagemFaseDivida;
import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.FaseDividaDao;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.FluxoFaseDividaWrapperRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import br.gov.ce.pge.gestao.entity.FaseDividaTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import br.gov.ce.pge.gestao.repository.FaseDividaRepository;
import br.gov.ce.pge.gestao.shared.exception.FaseDividaNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class FaseDividaServiceImplTest {

    @Autowired
    @InjectMocks
    private FaseDividaServiceImpl service;

    @Mock
    private FaseDividaRepository repository;

    @Mock
    private AuditoriaDao auditoriaDao;

    @Mock
    private FaseDividaDao faseDividaDao;

    @Test
    void save_ok() {
        when(repository.save(any())).thenReturn(FaseDividaTest.getFaseDivida());
        when(repository.findMaxCodigo()).thenReturn("0");

        FaseDividaResponseDto dto = service.save(FaseDividaRequestDtoTest.getRequest(), anyString());
        assertEquals(FaseDividaTest.getFaseDivida().getId().toString(), dto.getId());
        assertEquals(FaseDividaTest.getFaseDivida().getNome(), dto.getNome());
        assertEquals(FaseDividaTest.getFaseDivida().getCodigo(), dto.getCodigo());
        assertEquals(FaseDividaTest.getFaseDivida().getTipoCobranca(), dto.getTipoCobranca());
        assertEquals(FaseDividaTest.getFaseDivida().getExigeCobranca(), dto.getExigeCobranca());
        assertEquals(FaseDividaTest.getFaseDivida().getTipoMovimentacao(), dto.getTipoMovimentacao());
        assertEquals(FaseDividaTest.getFaseDivida().getDescricao(), dto.getDescricao());

        verify(repository, times(1)).save(any(FaseDivida.class));
    }

    @Test
    void save_error_fase_cadastrada() {
        when(repository.findByNome(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDivida()));

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            service.save(FaseDividaRequestDtoTest.getRequest(), anyString());
        });

        Assertions.assertEquals(String.format(MensagemFaseDivida.MENSAGEM_FASE_CADASTRADA, FaseDividaRequestDtoTest.getRequest().getNome()), error.getMessage());
        verify(repository, times(1)).findByNome(any(String.class));
        verify(repository, times(0)).save(any(FaseDivida.class));
    }

    @Test
    void save_error_exige_cobranca_false() {

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            service.save(FaseDividaRequestDtoTest.getRequestExigeCobrancaFalse(), anyString());
        });

        Assertions.assertEquals(MensagemFaseDivida.MENSAGEM_FASE_TIPO_COBRANCA_INVALIDO, error.getMessage());
        verify(repository, times(0)).save(any(FaseDivida.class));
    }

    @Test
    void save_ok_tipo_cobranca_vazio() {
        when(repository.findMaxCodigo()).thenReturn("0");

        service.save(FaseDividaRequestDtoTest.getRequestTipoCobrancaVazio(), anyString());

        verify(repository, times(1)).save(any(FaseDivida.class));
    }

    @Test
    void update_ok() throws JsonProcessingException {
        when(repository.findById(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDivida()));
        when(repository.save(any())).thenReturn(FaseDividaTest.getFaseDividaUpdate());

        FaseDividaResponseDto dto = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), FaseDividaRequestDtoTest.getRequestUpdate(), anyString());
        assertEquals(FaseDividaRequestDtoTest.getRequestUpdate().getId().toString(), dto.getId());
        assertEquals(FaseDividaRequestDtoTest.getRequestUpdate().getNome(), dto.getNome());
        assertEquals(FaseDividaRequestDtoTest.getRequestUpdate().getSituacao(), dto.getSituacao());

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any(FaseDivida.class));
    }

    @Test
    void update_error_nome_ja_cadastrado() {
        when(repository.findById(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDivida()));
        doThrow(new NegocioException("O nome da fase é igual ao nome da fase Teste Junit já cadastrada.")).when(repository).findByNome(any());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), FaseDividaRequestDtoTest.getRequestUpdate(), anyString());
        });

        assertEquals("O nome da fase é igual ao nome da fase Teste Junit já cadastrada.", exception.getMessage());

        verify(repository, times(1)).findById(any());
        verify(repository, times(0)).save(any(FaseDivida.class));
    }

    @Test
    void update_error_fase_cadastrada() throws JsonProcessingException {
        when(repository.findById(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDivida()));

        service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), FaseDividaRequestDtoTest.getRequest(), anyString());
    }

    @Test
    void find_all_ok() {
        List<FaseDivida> lista = List.of(FaseDividaTest.getFaseDivida());

        when(repository.findAllFase()).thenReturn(lista);

        List<FaseDividaResponseDto> dto = service.findAll();

        assertEquals(1, dto.size());
        assertEquals("Fase Cadastrada", dto.get(0).getNome());
        assertEquals(Situacao.ATIVA, dto.get(0).getSituacao());
        verify(repository, times(1)).findAllFase();
    }

    @Test
    void find_semelhantes_ok() {
        List<FaseDivida> lista = List.of(FaseDividaTest.getFaseDivida());

        when(repository.findByNomeSemelhante(any())).thenReturn(lista);

        List<FaseDividaResponseDto> dto = service.findSemelhantes("Fase Cadastrda");

        assertEquals(1, dto.size());
        assertEquals("Fase Cadastrada", dto.get(0).getNome());
        assertEquals(Situacao.ATIVA, dto.get(0).getSituacao());
        verify(repository, times(1)).findByNomeSemelhante(any());
    }

    @Test
    void find_all_vazia_ok() {
        List<FaseDivida> lista = List.of();

        when(repository.findAllFase()).thenReturn(lista);

        List<FaseDividaResponseDto> dto = service.findAll();

        assertEquals(0, dto.size());
        verify(repository, times(1)).findAllFase();
    }

    @Test
    void find_by_id_ok() {
        when(repository.findById(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDivida()));

        FaseDividaResponseDto dto = service.findById(any());
        assertEquals("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a", dto.getId());
        assertEquals("Fase Cadastrada", dto.getNome());
        assertEquals(Situacao.ATIVA, dto.getSituacao());
        Assertions.assertEquals("teste", dto.getDescricao());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
        Assertions.assertEquals(TipoMovimentacaoFase.AUTOMATICA, dto.getTipoMovimentacao());
        Assertions.assertEquals("00001", dto.getCodigo());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void find_by_id_nao_encontrado() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception error = Assertions.assertThrows(FaseDividaNotFoundException.class, () -> {
            service.findById(any());
        });

        Assertions.assertEquals("Fase da Dívida não encontrada.", error.getMessage());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void find_view_history_fase_divida_by_id_ok() throws JsonProcessingException {

        when((List<AuditoriaDto>) auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("FaseDivida"))).thenReturn(List.of(AuditoriaTest.getAuditoriaFaseDivida()));

        PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> dto = service.viewChangeHistoryById(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), 1, 10);

        List<HistoricoAtualizacaoResponseDto> historicos = dto.getResultado();

        List<Object> dadosAlterados = historicos.get(0).getDadosAlterados();

        @SuppressWarnings("unchecked")
        List<Document> documents = (List<Document>) dadosAlterados.get(0);

        assertEquals("anonimo", historicos.get(0).getResponsavel());
        assertEquals("Nome da Fase", documents.get(0).getString("campoAtualizado"));
        assertEquals("teste", documents.get(0).getString("valorAntigo"));
        assertEquals("teste up", documents.get(0).getString("valorNovo"));
        assertEquals("Descrição da Fase", documents.get(1).getString("campoAtualizado"));
        assertEquals("teste descricao", documents.get(1).getString("valorAntigo"));
        assertEquals("teste descricao up", documents.get(1).getString("valorNovo"));

        verify(auditoriaDao, times(1)).findHistorysUpdates(any(UUID.class), eq(0L), eq(10), eq("FaseDivida"));
    }

    @Test
    void find_view_history_fase_divida_by_id_ok_tipo_cobranca_null() throws JsonProcessingException {

        when((List<AuditoriaDto>) auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("FaseDivida"))).thenReturn(List.of(AuditoriaTest.getAuditoriaFaseDividaTipoCobrancaNull()));

        PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> dto = service.viewChangeHistoryById(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), 1, 10);

        List<HistoricoAtualizacaoResponseDto> historicos = dto.getResultado();

        List<Object> dadosAlterados = historicos.get(0).getDadosAlterados();

        @SuppressWarnings("unchecked")
        List<Document> documents = (List<Document>) dadosAlterados.get(0);

        assertEquals("anonimo", historicos.get(0).getResponsavel());
        assertEquals("Nome da Fase", documents.get(0).getString("campoAtualizado"));
        assertEquals("teste", documents.get(0).getString("valorAntigo"));
        assertEquals("teste up", documents.get(0).getString("valorNovo"));
        assertEquals("Descrição da Fase", documents.get(1).getString("campoAtualizado"));
        assertEquals("teste descricao", documents.get(1).getString("valorAntigo"));
        assertEquals("teste descricao up", documents.get(1).getString("valorNovo"));

        verify(auditoriaDao, times(1)).findHistorysUpdates(any(UUID.class), eq(0L), eq(10), eq("FaseDivida"));
    }

    @Test
    void find_fluxo_ok() {
        List<FaseDivida> lista = List.of(FaseDividaTest.getFaseDividaFluxo());

        when(repository.findFasesAtivas()).thenReturn(lista);

        List<FluxoFaseDividaResponseDto> dto = service.obterFluxoFase();

        assertEquals(1, dto.size());
        assertEquals("Fase Cadastrada", dto.get(0).getNome());
        assertEquals(1, dto.get(0).getFasesPermitidas().size());
        verify(repository, times(1)).findFasesAtivas();
    }

    @Test
    void salvar_fluxo_ok() {
        FaseDivida faseDivida = FaseDividaTest.getFaseDividaFluxo();

        when(repository.findById(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDividaFluxo()));

        service.salvarFluxoFases(FluxoFaseDividaWrapperRequestDtoTest.getFluxoFaseRequest());

        verify(repository, times(2)).save(any(FaseDivida.class));
    }

    @Test
    void delete_ok() {
        var model = FaseDividaTest.getFaseDivida();

        when(repository.findById(any())).thenReturn(Optional.of(model));

        service.delete(UUID.randomUUID(), "Paulo");

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).delete(any());
    }

    @Test
    void find_by_filter_ok() {

        when(faseDividaDao.findFaseDividaByFilter(any())).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        var dto = service.findByFilter(any());

        assertEquals(1, dto.size());
        assertEquals("Fase Cadastrada", dto.get(0).getNome());
        assertEquals(Situacao.ATIVA, dto.get(0).getSituacao());

        verify(faseDividaDao, times(1)).findFaseDividaByFilter(any());
    }

    @Test
    void salvar_fluxo_fase_permitida_com_mesmo_id_fase_atual() {
        when(repository.findById(any())).thenReturn(Optional.of(FaseDividaTest.getFaseDividaFluxo()));

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            service.salvarFluxoFases(FluxoFaseDividaWrapperRequestDtoTest.getFluxoFaseRequestMesmoId());
        });

        Assertions.assertEquals(MensagemFaseDivida.MENSAGEM_FASE_FLUXO_IGUAL_INVALIDA, error.getMessage());

        verify(repository, times(2)).findById(any());
    }
}
