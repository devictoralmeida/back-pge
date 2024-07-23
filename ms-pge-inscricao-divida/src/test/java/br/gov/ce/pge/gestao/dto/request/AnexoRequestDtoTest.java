package br.gov.ce.pge.gestao.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnexoRequestDtoTest {
  public static AnexoRequestDto get_anexo() {
    AnexoRequestDto dto = new AnexoRequestDto();
    dto.setNome("nome");
    dto.setTipoDocumento("Ofício");
    MockMultipartFile arquivo = new MockMultipartFile("file", "filename.pdf", "application/pdf", "content".getBytes());
    dto.setArquivo(arquivo);
    return dto;
  }

  public static List<AnexoRequestDto> get_anexos_list() {
    AnexoRequestDto dto = new AnexoRequestDto();
    dto.setNome("teste");
    dto.setTipoDocumento("Documento");
    MockMultipartFile arquivo = new MockMultipartFile("file", "filename.pdf", "application/pdf", "content".getBytes());
    dto.setArquivo(arquivo);

    return List.of(get_anexo(), dto);
  }

  @Test
  @DisplayName("Teste getters e setters")
  void test_getters_and_setters() {
    AnexoRequestDto anexoRequest = get_anexo();

    final String nome = "nome";
    anexoRequest.setNome(nome);
    assertEquals(nome, anexoRequest.getNome());

    final String tipoDocumento = "Ofício";
    anexoRequest.setTipoDocumento(tipoDocumento);
    assertEquals(tipoDocumento, anexoRequest.getTipoDocumento());

    MockMultipartFile arquivo = new MockMultipartFile("file", "filename.pdf", "application/pdf", "content".getBytes());
    anexoRequest.setArquivo(arquivo);
    assertEquals(arquivo, anexoRequest.getArquivo());
  }
}
