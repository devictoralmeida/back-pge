package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.QualificacaoCorresponsavelTest;
import br.gov.ce.pge.gestao.entity.TipoDevedorTest;
import br.gov.ce.pge.gestao.entity.TipoPapelPessoaDividaTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DividaPessoaRequestDtoTest {
  public static DividaPessoaRequestDto getDividaPessoaDevedora() {
    DividaPessoaRequestDto dto = new DividaPessoaRequestDto();
    dto.setIdPapelPessoa(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId());
    dto.setIdQualificacaoCorresponsavel(null);
    dto.setIdTipoDevedor(TipoDevedorTest.getTipoDevedorPrincipal().getId());
    return dto;
  }

  public static DividaPessoaRequestDto getDividaPessoaSucessora() {
    DividaPessoaRequestDto dto = new DividaPessoaRequestDto();
    dto.setIdPapelPessoa(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor().getId());
    dto.setIdQualificacaoCorresponsavel(null);
    dto.setIdTipoDevedor(null);
    dto.setDeclaracaoAusenciaContatos(true);
    return dto;
  }

  public static DividaPessoaRequestDto getDividaPessoaCorresponsavel() {
    DividaPessoaRequestDto dto = new DividaPessoaRequestDto();
    dto.setIdPapelPessoa(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel().getId());
    dto.setIdQualificacaoCorresponsavel(QualificacaoCorresponsavelTest.getQualificacao().getId());
    dto.setIdTipoDevedor(null);
    dto.setDeclaracaoAusenciaContatos(true);
    return dto;
  }

  public static List<DividaPessoaRequestDto> getDividaPessoasList() {
    return Arrays.asList(getDividaPessoaDevedora(), getDividaPessoaSucessora(), getDividaPessoaCorresponsavel());
  }

  @Test
  void teste_pessoa_devedora() {
    DividaPessoaRequestDto dto = getDividaPessoaDevedora();
    assertNotNull(dto);
    assertEquals(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId(), dto.getIdPapelPessoa());
    assertNull(dto.getIdQualificacaoCorresponsavel());
    assertEquals(TipoDevedorTest.getTipoDevedorPrincipal().getId(), dto.getIdTipoDevedor());
    assertFalse(dto.getDeclaracaoAusenciaContatos());
  }
}
