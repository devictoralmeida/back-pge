package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.CodigoVerificacao;
import br.gov.ce.pge.gestao.repository.CodigoVerificacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({ MockitoExtension.class })
public class CodigoVerificacaoServiceImplTest {

    @Mock
    private CodigoVerificacaoRepository repository;

    @InjectMocks
    private CodigoVerificacaoServiceImpl service;

    @Test
    void test_save_codigo() {

        var codigo = new CodigoVerificacao();
        codigo.setCodigo("123456");
        codigo.setDataExpiracao(LocalDateTime.now());

        when(repository.save(any())).thenReturn(codigo);

        var save = service.save(codigo);

        assertNotNull(save);
    }

}
