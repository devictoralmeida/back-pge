package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.PessoaTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentoRequestDtoTest {
  public static DocumentoRequestDto getDocumento() {
    DocumentoRequestDto dto = new DocumentoRequestDto();
    dto.setDocumento(PessoaTest.getPessoaDevedora().getDocumento());
    return dto;
  }

  @Test
  void teste_documento() {
    DocumentoRequestDto dto = getDocumento();
    assertEquals(PessoaTest.getPessoaDevedora().getDocumento(), dto.getDocumento());
  }
}
