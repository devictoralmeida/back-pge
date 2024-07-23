package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDto;

import java.util.Arrays;
import java.util.Collections;

class PessoaMapperToDtoTest {

  @Test
  void deve_converter_pessoa_sem_enderecos_e_contatos_para_dto() {
    Pessoa pessoa = new Pessoa();
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaFisica());

    PessoaResponseDto resultado = PessoaMapperToDto.converter(pessoa);

    Assertions.assertNotNull(resultado);
    Assertions.assertNull(resultado.getEndereco());
    Assertions.assertTrue(resultado.getContatos().isEmpty());
    Assertions.assertEquals(TipoPessoaTest.getTipoPessoaFisica().getId(), resultado.getIdTipoPessoa());
  }

  @Test
  void deve_converter_pessoa_com_endereco_principal_para_dto() {
    Pessoa pessoa = new Pessoa();
    Endereco endereco = new Endereco();
    endereco.setPrincipal(true);
    pessoa.setEnderecos(Collections.singletonList(endereco));
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaFisica());

    PessoaResponseDto resultado = PessoaMapperToDto.converter(pessoa);

    Assertions.assertNotNull(resultado);
    Assertions.assertNotNull(resultado.getEndereco());
    Assertions.assertEquals(TipoPessoaTest.getTipoPessoaFisica().getId(), resultado.getIdTipoPessoa());
  }

  @Test
  void deve_converter_pessoa_com_multiplos_contatos_para_dto() {
    Pessoa pessoa = new Pessoa();
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaFisica());
    Contato contato1 = new Contato();
    Contato contato2 = new Contato();
    contato1.setTipoContato(TipoContatoTest.getTipoContatoEmail());
    contato2.setTipoContato(TipoContatoTest.getTipoContatoEmail());
    pessoa.setContatos(Arrays.asList(contato1, contato2));

    PessoaResponseDto resultado = PessoaMapperToDto.converter(pessoa);

    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(2, resultado.getContatos().size());
  }

  @Test
  void deve_manter_endereco_nulo_quando_nenhum_endereco_principal() {
    Pessoa pessoa = new Pessoa();
    Endereco endereco = new Endereco();
    endereco.setPrincipal(false);
    pessoa.setEnderecos(Collections.singletonList(endereco));
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaFisica());

    PessoaResponseDto resultado = PessoaMapperToDto.converter(pessoa);

    Assertions.assertNotNull(resultado);
    Assertions.assertNull(resultado.getEndereco());
  }
}
