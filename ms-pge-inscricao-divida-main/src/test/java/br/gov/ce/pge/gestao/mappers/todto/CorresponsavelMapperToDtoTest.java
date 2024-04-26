package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.CorresponsavelResponseDto;
import br.gov.ce.pge.gestao.entity.CorresponsavelTest;
import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;

class CorresponsavelMapperToDtoTest {

	@Test
	void teste_converter() {
		CorresponsavelResponseDto dto = CorresponsavelMapperToDto.converter(CorresponsavelTest.getCorresponsavelPessoaFisica());
		
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
	void teste_converter_lista() {
		List<CorresponsavelResponseDto> listaDto = CorresponsavelMapperToDto.converterList(CorresponsavelTest.getListCorresponsaveis());
		
		assertEquals(2, listaDto.size());
		assertEquals("Kamila Lima", listaDto.get(0).getNomeRazaoSocial());
		assertEquals(TipoPessoa.PESSOA_FISICA, listaDto.get(0).getTipoPessoa());
		assertEquals("03174180332", listaDto.get(0).getDocumento());
		assertEquals(null, listaDto.get(0).getCgf());
		assertEquals(TipoContato.TELEFONE_CELULAR, listaDto.get(0).getTipoContato());
		assertEquals("85999343912", listaDto.get(0).getContato());
		assertEquals("kamila@gmail.com", listaDto.get(0).getEmail());
		assertEquals("60360450", listaDto.get(0).getCep());
		assertEquals("Travessa congo", listaDto.get(0).getLogradouro());
		assertEquals("42", listaDto.get(0).getNumero());
		assertEquals("Antonio Bezerra", listaDto.get(0).getBairro());
		assertEquals(null, listaDto.get(0).getComplemento());
		assertEquals(null, listaDto.get(0).getDistrito());
		assertEquals("Fortaleza", listaDto.get(0).getMunicipio());
		assertEquals("CE", listaDto.get(0).getUf());
		
	}

}
