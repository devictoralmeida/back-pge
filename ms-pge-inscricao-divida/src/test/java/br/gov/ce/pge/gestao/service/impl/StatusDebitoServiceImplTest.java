package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.StatusDebito;
import br.gov.ce.pge.gestao.repository.StatusDebitoRepository;
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
class StatusDebitoServiceImplTest {

  @Mock
  private StatusDebitoRepository repository;

  @InjectMocks
  private StatusDebitoServiceImpl service;

  private StatusDebito statusDebito1;
  private StatusDebito statusDebito2;

  @BeforeEach
  void setUp() {
    statusDebito1 = new StatusDebito();
    statusDebito1.setId(UUID.randomUUID());

    statusDebito2 = new StatusDebito();
    statusDebito2.setId(UUID.randomUUID());
  }

  @Test
  void buscar_todos_retorna_lista_de_status_debito() {
    when(repository.findAll()).thenReturn(Arrays.asList(statusDebito1, statusDebito2));
    List<StatusDebito> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_status_debito() {
    UUID id = statusDebito1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(statusDebito1));
    StatusDebito resultado = service.findById(id);
    assertEquals(statusDebito1, resultado);
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
