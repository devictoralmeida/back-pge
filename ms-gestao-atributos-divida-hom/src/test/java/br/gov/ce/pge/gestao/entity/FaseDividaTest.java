package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

public class FaseDividaTest {

    @Test
    public void test_criacao_origem() {
        FaseDivida dto = getFaseDivida();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());
        Assertions.assertEquals("Fase Cadastrada", dto.getNome());
        Assertions.assertEquals("teste", dto.getDescricao());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
        Assertions.assertEquals(TipoMovimentacaoFase.AUTOMATICA, dto.getTipoMovimentacao());
        Assertions.assertEquals("00001", dto.getCodigo());
    }

    public static FaseDivida getFaseDivida() {
        var fase = new FaseDivida();
        fase.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        fase.setDescricao("teste");
        fase.setSituacao(Situacao.ATIVA);
        fase.setNome("Fase Cadastrada");
        fase.setCodigo("00001");
        fase.setExigeCobranca(true);
        fase.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        fase.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        return fase;
    }

    public static FaseDivida getFaseDividaFluxo() {
        var fase = new FaseDivida();
        fase.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        fase.setDescricao("teste");
        fase.setSituacao(Situacao.ATIVA);
        fase.setNome("Fase Cadastrada");
        fase.setCodigo("00001");
        fase.setExigeCobranca(true);
        fase.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        fase.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        FaseDivida faseFluxo = new FaseDivida();
        faseFluxo.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18b"));
        fase.setFasesPermitidas(new HashSet<>());
        fase.getFasesPermitidas().add(faseFluxo);
        return fase;
    }

    public static FaseDivida getFaseDividaUpdate() {
        var fase = new FaseDivida();
        fase.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        fase.setDescricao("teste");
        fase.setSituacao(Situacao.INATIVA);
        fase.setNome("Fase Cadastrada Atualizada");
        fase.setCodigo("00001");
        fase.setExigeCobranca(true);
        fase.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        fase.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        return fase;
    }
}
