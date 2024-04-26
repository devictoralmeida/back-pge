package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;

public class CorresponsavelTest {

	public static Corresponsavel getCorresponsavelPessoaFisica() {
		var model = new Corresponsavel();
		model.setNomeRazaoSocial("Kamila Lima");
		model.setTipoPessoa(TipoPessoa.PESSOA_FISICA);
		model.setDocumento("03174180332");
		model.setCgf(null);
		model.setTipoContato(TipoContato.TELEFONE_CELULAR);
		model.setContato("85999343912");
		model.setEmail("kamila@gmail.com");
		model.setCep("60360450");
		model.setLogradouro("Travessa congo");
		model.setNumero("42");
		model.setBairro("Antonio Bezerra");
		model.setComplemento(null);
		model.setDistrito(null);
		model.setMunicipio("Fortaleza");
		model.setUf("CE");
		
		return model;
	}
	
	public static Corresponsavel getCorresponsavelPessoaJuridica() {
		var model = new Corresponsavel();
		model.setNomeRazaoSocial("Osvaldo e Julio Marketing ME");
		model.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
		model.setDocumento("86411461000172");
		model.setCgf("075035251");
		model.setTipoContato(TipoContato.WHATSAPP);
		model.setContato("85994677445");
		model.setEmail("seguranca@osvaldoejuliomarketingme.com.br");
		model.setCep("60360450");
		model.setLogradouro("Travessa congo");
		model.setNumero("42");
		model.setBairro("Antonio Bezerra");
		model.setComplemento(null);
		model.setDistrito(null);
		model.setMunicipio("Fortaleza");
		model.setUf("CE");
		
		return model;
	}
	
	public static List<Corresponsavel> getListCorresponsaveis() {
		return Arrays.asList(getCorresponsavelPessoaFisica(),getCorresponsavelPessoaJuridica());
	}
	
	@Test
	public void teste_pessoa_fisica() {
		var model = getCorresponsavelPessoaFisica();
		
		assertEquals("Kamila Lima", model.getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_FISICA, model.getTipoPessoa());
		assertEquals("03174180332", model.getDocumento());
		assertEquals(null, model.getCgf());
		assertEquals(TipoContato.TELEFONE_CELULAR, model.getTipoContato());
		assertEquals("85999343912", model.getContato());
		assertEquals("kamila@gmail.com", model.getEmail());
		assertEquals("60360450", model.getCep());
		assertEquals("Travessa congo", model.getLogradouro());
		assertEquals("42", model.getNumero());
		assertEquals("Antonio Bezerra", model.getBairro());
		assertEquals(null, model.getComplemento());
		assertEquals(null, model.getDistrito());
		assertEquals("Fortaleza", model.getMunicipio());
		assertEquals("CE", model.getUf());
	}
	
	@Test
	public void teste_pessoa_juridica() {
		var model = getCorresponsavelPessoaJuridica();
		
		assertEquals("Osvaldo e Julio Marketing ME", model.getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_JURIDICA, model.getTipoPessoa());
		assertEquals("86411461000172", model.getDocumento());
		assertEquals("075035251", model.getCgf());
		assertEquals(TipoContato.WHATSAPP, model.getTipoContato());
		assertEquals("85994677445", model.getContato());
		assertEquals("seguranca@osvaldoejuliomarketingme.com.br", model.getEmail());
		assertEquals("60360450", model.getCep());
		assertEquals("Travessa congo", model.getLogradouro());
		assertEquals("42", model.getNumero());
		assertEquals("Antonio Bezerra", model.getBairro());
		assertEquals(null, model.getComplemento());
		assertEquals(null, model.getDistrito());
		assertEquals("Fortaleza", model.getMunicipio());
		assertEquals("CE", model.getUf());
	}

}
