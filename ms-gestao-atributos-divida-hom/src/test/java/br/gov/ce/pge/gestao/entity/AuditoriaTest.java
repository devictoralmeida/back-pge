package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.dto.response.AuditoriaFaseDividaDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaOrigemDebitoDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaProdutoServicoDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaTipoReceitaDto;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

public class AuditoriaTest {

    @Test
    public void test_criacao_auditoria() throws JsonProcessingException {
        AuditoriaOrigemDebitoDto dto = getAuditoriaOrigemDebito();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("teste up", dto.getNome());
        Assertions.assertEquals("teste descricao up", dto.getDescricao());
        Assertions.assertEquals("2023-11-21 10:57:31.091234", dto.getDataMovimento());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
    }


    public static AuditoriaOrigemDebitoDto getAuditoriaOrigemDebito() throws JsonProcessingException {

        OrigemDebito origemDebito = new OrigemDebito();
        origemDebito.setNome("teste");
        origemDebito.setSituacao(Situacao.ATIVA);
        origemDebito.setDescricao("teste descricao");
        origemDebito.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));

        var auditoria = new AuditoriaOrigemDebitoDto();
        auditoria.setDadosAntigos(origemDebito.toStringMapper());
        auditoria.setNome("teste up");
        auditoria.setDescricao("teste descricao up");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);

        return auditoria;
    }

    public static AuditoriaFaseDividaDto getAuditoriaFaseDivida() throws JsonProcessingException {

        FaseDivida faseDivida = new FaseDivida();
        faseDivida.setNome("teste");
        faseDivida.setSituacao(Situacao.ATIVA);
        faseDivida.setDescricao("teste descricao");
        faseDivida.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        faseDivida.setTipoCobranca(Set.of(TipoCobranca.AJUIZAMENTO, TipoCobranca.PROTESTO));

        var auditoria = new AuditoriaFaseDividaDto();
        auditoria.setDadosAntigos(faseDivida.toStringMapper());
        auditoria.setNome("teste up");
        auditoria.setDescricao("teste descricao up");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);
        auditoria.setTipoCobrancasAdicionados("AJUIZAMENTO,PROTESTO");
        auditoria.setTipoCobrancasRemovidos("NOTIFICACAO_EXTRA_JUDICIAL");
        return auditoria;
    }

    public static AuditoriaFaseDividaDto getAuditoriaFaseDividaTipoCobrancaNull() throws JsonProcessingException {

        FaseDivida faseDivida = new FaseDivida();
        faseDivida.setNome("teste");
        faseDivida.setSituacao(Situacao.ATIVA);
        faseDivida.setDescricao("teste descricao");
        faseDivida.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        faseDivida.setTipoCobranca(null);

        var auditoria = new AuditoriaFaseDividaDto();
        auditoria.setDadosAntigos(faseDivida.toStringMapper());
        auditoria.setNome("teste up");
        auditoria.setDescricao("teste descricao up");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);
        auditoria.setTipoCobrancasAdicionados("");
        auditoria.setTipoCobrancasRemovidos("");
        return auditoria;
    }

    public static AuditoriaTipoReceitaDto getAuditoriaTipoReceita() throws JsonProcessingException {
        var auditoria = new AuditoriaTipoReceitaDto();
        auditoria.setIdTipoReceita(String.valueOf(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
        auditoria.setCodigo("0001");
        auditoria.setDadosAntigos(TipoReceitaTest.getTipoReceita().toStringMapper());
        auditoria.setNatureza(Natureza.TRIBUTARIA);
        auditoria.setDescricao("Receita 01 update");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);

        return auditoria;
    }

    public static AuditoriaTipoReceitaDto getAuditoriaTipoReceita_erro() {
        var auditoria = new AuditoriaTipoReceitaDto();
        auditoria.setIdTipoReceita(String.valueOf(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
        auditoria.setCodigo("0001");
        auditoria.setDadosAntigos("TipoReceita=(OrigemDebitoErro=erro");
        auditoria.setNatureza(Natureza.TRIBUTARIA);
        auditoria.setDescricao("Receita 01 update");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);

        return auditoria;
    }

    public static AuditoriaProdutoServicoDto getAuditoriaProdutoServico() throws JsonProcessingException {
        var auditoria = new AuditoriaProdutoServicoDto();
        auditoria.setIdProdutoServico(String.valueOf(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1")));
        auditoria.setCodigo("00001");
        auditoria.setDadosAntigos(ProdutoServicoTest.getProdutoServico().toStringMapper());
        auditoria.setDescricao("produto servico 1 update");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);

        return auditoria;
    }

}
