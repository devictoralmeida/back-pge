package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.configs.DaoConfig;
import br.gov.ce.pge.gestao.configs.FeignClientConfig;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dao.DividaDao;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.DividaPessoaTest;
import br.gov.ce.pge.gestao.entity.DividaTest;
import br.gov.ce.pge.gestao.repository.DividaPessoaRepository;
import br.gov.ce.pge.gestao.repository.DividaRepository;
import br.gov.ce.pge.gestao.service.FaseDividaService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DividaConsultaServiceImplTest {

    @InjectMocks
    private DividaConsultaServiceImpl dividaConsultaService;

    @Mock
    private DividaDao dao;

    @Mock
    private DaoConfig daoConfig;

    @Mock
    private FeignClientConfig feignClientConfig;

    @Mock
    private TipoReceitaService tipoReceitaService;

    @Mock
    private FaseDividaService faseDividaService;

    @Mock
    private DividaPessoaRepository dividaPessoaRepository;

    @Mock
    private DividaRepository repository;

    @Test
    void test_find_by_filter() {

        when(daoConfig.getDividaDao()).thenReturn(dao);
        when(dao.findByFilterDivida(any(), any())).thenReturn(List.of(DividaFilterResponseDtoTest.getDividaFilter()));
        when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
        when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
        when(dividaPessoaRepository.getSucessor(any())).thenReturn(DividaPessoaTest.getDividaPessoaFilter());
        when(feignClientConfig.getTipoReceitaService().findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributaria());
        when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());
        when(feignClientConfig.getFaseDividaService().findAll()).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        PaginacaoResponseDto<List<DividaFilterResponseDto>> filter = dividaConsultaService.findDividaByFilter(DividaFilterRequestDtoTest.getRequest(), 1, 10L, null);

        asserts(filter);
    }

    @Test
    void test_find_by_filter_natureza_tributaria() {

        when(daoConfig.getDividaDao()).thenReturn(dao);
        when(dao.findByFilterDivida(any(), any())).thenReturn(List.of(DividaFilterResponseDtoTest.getDividaFilter()));
        when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
        when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
        when(dividaPessoaRepository.getSucessor(any())).thenReturn(DividaPessoaTest.getDividaPessoaFilter());
        when(feignClientConfig.getTipoReceitaService().findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributariaDivida());
        when(feignClientConfig.getTipoReceitaService().findAll()).thenReturn(List.of(TipoReceitaResponseDtoTest.getTipoReceitaTributariaDivida()));
        when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());
        when(feignClientConfig.getFaseDividaService().findAll()).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        PaginacaoResponseDto<List<DividaFilterResponseDto>> filter = dividaConsultaService.findDividaByFilter(DividaFilterRequestDtoTest.getRequestTributaria(), 1, 10L, null);

        asserts(filter);
    }

    @Test
    void test_find_by_filter_natureza_nao_tributaria() {

        when(daoConfig.getDividaDao()).thenReturn(dao);
        when(dao.findByFilterDivida(any(), any())).thenReturn(List.of(DividaFilterResponseDtoTest.getDividaFilter()));
        when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
        when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
        when(dividaPessoaRepository.getSucessor(any())).thenReturn(DividaPessoaTest.getDividaPessoaFilter());
        when(feignClientConfig.getTipoReceitaService().findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaNaoTributariaDivida());
        when(feignClientConfig.getTipoReceitaService().findAll()).thenReturn(List.of(TipoReceitaResponseDtoTest.getTipoReceitaNaoTributariaDivida()));
        when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());
        when(feignClientConfig.getFaseDividaService().findAll()).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        PaginacaoResponseDto<List<DividaFilterResponseDto>> filter = dividaConsultaService.findDividaByFilter(DividaFilterRequestDtoTest.getRequestNaoTributaria(), 1, 10L, null);

        asserts(filter);
    }

    private void asserts(PaginacaoResponseDto<List<DividaFilterResponseDto>> filter) {
        assertNotNull(filter);
        assertNotNull(filter.getList());
        assertEquals(true, filter.getList().get(SharedConstant.INICIO_INDEX).getStatusCobranca());
        assertEquals("123456789", filter.getList().get(SharedConstant.INICIO_INDEX).getCgf());
        assertEquals("Teste", filter.getList().get(SharedConstant.INICIO_INDEX).getNomeDevedor());
    }

    @Test
    void test_find_by_filter_consulta_generica() {

        when(daoConfig.getDividaDao()).thenReturn(dao);
        when(dao.findByFilterDivida(any(), any())).thenReturn(List.of(DividaFilterResponseDtoTest.getDividaFilter()));
        when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
        when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
        when(feignClientConfig.getTipoReceitaService().findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributaria());
        when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());

        PaginacaoResponseDto<List<DividaFilterResponseDto>> filter = dividaConsultaService.findDividaByFilter(DividaFilterRequestDtoTest.getRequestNomeDevedor(), 1, 10L, null);

        asserts(filter);

        assertNotNull(filter);
    }

    @Test
    void test_find_by_filter_lote_erro_nao_tributaria() {

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            dividaConsultaService.findDividaByFilter(DividaFilterRequestDtoTest.getRequestLoteNaoTributariaErrado(), 1, 10L, null);
        });

        Assertions.assertEquals(MessageCommonsConstants.MSG_ERRO_LOTE, error.getMessage());

    }

    @Test
    void test_find_by_filter_lote_erro_tributaria() {

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            dividaConsultaService.findDividaByFilter(DividaFilterRequestDtoTest.getRequestLoteTributariaErrado(), 1, 10L, null);
        });

        Assertions.assertEquals(MessageCommonsConstants.MSG_ERRO_LOTE, error.getMessage());

    }

    @Test
    void test_find_by_id() {
        when(repository.findById(any())).thenReturn(Optional.of(DividaTest.getDividaReponse()));

        DividaResponseDto divida = dividaConsultaService.findById(UUID.fromString("e93fd062-c46e-4dfa-ad87-67ab0300dd37"));

        assertNotNull(divida);
        assertEquals(UUID.fromString("e93fd062-c46e-4dfa-ad87-67ab0300dd37"), divida.getId());

    }

    @Test
    void test_find_by_id_erro() {

        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception erro = assertThrows(NegocioException.class, () -> {
            dividaConsultaService.findById(any());
        });

        assertEquals(MessageCommonsConstants.MENSAGEM_DIVIDA_NAO_ENCONTRADA, erro.getMessage());

        verify(repository, times(1)).findById(any());

    }

    @Test
    void test_retorna_acoes_judiciais_quando_divida_existe() {
        when(repository.findById(any())).thenReturn(Optional.of(DividaTest.getDividaComAcoesJudiciais()));
        AcaoJudicialDividaResponseDto resultado = dividaConsultaService.findAcoesJudiciaisByDivida(DividaTest.getDividaComAcoesJudiciais().getId());
        assertNotNull(resultado);
        assertEquals(1, resultado.getAcoesJudiciais().size());
    }

    @Test
    void test_find_by_acoes_judiciais_exception_divida_inexistente() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception erro = assertThrows(NegocioException.class, () -> {
            dividaConsultaService.findAcoesJudiciaisByDivida(UUID.randomUUID());
        });
        
        assertEquals(MessageCommonsConstants.MENSAGEM_DIVIDA_NAO_ENCONTRADA, erro.getMessage());
    }
}