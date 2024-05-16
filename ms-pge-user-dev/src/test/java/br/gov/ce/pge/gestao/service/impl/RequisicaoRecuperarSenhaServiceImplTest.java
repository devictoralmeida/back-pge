package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.RequisicaoRecuperarSenha;
import br.gov.ce.pge.gestao.repository.RequisicaoRecuperarSenhaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({ MockitoExtension.class })
public class RequisicaoRecuperarSenhaServiceImplTest {

    @Mock
    private RequisicaoRecuperarSenhaRepository repository;

    @InjectMocks
    private RequisicaoRecuperarSenhaServiceImpl service;

    @Test
    void save_sucesso() {

        var requisicao = new RequisicaoRecuperarSenha();
        requisicao.setHoraRequisicao(LocalDateTime.now());
        requisicao.setEmail("email@pge.br");

        service.save(requisicao);

        verify(repository, times(1)).save(any(RequisicaoRecuperarSenha.class));
    }

    @Test
    void teste_findBy_Email() {

        var requisicao = new RequisicaoRecuperarSenha();
        requisicao.setHoraRequisicao(LocalDateTime.now());
        requisicao.setEmail("email@pge.br");

        when(repository.findByEmail(any())).thenReturn(Arrays.asList(requisicao));

        List<RequisicaoRecuperarSenha> emails = service.getRequestByEmail("email@pge.br");

        assertNotNull(emails);
    }
}
