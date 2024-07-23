package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.PessoaTest;
import br.gov.ce.pge.gestao.entity.TipoPessoaTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaRequestDtoTest {
  public static PessoaRequestDto getPessoaDevedora() {
    PessoaRequestDto dto = new PessoaRequestDto();
    dto.setDocumento(PessoaTest.getPessoaDevedora().getDocumento());
    dto.setNomeRazaoSocial(PessoaTest.getPessoaDevedora().getNomeRazaoSocial());
    dto.setCgf(null);
    dto.setIdTipoPessoa(TipoPessoaTest.getTipoPessoaFisica().getId());
    dto.setDividaPessoa(DividaPessoaRequestDtoTest.getDividaPessoaDevedora());
    dto.setEndereco(EnderecoRequestDtoTest.getEndereco());
    dto.setContatos(ContatoRequestDtoTest.getContatosList());
    return dto;
  }

  public static PessoaRequestDto getPessoaSucessora() {
    PessoaRequestDto dto = new PessoaRequestDto();
    dto.setDocumento(PessoaTest.getPessoaSucessora().getDocumento());
    dto.setNomeRazaoSocial(PessoaTest.getPessoaSucessora().getNomeRazaoSocial());
    dto.setCgf(PessoaTest.getPessoaSucessora().getCgf());
    dto.setIdTipoPessoa(TipoPessoaTest.getTipoPessoaJuridica().getId());
    dto.setDividaPessoa(DividaPessoaRequestDtoTest.getDividaPessoaSucessora());
    dto.setEndereco(EnderecoRequestDtoTest.getEndereco());
    dto.setContatos(null);
    return dto;
  }

  public static PessoaRequestDto getPessoaCorresponsavel() {
    PessoaRequestDto dto = new PessoaRequestDto();
    dto.setDocumento(PessoaTest.getPessoaCorresponsavel().getDocumento());
    dto.setNomeRazaoSocial(PessoaTest.getPessoaCorresponsavel().getNomeRazaoSocial());
    dto.setCgf(PessoaTest.getPessoaCorresponsavel().getCgf());
    dto.setIdTipoPessoa(TipoPessoaTest.getTipoPessoaJuridica().getId());
    dto.setDividaPessoa(DividaPessoaRequestDtoTest.getDividaPessoaCorresponsavel());
    dto.setEndereco(EnderecoRequestDtoTest.getEndereco());
    dto.setContatos(null);
    return dto;
  }

  public static List<PessoaRequestDto> getPessoasList() {
    return Arrays.asList(getPessoaDevedora(), getPessoaSucessora(), getPessoaCorresponsavel());
  }

  @Test
  void teste_pessoa_devedora() {
    PessoaRequestDto dto = getPessoaDevedora();

    assertEquals(PessoaTest.getPessoaDevedora().getDocumento(), dto.getDocumento());
    assertEquals(PessoaTest.getPessoaDevedora().getNomeRazaoSocial(), dto.getNomeRazaoSocial());
    assertNull(dto.getCgf());
    assertEquals(TipoPessoaTest.getTipoPessoaFisica().getId(), dto.getIdTipoPessoa());
    assertNotNull(dto.getDividaPessoa());
    assertNotNull(dto.getEndereco());
  }
}
