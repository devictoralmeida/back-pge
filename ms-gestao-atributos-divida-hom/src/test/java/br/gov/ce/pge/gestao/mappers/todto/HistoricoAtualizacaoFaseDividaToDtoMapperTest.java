package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.AuditoriaFaseDividaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoricoAtualizacaoFaseDividaToDtoMapperTest {

    @Test
    public void test_processar_auditoria_fase_divida_dados_antigos_nulo() {

        AuditoriaFaseDividaDto faseDividaDto = new AuditoriaFaseDividaDto();
        faseDividaDto.setDadosAntigos(null);

        List<HistoricoAtualizacaoResponseDto> historicos = HistoricoAtualizacaoFaseDividaToDtoMapper
                .processarAuditoriaFaseDivida(faseDividaDto);

        assertTrue(historicos.isEmpty());
    }

}
