package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicial;
import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicialTest;
import br.gov.ce.pge.gestao.repository.ProvidenciaAcaoJudicialRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProvidenciaAcaoJudicialServiceImplTest {

    @Mock
    private ProvidenciaAcaoJudicialRepository repository;

    @InjectMocks
    private ProvidenciaJudicialServiceImpl service;

    @Test
    void test_find_all() {

        when(repository.findAll()).thenReturn(List.of(ProvidenciaAcaoJudicialTest.getProvidencia()));

        List<ProvidenciaAcaoJudicial> providencias = service.findAll();

        assertNotNull(providencias);
        assertEquals(SharedConstant.INCREMENTO, providencias.size());
        assertEquals("Teste", providencias.get(SharedConstant.INICIO_INDEX).getNome());
    }

    @Test
    void test_find_by_id() {
        when(repository.findById(any())).thenReturn(Optional.of(ProvidenciaAcaoJudicialTest.getProvidencia()));

        ProvidenciaAcaoJudicial providencia = service.findById(any());

        assertNotNull(providencia);
        assertEquals("Teste", providencia.getNome());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void test_find_by_id_erro() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        NegocioException erro = assertThrows(NegocioException.class, () ->
            service.findById(any())
        );

        assertEquals(MessageCommonsConstants.MENSAGEM_PROVIDENCIA_NAO_ECONTRADA, erro.getMessage());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void test_save() {

        service.save("teste");

        verify(repository, times(1)).save(any(ProvidenciaAcaoJudicial.class));
    }

    @Test
    void test_find_by_nome() {
        when(service.findByNome(any())).thenReturn(ProvidenciaAcaoJudicialTest.getProvidencia());

        ProvidenciaAcaoJudicial providencia = service.findByNome("teste");

        assertEquals("Teste", providencia.getNome());

        verify(repository, times(1)).findByNome(any());
    }
}
