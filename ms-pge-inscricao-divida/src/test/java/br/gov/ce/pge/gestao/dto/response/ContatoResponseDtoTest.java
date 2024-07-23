package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.ContatoTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ContatoResponseDtoTest {
  public static ContatoResponseDto getContato() {
    return new ContatoResponseDto(ContatoTest.getContatoCelular());
  }

  public static List<ContatoResponseDto> getContatos() {
    return Arrays.asList(ContatoResponseDtoTest.getContato(), new ContatoResponseDto(ContatoTest.getContatoEmail()));
  }

  @Test
  void test_contato() {
    ContatoResponseDto dto = ContatoResponseDtoTest.getContato();

    Assertions.assertNotNull(dto);
    Assertions.assertEquals(ContatoTest.getContatoCelular().getId(), dto.getId());
    Assertions.assertEquals(ContatoTest.getContatoCelular().getValorContato(), dto.getValorContato());
    Assertions.assertEquals(ContatoTest.getContatoCelular().getTipoContato().getId(), dto.getIdTipoContato());
    Assertions.assertEquals(ContatoTest.getContatoCelular().getNumeroDdiContato(), dto.getNumeroDdiContato());
  }
}
