package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.TipoDevedor;
import br.gov.ce.pge.gestao.repository.TipoDevedorRepository;
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
class TipoDevedorServiceImplTest {

  @Mock
  private TipoDevedorRepository repository;

  @InjectMocks
  private TipoDevedorServiceImpl service;

  private TipoDevedor tipoDevedor1;
  private TipoDevedor tipoDevedor2;

  @BeforeEach
  void setUp() {
    tipoDevedor1 = new TipoDevedor();
    tipoDevedor1.setId(UUID.randomUUID());

    tipoDevedor2 = new TipoDevedor();
    tipoDevedor2.setId(UUID.randomUUID());
  }

  @Test
  void buscar_todos_retorna_lista_de_tipo_devedor() {
    when(repository.findAll()).thenReturn(Arrays.asList(tipoDevedor1, tipoDevedor2));
    List<TipoDevedor> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_tipo_devedor() {
    UUID id = tipoDevedor1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(tipoDevedor1));
    TipoDevedor resultado = service.findById(id);
    assertEquals(tipoDevedor1, resultado);
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
