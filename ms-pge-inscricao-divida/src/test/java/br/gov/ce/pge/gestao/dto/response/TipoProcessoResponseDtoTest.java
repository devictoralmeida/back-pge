package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TipoProcessoResponseDtoTest {
  public static TipoProcessoResponseDto get_tipo_processo() {
    TipoProcessoResponseDto tipoProcessoResponseDto = new TipoProcessoResponseDto();
    tipoProcessoResponseDto.setId(UUID.randomUUID());
    tipoProcessoResponseDto.setNome("nome");
    return tipoProcessoResponseDto;
  }

  @Test
  void teste_tipo_processo() {
    TipoProcessoResponseDto tipoProcessoResponseDto = get_tipo_processo();
    assertEquals("nome", tipoProcessoResponseDto.getNome());
    assertNotNull(tipoProcessoResponseDto.getId());
  }
}
