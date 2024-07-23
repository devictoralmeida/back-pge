package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDto;
import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDto;
import br.gov.ce.pge.gestao.entity.EnderecoTest;
import br.gov.ce.pge.gestao.entity.Pessoa;
import br.gov.ce.pge.gestao.entity.PessoaTest;
import br.gov.ce.pge.gestao.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaPessoaServiceImplTest {
  @Mock
  private PessoaRepository repository;

  @InjectMocks
  private ConsultaPessoaServiceImpl service;

  @Test
  void test_consulta_documento_sucesso() {
    Pessoa pessoa = PessoaTest.getPessoaDevedora();
    pessoa.setEnderecos(Arrays.asList(EnderecoTest.getEndereco()));

    when(repository.findByDocumento(any())).thenReturn(Optional.of(pessoa));

    PessoaResponseDto response = service.findByDocumento(DocumentoRequestDtoTest.getDocumento());

    assertNotNull(response);
    verify(repository, times(1)).findByDocumento(any());
  }

  @Test
  void test_consulta_retorno_null() {
    when(repository.findByDocumento(any())).thenReturn(Optional.empty());
    DocumentoRequestDto request = new DocumentoRequestDto();
    request.setDocumento("12345678901");

    PessoaResponseDto response = service.findByDocumento(request);

    assertNull(response);
    verify(repository, times(1)).findByDocumento(any());
  }
}
