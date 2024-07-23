package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.PessoaTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PessoaResponseDtoTest {
  public static PessoaResponseDto getPessoa() {
    PessoaResponseDto dto = new PessoaResponseDto();
    dto.setId(PessoaTest.getPessoaDevedora().getId());
    dto.setDocumento(PessoaTest.getPessoaDevedora().getDocumento());
    dto.setNomeRazaoSocial(PessoaTest.getPessoaDevedora().getNomeRazaoSocial());
    dto.setCgf(PessoaTest.getPessoaDevedora().getCgf());
    dto.setIdTipoPessoa(PessoaTest.getPessoaDevedora().getTipoPessoa().getId());
    dto.setEndereco(EnderecoResponseDtoTest.getEndereco());
    dto.setContatos(ContatoResponseDtoTest.getContatos());
    return dto;
  }

  @Test
  void test_pessoa() {
    PessoaResponseDto dto = PessoaResponseDtoTest.getPessoa();

    Assertions.assertNotNull(dto);
    Assertions.assertEquals(PessoaTest.getPessoaDevedora().getId(), dto.getId());
    Assertions.assertEquals(PessoaTest.getPessoaDevedora().getDocumento(), dto.getDocumento());
    Assertions.assertEquals(PessoaTest.getPessoaDevedora().getNomeRazaoSocial(), dto.getNomeRazaoSocial());
    Assertions.assertEquals(PessoaTest.getPessoaDevedora().getCgf(), dto.getCgf());
    Assertions.assertEquals(PessoaTest.getPessoaDevedora().getTipoPessoa().getId(), dto.getIdTipoPessoa());
    Assertions.assertNotNull(dto.getEndereco());
    Assertions.assertNotNull(dto.getContatos());
  }
}
