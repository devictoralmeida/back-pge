package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoDocumentoAnexoResponseDtoTest {
  public static TipoDocumentoAnexoResponseDto get_tipo_documento_anexo() {
    TipoDocumentoAnexoResponseDto dto = new TipoDocumentoAnexoResponseDto();
    dto.setId(UUID.fromString("e45a9b9f-8bd2-4e8c-a98f-abb5743209fa"));
    dto.setTipoDocumento("Nota Fiscal");
    return dto;
  }

  @Test
  @DisplayName("Teste getters e setters")
  public void test_getters_and_setters() {
    TipoDocumentoAnexoResponseDto tipoDocumentoAnexoResponseDto = get_tipo_documento_anexo();
    assertEquals("e45a9b9f-8bd2-4e8c-a98f-abb5743209fa", tipoDocumentoAnexoResponseDto.getId().toString());
    assertEquals("Nota Fiscal", tipoDocumentoAnexoResponseDto.getTipoDocumento());
  }
}
