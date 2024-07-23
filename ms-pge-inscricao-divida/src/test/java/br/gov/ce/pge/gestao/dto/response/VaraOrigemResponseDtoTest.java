package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VaraOrigemResponseDtoTest {
  public static VaraOrigemResponseDto get_vara_origem() {
    VaraOrigemResponseDto dto = new VaraOrigemResponseDto();
    dto.setId(UUID.randomUUID());
    dto.setNome("vara teste");
    return dto;
  }

  @Test
  void teste_vara_origem() {
    VaraOrigemResponseDto dto = get_vara_origem();
    assertEquals("vara teste", dto.getNome());
    assertNotNull(dto.getId());
  }
}
