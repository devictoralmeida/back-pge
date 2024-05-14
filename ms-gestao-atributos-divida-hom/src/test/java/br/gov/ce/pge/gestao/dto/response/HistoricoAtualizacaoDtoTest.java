package br.gov.ce.pge.gestao.dto.response;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HistoricoAtualizacaoDtoTest {

    @Test
    public void test_criacao_auditoria() {
        PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> response = getResponse();

        Assertions.assertNotNull(response);
        Integer tamanhoEsperado = 1;
        Assertions.assertEquals(tamanhoEsperado, response.getTotalRegistros());
        Assertions.assertEquals(tamanhoEsperado, response.getTotalRegistros());
        Assertions.assertEquals(tamanhoEsperado, response.getResultado().size());
        Assertions.assertEquals("anonimo", response.getResultado().get(0).getResponsavel());
        Assertions.assertEquals("21/11/2023 10:57:31", response.getResultado().get(0).getDataAlterado());
    }

    public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getResponse() {

        var paginacaoResponse = new PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>>();

        HistoricoAtualizacaoResponseDto historico = new HistoricoAtualizacaoResponseDto();
        historico.setResponsavel("anonimo");
        historico.setDataAlterado("21/11/2023 10:57:31");

        Document document = new Document();
        document.put("campoAtualizado", "situacao");
        document.put("valorAntigo", "ATIVA");
        document.put("valorNovo", "INATIVA");

        historico.setDadosAlterados(List.of(document));

        paginacaoResponse.setPaginaAtual(1)
                .setTotalPaginas(1)
                .setTotalRegistros(1)
                .setResultado(List.of(historico));

        return paginacaoResponse;
    }
    
}
