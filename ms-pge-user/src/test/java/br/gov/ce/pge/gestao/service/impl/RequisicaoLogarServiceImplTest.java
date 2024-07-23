package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ce.pge.gestao.entity.RequisicaoLogar;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.repository.RequisicaoLogarRepository;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;

@ExtendWith({ MockitoExtension.class })
public class RequisicaoLogarServiceImplTest {

    @InjectMocks
    private RequisicaoLogarServiceImpl service;

    @Mock
    private UsuarioConsultaService usuarioService;

    @Mock
    private RequisicaoLogarRepository repository;

    @Test
    void test_save_sucesso() {
        when(usuarioService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        service.save(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"), false);

        verify(repository, times(1)).save(any());
    }

    @Test
    void test_requisicoes_logar() {

        when(usuarioService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        List<RequisicaoLogar> resultado = service.getRequestByUserId(UsuarioTest.getUsuario().getId());

        assertNotNull(resultado);
    }

}
