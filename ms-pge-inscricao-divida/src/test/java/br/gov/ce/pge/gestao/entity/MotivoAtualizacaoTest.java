package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MotivoAtualizacaoTest {

    public static MotivoAtualizacaoFase getMotivo() {
        MotivoAtualizacaoFase motivoAtualizacaoFase = new MotivoAtualizacaoFase();
        motivoAtualizacaoFase.setId(UUID.fromString("9daa2753-6756-4fbb-b72f-90c020b78c2d"));
        motivoAtualizacaoFase.setNome(SharedConstant.FASE_INICIAL);

        return motivoAtualizacaoFase;
    }

    @Test
    void asserts() {
        MotivoAtualizacaoFase motivo = getMotivo();

        assertEquals(UUID.fromString("9daa2753-6756-4fbb-b72f-90c020b78c2d"), motivo.getId());
        assertEquals(SharedConstant.FASE_INICIAL, motivo.getNome());
    }
}
