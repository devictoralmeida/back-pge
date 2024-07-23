package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.QualificacaoCorresponsavel;
import br.gov.ce.pge.gestao.repository.QualificacaoCorresponsavelRepository;
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
class QualificacaoCorresponsavelServiceImplTest {

  @Mock
  private QualificacaoCorresponsavelRepository repository;

  @InjectMocks
  private QualificacaoCorresponsavelServiceImpl service;

  private QualificacaoCorresponsavel qualificacaoCorresponsavel1;
  private QualificacaoCorresponsavel qualificacaoCorresponsavel2;

  @BeforeEach
  void setUp() {
    qualificacaoCorresponsavel1 = new QualificacaoCorresponsavel();
    qualificacaoCorresponsavel1.setId(UUID.randomUUID());

    qualificacaoCorresponsavel2 = new QualificacaoCorresponsavel();
    qualificacaoCorresponsavel2.setId(UUID.randomUUID());
  }

  @Test
  void buscar_todos_retorna_lista_de_qualificacao_corresponsavel() {
    when(repository.findAll()).thenReturn(Arrays.asList(qualificacaoCorresponsavel1, qualificacaoCorresponsavel2));
    List<QualificacaoCorresponsavel> resultado = service.findAll();
    assertEquals(2, resultado.size());
    verify(repository).findAll();
  }

  @Test
  void buscar_por_id_retorna_qualificacao_corresponsavel() {
    UUID id = qualificacaoCorresponsavel1.getId();
    when(repository.findById(id)).thenReturn(Optional.of(qualificacaoCorresponsavel1));
    QualificacaoCorresponsavel resultado = service.findById(id);
    assertEquals(qualificacaoCorresponsavel1, resultado);
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
