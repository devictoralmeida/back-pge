package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dto.response.TipoProcessoResponseDto;
import br.gov.ce.pge.gestao.entity.TipoProcesso;
import br.gov.ce.pge.gestao.repository.TipoProcessoRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TipoProcessoServiceImplTest {

  @Test
  void teste_find_all() {
    TipoProcessoRepository repository = Mockito.mock(TipoProcessoRepository.class);
    TipoProcesso tipoProcesso = new TipoProcesso();
    tipoProcesso.setId(UUID.randomUUID());
    tipoProcesso.setNome("Test");

    when(repository.findAll()).thenReturn(List.of(tipoProcesso));

    TipoProcessoServiceImpl service = new TipoProcessoServiceImpl(repository);
    TipoProcessoResponseDto result = service.findAll().get(0);

    assertEquals(1, service.findAll().size());
    assertEquals(tipoProcesso.getId(), result.getId());
    assertEquals(tipoProcesso.getNome(), result.getNome());
  }

  @Test
  void teste_find_by_nome_sucesso() {
    TipoProcessoRepository repository = Mockito.mock(TipoProcessoRepository.class);
    TipoProcesso tipoProcesso = new TipoProcesso();
    tipoProcesso.setNome("Test");
    when(repository.findByNomeIgnoreCase("Test")).thenReturn(Optional.of(tipoProcesso));

    TipoProcessoServiceImpl service = new TipoProcessoServiceImpl(repository);

    assertEquals("Test", service.findByNome("Test").getNome());
  }

  @Test
  void teste_exception_find_by_nome_com_tipo_processo_null() {
    TipoProcessoRepository repository = Mockito.mock(TipoProcessoRepository.class);

    TipoProcessoServiceImpl service = new TipoProcessoServiceImpl(repository);

    assertThrows(NegocioException.class, () -> service.findByNome(null));
  }

  @Test
  void teste_save_sucesso() {
    TipoProcessoRepository repository = Mockito.mock(TipoProcessoRepository.class);
    TipoProcesso tipoProcesso = new TipoProcesso();
    tipoProcesso.setNome("Test");
    when(repository.save(Mockito.any(TipoProcesso.class))).thenReturn(tipoProcesso);

    TipoProcessoServiceImpl service = new TipoProcessoServiceImpl(repository);

    assertEquals("Test", service.save("Test").getNome());
    verify(repository, times(1)).save(Mockito.any(TipoProcesso.class));
    verifyNoMoreInteractions(repository);
  }
}
