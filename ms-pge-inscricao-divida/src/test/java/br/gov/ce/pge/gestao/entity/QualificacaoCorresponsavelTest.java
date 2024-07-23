package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QualificacaoCorresponsavelTest {

    public static QualificacaoCorresponsavel getQualificacao() {

        QualificacaoCorresponsavel qualificacaoCorresponsavel = new QualificacaoCorresponsavel();
        qualificacaoCorresponsavel.setId(UUID.fromString("7674de4f-93d6-43ec-8bb0-cdc63ac07897"));
        qualificacaoCorresponsavel.setNome("teste");

        return qualificacaoCorresponsavel;
    }

    @Test
    void asserts() {
        QualificacaoCorresponsavel qualificacao = getQualificacao();

        assertEquals(UUID.fromString("7674de4f-93d6-43ec-8bb0-cdc63ac07897"), qualificacao.getId());
        assertEquals("teste", qualificacao.getNome());
    }

}
