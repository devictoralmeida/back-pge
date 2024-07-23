package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TipoReceitaResponseDtoTest {
  public static TipoReceitaResponseDto getTipoReceitaTributaria() {
    TipoReceitaResponseDto dto = new TipoReceitaResponseDto();
    dto.setId(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"));
    dto.setCodigo("0001");
    dto.setDescricao("Receita 01");
    dto.setNatureza(Natureza.TRIBUTARIA);
    dto.setSituacao(Situacao.ATIVA);
    dto.setOrigemDebitos(List.of(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386")));
    return dto;
  }

  public static List<TipoReceitaResponseDto> getList() {
    return List.of(getTipoReceitaTributaria());
  }

  public static TipoReceitaResponseDto getTipoReceitaTributariaDivida() {
    TipoReceitaResponseDto dto = new TipoReceitaResponseDto();
    dto.setId(UUID.fromString("b1d83b79-6730-4e93-ab33-ae7ff45c9868"));
    dto.setCodigo("0001");
    dto.setDescricao("Receita 01");
    dto.setNatureza(Natureza.TRIBUTARIA);
    dto.setSituacao(Situacao.ATIVA);
    dto.setOrigemDebitos(List.of(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386")));
    return dto;
  }

  public static TipoReceitaResponseDto getTipoReceitaNaoTributaria() {
    TipoReceitaResponseDto dto = new TipoReceitaResponseDto();
    dto.setId(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212"));
    dto.setCodigo("0002");
    dto.setDescricao("Receita 02");
    dto.setNatureza(Natureza.NAO_TRIBUTARIA);
    dto.setSituacao(Situacao.ATIVA);
    dto.setOrigemDebitos(List.of(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386")));
    return dto;
  }

  public static TipoReceitaResponseDto getTipoReceitaNaoTributariaDivida() {
    TipoReceitaResponseDto dto = new TipoReceitaResponseDto();
    dto.setId(UUID.fromString("b1d83b79-6730-4e93-ab33-ae7ff45c9868"));
    dto.setCodigo("0002");
    dto.setDescricao("Receita 02");
    dto.setNatureza(Natureza.NAO_TRIBUTARIA);
    dto.setSituacao(Situacao.ATIVA);
    dto.setOrigemDebitos(List.of(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386")));
    return dto;
  }

  @Test
  void test_criacao_tipo_receita() {
    TipoReceitaResponseDto dto = getTipoReceitaTributaria();

    Assertions.assertNotNull(dto);
    Assertions.assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), dto.getId());
    Assertions.assertEquals("Receita 01", dto.getDescricao());
    Assertions.assertEquals("0001", dto.getCodigo());
    Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
    Assertions.assertEquals(Natureza.TRIBUTARIA, dto.getNatureza());
    final long tamanhoEsperado = 1;
    Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
  }

  @Test
  void test_constructor_and_getters_setters() {
    UUID id = UUID.randomUUID();
    final String codigo = "COD123";
    final String descricao = "Descrição do tipo de receita";
    List<UUID> origemDebitos = new ArrayList<>();
    origemDebitos.add(UUID.randomUUID());
    origemDebitos.add(UUID.randomUUID());
    final Natureza natureza = Natureza.TRIBUTARIA;
    final Situacao situacao = Situacao.ATIVA;
    LocalDateTime dataCriacao = LocalDateTime.now();
    LocalDateTime dataAtualizacao = LocalDateTime.now();

    TipoReceitaResponseDto tipoReceita = new TipoReceitaResponseDto();
    tipoReceita.setId(id);
    tipoReceita.setCodigo(codigo);
    tipoReceita.setDescricao(descricao);
    tipoReceita.setOrigemDebitos(origemDebitos);
    tipoReceita.setNatureza(natureza);
    tipoReceita.setSituacao(situacao);
    tipoReceita.setDataCriacao(dataCriacao);
    tipoReceita.setDataAtualizacao(dataAtualizacao);

    asserts(id, codigo, descricao, origemDebitos, natureza, situacao, dataCriacao, dataAtualizacao, tipoReceita);
  }

  private void asserts(UUID id, String codigo, String descricao, List<UUID> origemDebitos, Natureza natureza, Situacao situacao, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, TipoReceitaResponseDto tipoReceita) {
    Assertions.assertEquals(id, tipoReceita.getId());
    Assertions.assertEquals(codigo, tipoReceita.getCodigo());
    Assertions.assertEquals(descricao, tipoReceita.getDescricao());
    Assertions.assertEquals(origemDebitos, tipoReceita.getOrigemDebitos());
    Assertions.assertEquals(natureza, tipoReceita.getNatureza());
    Assertions.assertEquals(situacao, tipoReceita.getSituacao());
    Assertions.assertEquals(dataCriacao, tipoReceita.getDataCriacao());
    Assertions.assertEquals(dataAtualizacao, tipoReceita.getDataAtualizacao());
  }

  @Test
  void test_default_constructor() {
    TipoReceitaResponseDto tipoReceita = new TipoReceitaResponseDto();

    Assertions.assertNotNull(tipoReceita);
    Assertions.assertNull(tipoReceita.getId());
    Assertions.assertNull(tipoReceita.getCodigo());
    Assertions.assertNull(tipoReceita.getDescricao());
    Assertions.assertNull(tipoReceita.getOrigemDebitos());
    Assertions.assertNull(tipoReceita.getNatureza());
    Assertions.assertNull(tipoReceita.getSituacao());
    Assertions.assertNull(tipoReceita.getDataCriacao());
    Assertions.assertNull(tipoReceita.getDataAtualizacao());
  }

}
