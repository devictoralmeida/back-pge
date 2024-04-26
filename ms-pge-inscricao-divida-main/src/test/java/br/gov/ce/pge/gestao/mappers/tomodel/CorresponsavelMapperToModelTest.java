package br.gov.ce.pge.gestao.mappers.tomodel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.request.CorresponsavelRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Corresponsavel;
import br.gov.ce.pge.gestao.entity.InscricaoTest;
import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;

class CorresponsavelMapperToModelTest {

	@Test
	void teste_converter() {
		
		Corresponsavel model = CorresponsavelMapperToModel.converter(CorresponsavelRequestDtoTest.getCorresponsavelPessoaFisica(), InscricaoTest.getIncricaoCompleta());
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
	void teste_converter_lista() {
		
		List<Corresponsavel> lista = CorresponsavelMapperToModel.converterList(CorresponsavelRequestDtoTest.getListCorresponsaveis(), InscricaoTest.getIncricaoCompleta());
		assertEquals("Osvaldo e Julio Marketing ME", lista.get(1).getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_JURIDICA, lista.get(1).getTipoPessoa());
		assertEquals("86411461000172", lista.get(1).getDocumento());
		assertEquals("075035251", lista.get(1).getCgf());
		assertEquals(TipoContato.WHATSAPP, lista.get(1).getTipoContato());
		assertEquals("85994677445", lista.get(1).getContato());
		assertEquals("seguranca@osvaldoejuliomarketingme.com.br", lista.get(1).getEmail());
		assertEquals("60360450", lista.get(1).getCep());
		assertEquals("Travessa congo", lista.get(1).getLogradouro());
		assertEquals("42", lista.get(1).getNumero());
		assertEquals("Antonio Bezerra", lista.get(1).getBairro());
		assertEquals(null, lista.get(1).getComplemento());
		assertEquals(null, lista.get(1).getDistrito());
		assertEquals("Fortaleza", lista.get(1).getMunicipio());
		assertEquals("CE", lista.get(1).getUf());
	}
	
}
