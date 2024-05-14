package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevedorTest {

    public static Devedor get_devedor_pessoa_fisica() {
        var model = new Devedor();
        model.setId(UUID.fromString("683bcca0-4d39-4711-b1eb-e4c1216df9a3"));
        model.setNomeRazaoSocial("Ronnys Nascimento");
        model.setTipoPessoa(TipoPessoa.PESSOA_FISICA);
        model.setDocumento("05459813360");
        model.setCgf(null);
        model.setTipoContato(TipoContato.TELEFONE_CELULAR);
        model.setContato("85999343912");
        model.setEmail("ronnys.m@gmail.com");
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

    public static Devedor getDevedorPessoaJuridica() {
        var model = new Devedor();
        model.setId(UUID.fromString("683bcca0-4d39-4711-b1eb-e4c1216df9a3"));
        model.setNomeRazaoSocial("Sabrina e Sophia Contábil Ltda");
        model.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
        model.setDocumento("77657073000185");
        model.setCgf("075035251");
        model.setTipoContato(TipoContato.WHATSAPP);
        model.setContato("85999343912");
        model.setEmail("contato@sabrinaesophiacontabilltda.com.br");
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

    @Test
    public void teste_pessoa_fisica() {
        var model = get_devedor_pessoa_fisica();

        assertEquals("Ronnys Nascimento", model.getNomeRazaoSocial());
        assertEquals(TipoPessoa.PESSOA_FISICA, model.getTipoPessoa());
        assertEquals("05459813360", model.getDocumento());
        assertEquals(null, model.getCgf());
        assertEquals(TipoContato.TELEFONE_CELULAR, model.getTipoContato());
        assertEquals("85999343912", model.getContato());
        assertEquals("ronnys.m@gmail.com", model.getEmail());
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
        var model = getDevedorPessoaJuridica();

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

}
