package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.TipoPapelPessoaDivida;
import br.gov.ce.pge.gestao.repository.TipoPapelPessoaDividaRepository;
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
class TipoPapelPessoaDividaServiceImplTest {

  @Mock
  private TipoPapelPessoaDividaRepository repository;

  @InjectMocks
  private TipoPapelPessoaDividaServiceImpl service;

  private TipoPapelPessoaDivida tipoPapelPessoaDivida1;
  private TipoPapelPessoaDivida tipoPapelPessoaDivida2;

  @BeforeEach
  void setUp() {
    tipoPapelPessoaDivida1 = new TipoPapelPessoaDivida();
    tipoPapelPessoaDivida1.setId(UUID.randomUUID());

    tipoPapelPessoaDivida2 = new TipoPapelPessoaDivida();
    tipoPapelPessoaDivida2.setId(UUID.randomUUID());
  }

  @Test
  void buscar_todos_retorna_lista_de_tipo_papel_pessoa_divida() {
    when(repository.findAll()).thenReturn(Arrays.asList(tipoPapelPessoaDivida1, tipoPapelPessoaDivida2));
    List<TipoPapelPessoaDivida> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_tipo_papel_pessoa_divida() {
    UUID id = tipoPapelPessoaDivida1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(tipoPapelPessoaDivida1));
    TipoPapelPessoaDivida resultado = service.findById(id);
    assertEquals(tipoPapelPessoaDivida1, resultado);
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
