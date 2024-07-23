package br.gov.ce.pge.gestao.entity;

import java.util.UUID;

public class MotivoAtualizacaoFaseTest {

    public static MotivoAtualizacaoFase getMotivo() {

        MotivoAtualizacaoFase motivo = new MotivoAtualizacaoFase();
        motivo.setNome("Teste");
        motivo.setId(UUID.fromString("47074080-e8f7-4823-ab3b-3d5c071fbcd4"));

        return motivo;
    }

}
