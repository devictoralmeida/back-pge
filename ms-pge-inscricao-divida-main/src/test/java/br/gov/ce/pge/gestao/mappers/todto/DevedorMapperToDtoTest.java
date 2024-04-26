package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.DevedorResponseDto;
import br.gov.ce.pge.gestao.entity.DevedorTest;
import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;

class DevedorMapperToDtoTest {

	@Test
	void teste_converter() {
		DevedorResponseDto dto = DevedorMapperToDto.converter(DevedorTest.getDevedorPessoaFisica());
		
		assertEquals("Ronnys Nascimento", dto.getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_FISICA, dto.getTipoPessoa());
		assertEquals("05459813360", dto.getDocumento());
		assertEquals(null, dto.getCgf());
		assertEquals(TipoContato.TELEFONE_CELULAR, dto.getTipoContato());
		assertEquals("85999343912", dto.getContato());
		assertEquals("ronnys.m@gmail.com", dto.getEmail());
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
