package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoPessoaTest {

    public static TipoPessoa getTipoPessoaFisica() {

        TipoPessoa tipoPessoa = new TipoPessoa();
        tipoPessoa.setId(UUID.fromString("22bb6280-17bd-41ff-b4df-17730fd7ac4f"));
        tipoPessoa.setNome("Pessoa Física");

        return tipoPessoa;
    }

    public static TipoPessoa getTipoPessoaJuridica() {

        TipoPessoa tipoPessoa = new TipoPessoa();
        tipoPessoa.setId(UUID.fromString("5ba4891a-2940-441e-b6c9-3730ef5f2a93"));
        tipoPessoa.setNome("Pessoa Jurídica");

        return tipoPessoa;
    }

    @Test
    void asserts() {

        TipoPessoa tipoPessoa = getTipoPessoaFisica();

        assertEquals(UUID.fromString("22bb6280-17bd-41ff-b4df-17730fd7ac4f"), tipoPessoa.getId());
        assertEquals("Pessoa Física", tipoPessoa.getNome());
    }
}
