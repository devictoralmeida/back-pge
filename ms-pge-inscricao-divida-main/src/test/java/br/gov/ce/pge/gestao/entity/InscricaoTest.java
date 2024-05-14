package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.StatusInscricao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InscricaoTest {

    public static Inscricao getIncricaoSomenteComDevedor() {
        var model = new Inscricao();
        model.setDevedor(DevedorTest.get_devedor_pessoa_fisica());

        return model;
    }

    public static Inscricao getIncricaoComDevedorDivida() {
        var model = new Inscricao();
        model.setDevedor(DevedorTest.get_devedor_pessoa_fisica());
        model.setDivida(DividaTest.get_divida());

        return model;
    }

    public static Inscricao getIncricaoComDevedorDividaStatus() {
        var model = new Inscricao();
        model.setDevedor(DevedorTest.get_devedor_pessoa_fisica());
        model.setDivida(DividaTest.get_divida());
        model.setStatus(StatusInscricao.EM_ANALISE);

        return model;
    }

    public static Inscricao getIncricaoComDevedorDividaStatusCorresponsaveis() {
        var model = new Inscricao();
        model.setDevedor(DevedorTest.get_devedor_pessoa_fisica());
        model.setDivida(DividaTest.get_divida());
        model.setStatus(StatusInscricao.EM_ANALISE);
        model.setCorresponsaveis(CorresponsavelTest.get_list_corresponsaveis());
        return model;
    }

    public static Inscricao get_inscricao_completa() {
        var model = new Inscricao();
        model.setId(UUID.fromString("c8bf3eff-2d65-46f7-bb6a-8f4c93be7886"));
        model.setDevedor(DevedorTest.get_devedor_pessoa_fisica());
        model.setDivida(DividaTest.get_divida());
        model.setStatus(StatusInscricao.EM_ANALISE);
        model.setCorresponsaveis(CorresponsavelTest.get_list_corresponsaveis());
        model.setDebitos(DebitoTest.get_debitos_list());
        return model;
    }

    public static Inscricao get_inscricao_completa_update() {
        var model = new Inscricao();
        model.setId(UUID.fromString("c8bf3eff-2d65-46f7-bb6a-8f4c93be7886"));
        model.setDevedor(DevedorTest.get_devedor_pessoa_fisica());
        model.setDivida(DividaTest.get_divida());
        model.setStatus(StatusInscricao.INSCRITO);
        model.setCorresponsaveis(CorresponsavelTest.get_list_corresponsaveis());
        model.setDebitos(DebitoTest.get_debitos_list());
        return model;
    }

    @Test
    void teste_inscricao_incompleto() {
        var model = getIncricaoSomenteComDevedor();

        assertNotNull(model.getDevedor());
        assertNull(model.getDivida());
        assertNull(model.getStatus());

        model = getIncricaoComDevedorDivida();

        assertNotNull(model.getDevedor());
        assertNotNull(model.getDivida());
        assertEquals(null, model.getStatus());

        model = getIncricaoComDevedorDividaStatus();

        assertNotNull(model.getDevedor());
        assertNotNull(model.getDivida());
        assertEquals(StatusInscricao.EM_ANALISE, model.getStatus());

        model = getIncricaoComDevedorDividaStatusCorresponsaveis();

        assertNotNull(model.getDevedor());
        assertNotNull(model.getDivida());
        assertEquals(StatusInscricao.EM_ANALISE, model.getStatus());
        assertNotNull(model.getCorresponsaveis());
        assertEquals(2, model.getCorresponsaveis().size());
        assertEquals("Kamila Lima", model.getCorresponsaveis().get(0).getNomeRazaoSocial());
        assertEquals("Osvaldo e Julio Marketing ME", model.getCorresponsaveis().get(1).getNomeRazaoSocial());

        model = get_inscricao_completa();

        assertNotNull(model.getDevedor());
        assertNotNull(model.getDivida());
        assertEquals(StatusInscricao.EM_ANALISE, model.getStatus());
        assertNotNull(model.getCorresponsaveis());
        assertEquals(2, model.getCorresponsaveis().size());
        assertEquals("Kamila Lima", model.getCorresponsaveis().get(0).getNomeRazaoSocial());
        assertEquals("Osvaldo e Julio Marketing ME", model.getCorresponsaveis().get(1).getNomeRazaoSocial());
        assertEquals(BigDecimal.valueOf(150), model.getDebitos().get(0).getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), model.getDebitos().get(0).getValorMulta());
    }

}
