package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.EnderecoTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnderecoRequestDtoTest {
  public static EnderecoRequestDto getEndereco() {
    EnderecoRequestDto dto = new EnderecoRequestDto();
    dto.setCep(EnderecoTest.getEndereco().getCep());
    dto.setLogradouro(EnderecoTest.getEndereco().getLogradouro());
    dto.setNumero(EnderecoTest.getEndereco().getNumero());
    dto.setBairro(EnderecoTest.getEndereco().getBairro());
    dto.setComplemento(EnderecoTest.getEndereco().getComplemento());
    dto.setDistrito(EnderecoTest.getEndereco().getDistrito());
    dto.setMunicipio(EnderecoTest.getEndereco().getMunicipio());
    dto.setUf(EnderecoTest.getEndereco().getUf());
    dto.setPrincipal(true);

    return dto;
  }

  public static EnderecoRequestDto getEnderecoDiferente() {
    EnderecoRequestDto dto = new EnderecoRequestDto();
    dto.setCep(EnderecoTest.getEnderecoDiferente().getCep());
    dto.setLogradouro(EnderecoTest.getEnderecoDiferente().getLogradouro());
    dto.setNumero(EnderecoTest.getEnderecoDiferente().getNumero());
    dto.setBairro(EnderecoTest.getEnderecoDiferente().getBairro());
    dto.setComplemento(EnderecoTest.getEnderecoDiferente().getComplemento());
    dto.setDistrito(EnderecoTest.getEnderecoDiferente().getDistrito());
    dto.setMunicipio(EnderecoTest.getEnderecoDiferente().getMunicipio());
    dto.setUf(EnderecoTest.getEnderecoDiferente().getUf());
    dto.setPrincipal(true);

    return dto;
  }
  
  

  @Test
  void teste_endereco_valido() {
    EnderecoRequestDto dto = getEndereco();

    assertEquals(EnderecoTest.getEndereco().getCep(), dto.getCep());
    assertEquals(EnderecoTest.getEndereco().getLogradouro(), dto.getLogradouro());
    assertEquals(EnderecoTest.getEndereco().getNumero(), dto.getNumero());
    assertEquals(EnderecoTest.getEndereco().getBairro(), dto.getBairro());
    assertEquals(EnderecoTest.getEndereco().getComplemento(), dto.getComplemento());
    assertEquals(EnderecoTest.getEndereco().getDistrito(), dto.getDistrito());
    assertEquals(EnderecoTest.getEndereco().getMunicipio(), dto.getMunicipio());
    assertEquals(EnderecoTest.getEndereco().getUf(), dto.getUf());
    assertTrue(dto.getPrincipal());
  }
  
  public static EnderecoRequestDto endereco1() {
	  EnderecoRequestDto endereco = new EnderecoRequestDto();
      endereco.setCep("60360450");
      endereco.setLogradouro("Travessa Congo");
      endereco.setNumero("42");
      endereco.setBairro("Padre Andrade");
      endereco.setComplemento(null);
      endereco.setDistrito("ce");
      endereco.setMunicipio("Fortaleza");
      endereco.setUf("CE");
      return endereco;
  }  
}
