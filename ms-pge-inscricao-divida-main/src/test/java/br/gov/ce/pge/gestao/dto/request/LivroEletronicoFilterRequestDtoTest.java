package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LivroEletronicoFilterRequestDtoTest {

  private LivroEletronicoFilterRequestDto livroFilterRequestDto;

  public static LivroEletronicoFilterRequestDto getRequestFilter() {
    var request = new LivroEletronicoFilterRequestDto();
    request.setNome(null);
    request.setCgf(null);
    request.setCpf(null);
    request.setCnpj(null);
    request.setNumeroInscricao(null);
    request.setNomeRazaoSocial(null);
    request.setLivros(null);
    request.setSituacao(null);
    request.setDataAbertura(null);
    request.setDataFechamento(null);
    return request;
  }

  public static LivroEletronicoFilterRequestDto getRequestFilterComLivros() {
    var request = new LivroEletronicoFilterRequestDto();
    request.setNome(null);
    request.setCgf(null);
    request.setCpf(null);
    request.setCnpj(null);
    request.setNumeroInscricao(null);
    request.setNomeRazaoSocial(null);
    request.setLivros(List.of("2f990d54-d6ff-4c36-a057-0492e7c59119"));
    request.setSituacao(null);
    request.setDataAbertura(null);
    request.setDataFechamento(null);
    return request;
  }

  @BeforeEach
  public void setUp() {
    this.livroFilterRequestDto = new LivroEletronicoFilterRequestDto();
    this.livroFilterRequestDto.setNome("2024");
    this.livroFilterRequestDto.setCgf("890123456");
    this.livroFilterRequestDto.setCpf("84096143081");
    this.livroFilterRequestDto.setCnpj("74691807000157");
    this.livroFilterRequestDto.setNumeroInscricao(UUID.fromString("e6772b0b-4c8a-4311-ae65-5f09c9f9d71d"));
    this.livroFilterRequestDto.setNomeRazaoSocial("Nexus Systems Corporation");
    this.livroFilterRequestDto.setLivros(List.of("Livro1", "Livro2"));
    this.livroFilterRequestDto.setSituacao(SituacaoLivro.ABERTO);
    this.livroFilterRequestDto.setDataAbertura(LocalDateTime.now());
    this.livroFilterRequestDto.setDataFechamento(LocalDateTime.now());
  }

  @Test
  void test_getter_and_setters() {
    assertEquals(this.livroFilterRequestDto.getCpf(), "84096143081");
    assertEquals(this.livroFilterRequestDto.getNome(), "2024");
    assertEquals(this.livroFilterRequestDto.getCgf(), "890123456");
    assertEquals(this.livroFilterRequestDto.getCnpj(), "74691807000157");
    assertEquals(this.livroFilterRequestDto.getNomeRazaoSocial(), "Nexus Systems Corporation");
    assertEquals(this.livroFilterRequestDto.getNumeroInscricao(), UUID.fromString("e6772b0b-4c8a-4311-ae65-5f09c9f9d71d"));
    assertNotNull(this.livroFilterRequestDto.getDataAbertura());
    assertNotNull(this.livroFilterRequestDto.getDataFechamento());

    assertNotNull(this.livroFilterRequestDto.getLivros());
    assertEquals(this.livroFilterRequestDto.getLivros().size(), 2);
    assertEquals(this.livroFilterRequestDto.getSituacao(), SituacaoLivro.ABERTO);
  }

  @Test
  void testFiltersMethod() {
    LivroEletronicoFilterRequestDto dto = new LivroEletronicoFilterRequestDto();

    String nome = "Livro Teste";
    String cgf = "123456789";
    UUID numeroInscricao = UUID.randomUUID();
    LocalDateTime dataAbertura = LocalDateTime.now();
    LocalDateTime dataFechamento = LocalDateTime.now().plusDays(7);

    dto.setNome(nome);
    dto.setCgf(cgf);
    dto.setNumeroInscricao(numeroInscricao);
    dto.setDataAbertura(dataAbertura);
    dto.setDataFechamento(dataFechamento);

    Map<String, Object> filters = dto.filters();

    assertEquals(nome, filters.get("nome"));
    assertEquals(cgf, filters.get("cgf"));
    assertEquals(numeroInscricao, filters.get("numeroInscricao"));
    assertEquals(dataAbertura, filters.get("dataAbertura"));
    assertEquals(dataFechamento, filters.get("dataFechamento"));
  }

  @Test
  void testNoArgsConstructor() {
    LivroEletronicoFilterRequestDto dto = new LivroEletronicoFilterRequestDto();
    assertNotNull(dto);
  }
}
