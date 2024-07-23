package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.TipoDocumento;
import br.gov.ce.pge.gestao.repository.TipoDocumentoRepository;
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
class TipoDocumentoServiceImplTest {

  @Mock
  private TipoDocumentoRepository repository;

  @InjectMocks
  private TipoDocumentoServiceImpl service;

  private TipoDocumento tipoDocumento1;
  private TipoDocumento tipoDocumento2;

  @BeforeEach
  void setUp() {
    tipoDocumento1 = new TipoDocumento();
    tipoDocumento1.setId(UUID.randomUUID());

    tipoDocumento2 = new TipoDocumento();
    tipoDocumento2.setId(UUID.randomUUID());
  }

  @Test
  void buscar_todos_retorna_lista_de_tipo_documento() {
    when(repository.findAll()).thenReturn(Arrays.asList(tipoDocumento1, tipoDocumento2));
    List<TipoDocumento> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_tipo_documento() {
    UUID id = tipoDocumento1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(tipoDocumento1));
    TipoDocumento resultado = service.findById(id);
    assertEquals(tipoDocumento1, resultado);
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
