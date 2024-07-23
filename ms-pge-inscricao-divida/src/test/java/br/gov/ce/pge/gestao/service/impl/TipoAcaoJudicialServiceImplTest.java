package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.TipoAcaoJudicial;
import br.gov.ce.pge.gestao.entity.TipoAcaoJudicialTest;
import br.gov.ce.pge.gestao.repository.TipoAcaoJudicialRepository;
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
public class TipoAcaoJudicialServiceImplTest {

    @Mock
    private TipoAcaoJudicialRepository repository;

    @InjectMocks
    private TipoAcaoJudicialServiceImpl service;

    @Test
    void test_find_all() {

        when(repository.findAll()).thenReturn(List.of(TipoAcaoJudicialTest.getTipoAcaoJudicial()));

        List<TipoAcaoJudicial> tipos = service.findAll();

        assertNotNull(tipos);
        assertEquals(SharedConstant.INCREMENTO, tipos.size());
        assertEquals("Teste", tipos.get(SharedConstant.INICIO_INDEX).getTipo());

        verify(repository, times(1)).findAll();
    }

    @Test
    void test_find_by_id() {

        when(repository.findById(any())).thenReturn(Optional.of(TipoAcaoJudicialTest.getTipoAcaoJudicial()));

        TipoAcaoJudicial tipo = service.findById(any());

        assertNotNull(tipo);
        assertEquals("Teste", tipo.getTipo());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void test_find_by_id_erro() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        NegocioException erro = assertThrows(NegocioException.class, () ->
            service.findById(any())
        );

        assertEquals(MessageCommonsConstants.MENSAGEM_TIPO_ACAO_NAO_ENCONTRADO, erro.getMessage());

        verify(repository, times(1)).findById(any());
    }
}
