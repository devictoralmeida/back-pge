package br.gov.ce.pge.gestao.dto.request;

import java.util.Arrays;
import java.util.UUID;

public class AtualizaFaseRequestDtoTest {

    public static AtualizaFaseRequestDto getRequest() {

        AtualizaFaseRequestDto dto = new AtualizaFaseRequestDto();

        dto.setFase("3d45ee92-27ca-45ed-a7f9-67f8de96765a");
        dto.setDividas(Arrays.asList("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));
        dto.setMotivo("5468064b-7a5b-4e58-b7cf-9c90775bdfdb");
        dto.setObservacao("Teste");
        dto.setAcaoJudicial(Arrays.asList("582eb8f3-18dc-4a4c-8acd-a1dd672506c2"));

        return dto;
    }

    public static AtualizaFaseRequestDto getRequestSalvarFalso() {

        AtualizaFaseRequestDto dto = new AtualizaFaseRequestDto();

        dto.setFase("a75a6cc2-26c7-4af5-ac86-bd0d00306b0c");
        dto.setDividas(Arrays.asList("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));
        dto.setMotivo("5468064b-7a5b-4e58-b7cf-9c90775bdfdb");
        dto.setObservacao("Teste");
        dto.setAcaoJudicial(Arrays.asList("582eb8f3-18dc-4a4c-8acd-a1dd672506c2"));
        dto.setSalvar(Boolean.FALSE);

        return dto;
    }

    public static AtualizaFaseRequestDto getRequestQuitado() {

        AtualizaFaseRequestDto dto = new AtualizaFaseRequestDto();

        dto.setFase("fa72516b-0e0e-4ad7-94d4-cc9a188c8f9e");
        dto.setDividas(Arrays.asList("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));
        dto.setMotivo("5468064b-7a5b-4e58-b7cf-9c90775bdfdb");
        dto.setObservacao("Teste");
        dto.setAcaoJudicial(Arrays.asList("582eb8f3-18dc-4a4c-8acd-a1dd672506c2"));
        dto.setSalvar(false);

        return dto;
    }

}
