package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.ContatoTest;
import br.gov.ce.pge.gestao.entity.TipoContatoTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContatoRequestDtoTest {
  public static ContatoRequestDto getContatoEmail() {
    ContatoRequestDto dto = new ContatoRequestDto();
    dto.setIdTipoContato(TipoContatoTest.getTipoContatoEmail().getId());
    dto.setValorContato(ContatoTest.getContatoEmail().getValorContato());
    return dto;
  }

  public static List<ContatoRequestDto> getContatosList() {
    List<ContatoRequestDto> contatos = new ArrayList<>();
    ContatoRequestDto contato1 = getContatoEmail();

    ContatoRequestDto contato2 = new ContatoRequestDto();
    contato2.setIdTipoContato(TipoContatoTest.getTipoContatoCelular().getId());
    contato2.setValorContato(ContatoTest.getContatoCelular().getValorContato());
    contato2.setNumeroDdiContato(ContatoTest.getContatoCelular().getNumeroDdiContato());

    contatos.add(contato1);
    contatos.add(contato2);
    return contatos;
  }

  public static List<ContatoRequestDto> getContatosListDiferente() {
    List<ContatoRequestDto> contatos = new ArrayList<>();
    ContatoRequestDto contato1 = new ContatoRequestDto();
    contato1.setIdTipoContato(TipoContatoTest.getTipoContatoEmail().getId());
    contato1.setValorContato("emaildiferente@gmail.com");

    ContatoRequestDto contato2 = new ContatoRequestDto();
    contato2.setIdTipoContato(TipoContatoTest.getTipoContatoCelular().getId());
    contato2.setValorContato("85996803215");
    contato2.setNumeroDdiContato("54");

    contatos.add(contato1);
    contatos.add(contato2);
    return contatos;
  }

  @Test
  void teste_contato() {
    ContatoRequestDto contato = getContatoEmail();
    assertEquals(TipoContatoTest.getTipoContatoEmail().getId(), contato.getIdTipoContato());
    assertEquals(ContatoTest.getContatoEmail().getValorContato(), contato.getValorContato());
  }

  @Test
  void teste_contato_list() {
    List<ContatoRequestDto> contatos = getContatosList();
    assertEquals(2, contatos.size());

    ContatoRequestDto contato1 = contatos.get(0);
    assertEquals(TipoContatoTest.getTipoContatoEmail().getId(), contato1.getIdTipoContato());
    assertEquals(ContatoTest.getContatoEmail().getValorContato(), contato1.getValorContato());

    ContatoRequestDto contato2 = contatos.get(1);
    assertEquals(TipoContatoTest.getTipoContatoCelular().getId(), contato2.getIdTipoContato());
    assertEquals(ContatoTest.getContatoCelular().getValorContato(), contato2.getValorContato());

  }

  
  public static ContatoRequestDto contatoTelefone1() {
	  ContatoRequestDto contato = new ContatoRequestDto();
      contato.setNumeroDdiContato("55");
      contato.setIdTipoContato(UUID.fromString("00503c81-8fd7-4910-9d01-20ce87d8d576"));
      contato.setValorContato("8532350233");
      return contato;
  }
}
