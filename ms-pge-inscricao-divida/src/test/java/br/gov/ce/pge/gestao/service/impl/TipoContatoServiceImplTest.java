package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.TipoContato;
import br.gov.ce.pge.gestao.repository.TipoContatoRepository;
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
class TipoContatoServiceImplTest {

  @Mock
  private TipoContatoRepository repository;

  @InjectMocks
  private TipoContatoServiceImpl service;

  private TipoContato tipoContato1;
  private TipoContato tipoContato2;

  @BeforeEach
  void setUp() {
    tipoContato1 = new TipoContato();
    tipoContato1.setId(UUID.randomUUID());

    tipoContato2 = new TipoContato();
    tipoContato2.setId(UUID.randomUUID());
  }

  @Test
  void buscar_todos_retorna_lista_de_tipo_contato() {
    when(repository.findAll()).thenReturn(Arrays.asList(tipoContato1, tipoContato2));
    List<TipoContato> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_tipo_contato() {
    UUID id = tipoContato1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(tipoContato1));
    TipoContato resultado = service.findById(id);
    assertEquals(tipoContato1, resultado);
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
