package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.entity.AcaoJudicial;
import br.gov.ce.pge.gestao.entity.AcaoJudicialTest;
import br.gov.ce.pge.gestao.repository.AcaoJudicialRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcaoJudicialServiceImplTest {

  @Mock
  private AcaoJudicialRepository acaoJudicialRepository;

  @InjectMocks
  private AcaoJudicialServiceImpl acaoJudicialService;

  private AcaoJudicial acaoJudicial;

  @BeforeEach
  void setUp() {
    acaoJudicial = new AcaoJudicial();
    acaoJudicial.setId(UUID.randomUUID());
  }

  @Test
  void test_save() {
    acaoJudicialService.save(AcaoJudicialTest.getAcaoJudicial());
    verify(acaoJudicialRepository, times(1)).save(any(AcaoJudicial.class));
  }

  @Test
  void teste_find_all_sucesso() {
    when(acaoJudicialRepository.findAll()).thenReturn(Arrays.asList(acaoJudicial));

    List<AcaoJudicial> result = acaoJudicialService.findAll();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    verify(acaoJudicialRepository).findAll();
  }

  @Test
  void teste_find_by_id_sucesso() {
    UUID id = acaoJudicial.getId();
    when(acaoJudicialRepository.findById(id)).thenReturn(Optional.of(acaoJudicial));

    AcaoJudicial result = acaoJudicialService.findByIdModel(id);

    assertNotNull(result);
    assertEquals(acaoJudicial.getId(), result.getId());
    verify(acaoJudicialRepository).findById(id);
  }

  @Test
  void teste_exception_find_by_id() {
    UUID id = UUID.randomUUID();
    when(acaoJudicialRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NegocioException.class, () -> acaoJudicialService.findByIdModel(id));

    verify(acaoJudicialRepository).findById(id);
  }
}
