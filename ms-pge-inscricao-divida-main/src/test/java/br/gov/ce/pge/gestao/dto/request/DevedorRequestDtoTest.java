package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevedorRequestDtoTest {

    public static DevedorRequestDto get_devedor_pessoa_fisica() {
        var dto = new DevedorRequestDto();
        dto.setNomeRazaoSocial("Ronnys Nascimento");
        dto.setTipoPessoa(TipoPessoa.PESSOA_FISICA);
        dto.setDocumento("05459813360");
        dto.setCgf(null);
        dto.setTipoContato(TipoContato.TELEFONE_CELULAR);
        dto.setContato("85999343912");
        dto.setEmail("ronnys.m@gmail.com");
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

    public static DevedorRequestDto getDevedorPessoaJuridica() {
        var dto = new DevedorRequestDto();
        dto.setNomeRazaoSocial("Sabrina e Sophia Contábil Ltda");
        dto.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
        dto.setDocumento("77657073000185");
        dto.setCgf("075035251");
        dto.setTipoContato(TipoContato.WHATSAPP);
        dto.setContato("85999343912");
        dto.setEmail("contato@sabrinaesophiacontabilltda.com.br");
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

    @Test
    public void teste_pessoa_fisica() {
        var dto = get_devedor_pessoa_fisica();

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

    @Test
    public void teste_pessoa_juridica() {
        var dto = getDevedorPessoaJuridica();

        assertEquals("Sabrina e Sophia Contábil Ltda", dto.getNomeRazaoSocial());
        assertEquals(TipoPessoa.PESSOA_JURIDICA, dto.getTipoPessoa());
        assertEquals("77657073000185", dto.getDocumento());
        assertEquals("075035251", dto.getCgf());
        assertEquals(TipoContato.WHATSAPP, dto.getTipoContato());
        assertEquals("85999343912", dto.getContato());
        assertEquals("contato@sabrinaesophiacontabilltda.com.br", dto.getEmail());
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
