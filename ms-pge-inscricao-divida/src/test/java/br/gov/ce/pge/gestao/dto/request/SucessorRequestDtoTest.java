package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.constants.IdsConstants;

class SucessorRequestDtoTest {

	public static SucessorRequestDto getRequestSucessorPessoaFisica() {

		SucessorRequestDto pessoa = new SucessorRequestDto();
		pessoa.setIdTipoPessoa(IdsConstants.ID_TIPO_PESSOA_FISICA);
		pessoa.setDocumento("03174180333");
		pessoa.setNomeRazaoSocial("nome do sucessor");
		pessoa.setCgf(null);
		pessoa.setEndereco(EnderecoRequestDtoTest.endereco1());
		pessoa.setContatos(Arrays.asList(ContatoRequestDtoTest.contatoTelefone1()));

		pessoa.setDeclaracaoAusenciaContato(false);
		pessoa.setIdsDivida(Arrays.asList(UUID.fromString("676703be-4c14-4fe2-bdc3-70598cfea3f1"),
				UUID.fromString("a1707b98-8406-418e-bb13-e3edfbf28815")));
		pessoa.setLiberaDividaComSucessor(false);
		pessoa.setLiberaDevedoresDistinto(false);
		pessoa.setLiberaDividaFaseFinalistica(false);

		return pessoa;
	}
	
	public static SucessorRequestDto getRequestSucessorPessoaFisica_libera(Boolean liberaDividaComSucessor, 
			Boolean liberaDevedoresDistinto, Boolean liberaFaseFinalistica) {

		SucessorRequestDto pessoa = new SucessorRequestDto();
		pessoa.setIdTipoPessoa(IdsConstants.ID_TIPO_PESSOA_FISICA);
		pessoa.setDocumento("03174180333");
		pessoa.setNomeRazaoSocial("nome do sucessor");
		pessoa.setCgf(null);
		pessoa.setEndereco(EnderecoRequestDtoTest.endereco1());
		pessoa.setContatos(Arrays.asList(ContatoRequestDtoTest.contatoTelefone1()));

		pessoa.setDeclaracaoAusenciaContato(false);
		pessoa.setIdsDivida(Arrays.asList(UUID.fromString("676703be-4c14-4fe2-bdc3-70598cfea3f1"),
				UUID.fromString("a1707b98-8406-418e-bb13-e3edfbf28815")));
		pessoa.setLiberaDividaComSucessor(liberaDividaComSucessor);
		pessoa.setLiberaDevedoresDistinto(liberaDevedoresDistinto);
		pessoa.setLiberaDividaFaseFinalistica(liberaFaseFinalistica);

		return pessoa;
	}

	@Test
	void teste_request() {
		SucessorRequestDto pessoa = getRequestSucessorPessoaFisica();
		assertEquals(UUID.fromString("22bb6280-17bd-41ff-b4df-17730fd7ac4f"), pessoa.getIdTipoPessoa());
		assertEquals("03174180333", pessoa.getDocumento());
		assertEquals("nome do sucessor", pessoa.getNomeRazaoSocial());
		assertNull(pessoa.getCgf());
		assertEquals(false, pessoa.getDeclaracaoAusenciaContato());
		assertEquals(UUID.fromString("676703be-4c14-4fe2-bdc3-70598cfea3f1"), pessoa.getIdsDivida().get(0));
		assertEquals(UUID.fromString("a1707b98-8406-418e-bb13-e3edfbf28815"), pessoa.getIdsDivida().get(1));
		assertEquals(false, pessoa.getLiberaDividaComSucessor());
		assertEquals(false, pessoa.getLiberaDevedoresDistinto());
		assertEquals(false, pessoa.getLiberaDividaFaseFinalistica());
	}
}
