package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;

class CorresponsavelResponseDtoTest {

	public static CorresponsavelResponseDto getCorresponsavelPessoaFisica() {
		var dto = new CorresponsavelResponseDto();
		dto.setId(UUID.randomUUID());
		dto.setNomeRazaoSocial("Kamila Lima");
		dto.setTipoPessoa(TipoPessoa.PESSOA_FISICA);
		dto.setDocumento("03174180332");
		dto.setCgf(null);
		dto.setTipoContato(TipoContato.TELEFONE_CELULAR);
		dto.setContato("85999343912");
		dto.setEmail("kamila@gmail.com");
		dto.setCep("60360450");
		dto.setLogradouro("Travessa congo");
		dto.setNumero("42");
		dto.setBairro("Antonio Bezerra");
		dto.setComplemento(null);
		dto.setDistrito(null);
		dto.setMunicipio("Fortaleza");
		dto.setUf("CE");

		return dto;
	}

	public static CorresponsavelResponseDto getCorresponsavelPessoaJuridica() {
		var dto = new CorresponsavelResponseDto();
		dto.setId(UUID.randomUUID());
		dto.setNomeRazaoSocial("Osvaldo e Julio Marketing ME");
		dto.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
		dto.setDocumento("86411461000172");
		dto.setCgf("075035251");
		dto.setTipoContato(TipoContato.WHATSAPP);
		dto.setContato("85994677445");
		dto.setEmail("seguranca@osvaldoejuliomarketingme.com.br");
		dto.setCep("60360450");
		dto.setLogradouro("Travessa congo");
		dto.setNumero("42");
		dto.setBairro("Antonio Bezerra");
		dto.setComplemento(null);
		dto.setDistrito(null);
		dto.setMunicipio("Fortaleza");
		dto.setUf("CE");

		return dto;
	}

	public static List<CorresponsavelResponseDto> getListCorresponsaveis() {
		return Arrays.asList(getCorresponsavelPessoaFisica(), getCorresponsavelPessoaJuridica());
	}

	@Test
	public void teste_pessoa_fisica() {
		var dto = getCorresponsavelPessoaFisica();

		assertNotNull(dto.getId());
		assertEquals("Kamila Lima", dto.getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_FISICA, dto.getTipoPessoa());
		assertEquals("03174180332", dto.getDocumento());
		assertEquals(null, dto.getCgf());
		assertEquals(TipoContato.TELEFONE_CELULAR, dto.getTipoContato());
		assertEquals("85999343912", dto.getContato());
		assertEquals("kamila@gmail.com", dto.getEmail());
		assertEquals("60360450", dto.getCep());
		assertEquals("Travessa congo", dto.getLogradouro());
		assertEquals("42", dto.getNumero());
		assertEquals("Antonio Bezerra", dto.getBairro());
		assertEquals(null, dto.getComplemento());
		assertEquals(null, dto.getDistrito());
		assertEquals("Fortaleza", dto.getMunicipio());
		assertEquals("CE", dto.getUf());
	}

	@Test
	public void teste_pessoa_juridica() {
		var dto = getCorresponsavelPessoaJuridica();

		assertNotNull(dto.getId());
		assertEquals("Osvaldo e Julio Marketing ME", dto.getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_JURIDICA, dto.getTipoPessoa());
		assertEquals("86411461000172", dto.getDocumento());
		assertEquals("075035251", dto.getCgf());
		assertEquals(TipoContato.WHATSAPP, dto.getTipoContato());
		assertEquals("85994677445", dto.getContato());
		assertEquals("seguranca@osvaldoejuliomarketingme.com.br", dto.getEmail());
		assertEquals("60360450", dto.getCep());
		assertEquals("Travessa congo", dto.getLogradouro());
		assertEquals("42", dto.getNumero());
		assertEquals("Antonio Bezerra", dto.getBairro());
		assertEquals(null, dto.getComplemento());
		assertEquals(null, dto.getDistrito());
		assertEquals("Fortaleza", dto.getMunicipio());
		assertEquals("CE", dto.getUf());
	}

}
