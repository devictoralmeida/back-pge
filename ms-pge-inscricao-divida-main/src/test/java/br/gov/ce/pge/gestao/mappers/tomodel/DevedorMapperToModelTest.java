package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.DevedorRequestDto;
import br.gov.ce.pge.gestao.dto.request.DevedorRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Devedor;
import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DevedorMapperToModelTest {

    @Test
    void teste_converter() {
        Devedor model = DevedorMapperToModel.converter(DevedorRequestDtoTest.getDevedorPessoaJuridica());

        assertEquals("Sabrina e Sophia Contábil Ltda", model.getNomeRazaoSocial());
        assertEquals(TipoPessoa.PESSOA_JURIDICA, model.getTipoPessoa());
        assertEquals("77657073000185", model.getDocumento());
        assertEquals("075035251", model.getCgf());
        assertEquals(TipoContato.WHATSAPP, model.getTipoContato());
        assertEquals("85999343912", model.getContato());
        assertEquals("contato@sabrinaesophiacontabilltda.com.br", model.getEmail());
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
    void teste_verifica_tipo_pessoa_devedor() {
        DevedorRequestDto request = new DevedorRequestDto();
        request.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);

        assertThrows(NegocioException.class, () -> DevedorMapperToModel.verificaTipoPessoaDevedor(request));
    }

}
