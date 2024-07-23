package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

public class FaseDividaRequestDtoTest {

    @Test
    void test_filters() {
        FaseDividaRequestDto dto = getRequest();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Fase Cadastrada", dto.getNome());
        Assertions.assertEquals("teste", dto.getDescricao());
        Assertions.assertTrue(dto.isExigeCobranca());
        Assertions.assertEquals(Set.of(TipoCobranca.AJUIZAMENTO), dto.getTipoCobranca());
        Assertions.assertEquals(TipoMovimentacaoFase.AUTOMATICA, dto.getTipoMovimentacao());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
    }

    public static FaseDividaRequestDto getRequest() {
        var dto = new FaseDividaRequestDto();
        dto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        dto.setNome("Fase Cadastrada");
        dto.setDescricao("teste");
        dto.setSituacao(Situacao.ATIVA);
        dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        dto.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        dto.setExigeCobranca(true);
        return dto;
    }

    public static FaseDividaRequestDto getRequestUpdate() {
        var dto = new FaseDividaRequestDto();
        dto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        dto.setNome("Fase Cadastrada Atualizada");
        dto.setDescricao("teste");
        dto.setSituacao(Situacao.INATIVA);
        dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        dto.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        dto.setExigeCobranca(true);
        return dto;
    }

    public static FaseDividaRequestDto getRequestExigeCobrancaFalse() {
        var dto = new FaseDividaRequestDto();
        dto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        dto.setNome("Fas Cadastrada");
        dto.setDescricao("teste");
        dto.setSituacao(Situacao.INATIVA);
        dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        dto.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        dto.setExigeCobranca(false);
        return dto;
    }

    public static FaseDividaRequestDto getRequestTipoCobrancaVazio() {
        var dto = new FaseDividaRequestDto();
        dto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        dto.setNome("Fas Cadastrada");
        dto.setDescricao("teste");
        dto.setSituacao(Situacao.INATIVA);
        dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        dto.setExigeCobranca(false);
        return dto;
    }

    public static FaseDividaRequestDto getRequestSemNome() {
        var dto = new FaseDividaRequestDto();
        dto.setDescricao("teste");
        dto.setSituacao(Situacao.ATIVA);
        dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        dto.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        dto.setExigeCobranca(true);
        return dto;
    }
}
