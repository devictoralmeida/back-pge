package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.EnderecoTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnderecoResponseDtoTest {
  public static EnderecoResponseDto getEndereco() {
    return new EnderecoResponseDto(EnderecoTest.getEndereco());
  }

  @Test
  void test_endereco() {
    EnderecoResponseDto dto = EnderecoResponseDtoTest.getEndereco();

    Assertions.assertNotNull(dto);
    Assertions.assertEquals(EnderecoTest.getEndereco().getId(), dto.getId());
    Assertions.assertEquals(EnderecoTest.getEndereco().getCep(), dto.getCep());
    Assertions.assertEquals(EnderecoTest.getEndereco().getLogradouro(), dto.getLogradouro());
    Assertions.assertEquals(EnderecoTest.getEndereco().getNumero(), dto.getNumero());
    Assertions.assertEquals(EnderecoTest.getEndereco().getBairro(), dto.getBairro());
    Assertions.assertEquals(EnderecoTest.getEndereco().getComplemento(), dto.getComplemento());
    Assertions.assertEquals(EnderecoTest.getEndereco().getDistrito(), dto.getDistrito());
    Assertions.assertEquals(EnderecoTest.getEndereco().getMunicipio(), dto.getMunicipio());

  }
}
