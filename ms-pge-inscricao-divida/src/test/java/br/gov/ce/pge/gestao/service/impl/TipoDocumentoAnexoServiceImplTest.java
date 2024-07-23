package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dto.response.TipoDocumentoAnexoResponseDto;
import br.gov.ce.pge.gestao.entity.TipoDocumentoAnexo;
import br.gov.ce.pge.gestao.repository.TipoDocumentoAnexoRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TipoDocumentoAnexoServiceImplTest {

  @Test
  void teste_find_all() {
    TipoDocumentoAnexoRepository repository = Mockito.mock(TipoDocumentoAnexoRepository.class);
    TipoDocumentoAnexo tipoDocumentoAnexo = new TipoDocumentoAnexo();
    tipoDocumentoAnexo.setId(UUID.randomUUID());
    tipoDocumentoAnexo.setTipoDocumento("Test");

    when(repository.findAll()).thenReturn(List.of(tipoDocumentoAnexo));

    TipoDocumentoAnexoServiceImpl service = new TipoDocumentoAnexoServiceImpl(repository);
    TipoDocumentoAnexoResponseDto result = service.findAll().get(0);

    assertEquals(1, service.findAll().size());
    assertEquals(tipoDocumentoAnexo.getId(), result.getId());
    assertEquals(tipoDocumentoAnexo.getTipoDocumento(), result.getTipoDocumento());
  }

  @Test
  void teste_find_by_tipo_documento_sucesso() {
    TipoDocumentoAnexoRepository repository = Mockito.mock(TipoDocumentoAnexoRepository.class);
    TipoDocumentoAnexo tipoDocumentoAnexo = new TipoDocumentoAnexo();
    tipoDocumentoAnexo.setTipoDocumento("Test");
    when(repository.findByTipoDocumento("Test")).thenReturn(Optional.of(tipoDocumentoAnexo));

    TipoDocumentoAnexoServiceImpl service = new TipoDocumentoAnexoServiceImpl(repository);

    assertEquals("Test", service.findByTipoDocumento("Test").getTipoDocumento());
  }

  @Test
  void teste_exception_find_by_tipo_documento_com_tipo_documento_null() {
    TipoDocumentoAnexoRepository repository = Mockito.mock(TipoDocumentoAnexoRepository.class);

    TipoDocumentoAnexoServiceImpl service = new TipoDocumentoAnexoServiceImpl(repository);

    assertThrows(NegocioException.class, () -> service.findByTipoDocumento(null));
  }

  @Test
  void teste_save_sucesso() {
    TipoDocumentoAnexoRepository repository = Mockito.mock(TipoDocumentoAnexoRepository.class);

    TipoDocumentoAnexo tipoDocumentoAnexo = new TipoDocumentoAnexo();
    tipoDocumentoAnexo.setTipoDocumento("Test");

    when(repository.save(Mockito.any(TipoDocumentoAnexo.class))).thenReturn(tipoDocumentoAnexo);

    TipoDocumentoAnexoServiceImpl service = new TipoDocumentoAnexoServiceImpl(repository);

    assertEquals("Test", service.save("Test").getTipoDocumento());
    verify(repository, times(1)).save(Mockito.any(TipoDocumentoAnexo.class));
    verifyNoMoreInteractions(repository);
  }
}
