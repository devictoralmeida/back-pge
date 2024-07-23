package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.TipoPessoa;
import br.gov.ce.pge.gestao.repository.TipoPessoaRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoConsultaPessoaServiceImplTest {

  @Mock
  private TipoPessoaRepository repository;

  @InjectMocks
  private TipoPessoaServiceImpl service;

  private TipoPessoa tipoPessoa1;
  private TipoPessoa tipoPessoa2;

  @BeforeEach
  void configurar() {
    tipoPessoa1 = new TipoPessoa();
    tipoPessoa1.setId(UUID.randomUUID());
    tipoPessoa1.setNome("Pessoa Física");

    tipoPessoa2 = new TipoPessoa();
    tipoPessoa2.setId(UUID.randomUUID());
    tipoPessoa2.setNome("Pessoa Jurídica");
  }

  @Test
  void buscar_todos_retorna_lista_de_tipo_pessoa() {
    when(repository.findAll()).thenReturn(Arrays.asList(tipoPessoa1, tipoPessoa2));
    List<TipoPessoa> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_tipo_pessoa() {
    UUID id = tipoPessoa1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(tipoPessoa1));
    TipoPessoa resultado = service.findById(id);
    assertEquals(tipoPessoa1, resultado);
    verify(repository).findById(id);
  }

  @Test
  void buscar_por_id_lanca_excecao_quando_nao_encontrado() {
    UUID id = UUID.randomUUID();
    when(repository.findById(id)).thenReturn(Optional.empty());
    assertThrows(NegocioException.class, () -> service.findById(id));
    verify(repository).findById(id);
  }
}
