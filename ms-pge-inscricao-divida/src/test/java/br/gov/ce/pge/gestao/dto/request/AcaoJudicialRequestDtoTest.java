package br.gov.ce.pge.gestao.dto.request;

import java.util.UUID;

public class AcaoJudicialRequestDtoTest {

    public static AcaoJudicialRequestDto getRequest() {

        AcaoJudicialRequestDto request = new AcaoJudicialRequestDto();
        request.setAnexo("anexo");
        request.setAutor("autor");
        request.setDataAcaoJudicial("2024-05-01");
        request.setJuizo("1234");
        request.setNumeroOrdemJudicial("1234");
        request.setReu("reu");
        request.setObservacao("observação");
        request.setTipoAcao(UUID.randomUUID());
        request.setProvidencia("teste");
        request.setTipoAcao(UUID.randomUUID());

        return request;
    }
}
