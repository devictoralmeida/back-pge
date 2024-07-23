package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProdutoServicoResponseDtoTest {
  public static ProdutoServicoResponseDto getProdutoServico() {
    ProdutoServicoResponseDto dto = new ProdutoServicoResponseDto();
    dto.setId(UUID.fromString("a9e28442-86c7-4f05-b0e2-55835191a27a"));
    dto.setCodigo("0001");
    dto.setDescricao("Produto Serviço 01");
    dto.setIdsTipoReceitas(List.of(UUID.randomUUID(), TipoReceitaResponseDtoTest.getTipoReceitaTributaria().getId()));
    dto.setSituacao(Situacao.ATIVA);
    dto.setDataCriacao(LocalDateTime.now());
    return dto;
  }

  @Test
  void test_criacao_produto_servico() {
    ProdutoServicoResponseDto dto = getProdutoServico();
    Assertions.assertNotNull(dto);
    Assertions.assertEquals(UUID.fromString("a9e28442-86c7-4f05-b0e2-55835191a27a"), dto.getId());
    Assertions.assertEquals("Produto Serviço 01", dto.getDescricao());
    Assertions.assertEquals("0001", dto.getCodigo());
    Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
    Assertions.assertNotNull(dto.getDataCriacao());
    Assertions.assertNotNull(dto.getIdsTipoReceitas());
    Assertions.assertNull(dto.getDataAtualizacao());
  }

}
