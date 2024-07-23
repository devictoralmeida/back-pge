package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dto.response.VaraOrigemResponseDto;
import br.gov.ce.pge.gestao.entity.VaraOrigem;
import br.gov.ce.pge.gestao.repository.VaraOrigemRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VaraOrigemServiceImplTest {
  @Test
  void teste_find_all() {
    VaraOrigemRepository repository = Mockito.mock(VaraOrigemRepository.class);
    VaraOrigem varaOrigem = new VaraOrigem();
    varaOrigem.setId(UUID.randomUUID());
    varaOrigem.setNome("Test");

    when(repository.findAll()).thenReturn(List.of(varaOrigem));

    VaraOrigemServiceImpl service = new VaraOrigemServiceImpl(repository);
    VaraOrigemResponseDto result = service.findAll().get(0);

    assertEquals(1, service.findAll().size());
    assertEquals(varaOrigem.getId(), result.getId());
    assertEquals(varaOrigem.getNome(), result.getNome());
  }

  @Test
  void teste_find_by_nome_sucesso() {
    VaraOrigemRepository repository = Mockito.mock(VaraOrigemRepository.class);
    VaraOrigem varaOrigem = new VaraOrigem();
    varaOrigem.setNome("Test");
    when(repository.findByNomeIgnoreCase("Test")).thenReturn(Optional.of(varaOrigem));

    VaraOrigemServiceImpl service = new VaraOrigemServiceImpl(repository);

    assertEquals("Test", service.findByNome("Test").getNome());
  }

  @Test
  void teste_exception_find_by_nome_com_tipo_processo_null() {
    VaraOrigemRepository repository = Mockito.mock(VaraOrigemRepository.class);

    VaraOrigemServiceImpl service = new VaraOrigemServiceImpl(repository);

    assertThrows(NegocioException.class, () -> service.findByNome(null));
  }

  @Test
  void teste_save_sucesso() {
    VaraOrigemRepository repository = Mockito.mock(VaraOrigemRepository.class);
    VaraOrigem varaOrigem = new VaraOrigem();
    varaOrigem.setNome("Test");
    when(repository.save(Mockito.any(VaraOrigem.class))).thenReturn(varaOrigem);

    VaraOrigemServiceImpl service = new VaraOrigemServiceImpl(repository);

    assertEquals("Test", service.save("Test").getNome());
    verify(repository, times(1)).save(Mockito.any(VaraOrigem.class));
    verifyNoMoreInteractions(repository);
  }
}
