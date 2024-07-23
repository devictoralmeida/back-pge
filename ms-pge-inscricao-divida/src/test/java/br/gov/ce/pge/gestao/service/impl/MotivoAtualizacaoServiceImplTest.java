package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoFase;
import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoTest;
import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicial;
import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicialTest;
import br.gov.ce.pge.gestao.repository.MotivoAtualizacaoFaseRepository;
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
public class MotivoAtualizacaoServiceImplTest {

    @Mock
    private MotivoAtualizacaoFaseRepository repository;

    @InjectMocks
    private MotivoAtualizacaoFaseServiceImpl service;

    @Test
    void test_find_by_id() {

        when(repository.findById(any())).thenReturn(Optional.of(MotivoAtualizacaoTest.getMotivo()));

        MotivoAtualizacaoFase motivo = service.findByIdModel(any());
        assertNotNull(motivo);
        assertEquals("Fase Inicial", motivo.getNome());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void test_find_by_id_erro() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        NegocioException erro = assertThrows(NegocioException.class, () ->
                service.findByIdModel(any())
        );

        assertEquals(MessageCommonsConstants.MENSAGEM_MOTIVO_NAO_ENCONTRADO, erro.getMessage());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void test_find_all() {
        when(repository.findAll()).thenReturn(List.of(MotivoAtualizacaoTest.getMotivo()));

        List<MotivoAtualizacaoFase> motivos = service.findAll();

        assertNotNull(motivos);
        assertEquals(SharedConstant.INCREMENTO, motivos.size());
        assertEquals("Fase Inicial", motivos.get(SharedConstant.INICIO_INDEX).getNome());

        verify(repository, times(1)).findAll();
    }

}
