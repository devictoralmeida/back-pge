package br.gov.ce.pge.gestao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.entity.FaseDividaTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.entity.OrigemDebitoTest;
import br.gov.ce.pge.gestao.entity.ProdutoServicoTest;
import br.gov.ce.pge.gestao.entity.TipoReceitaTest;
import br.gov.ce.pge.gestao.shared.exception.BadValueException;

@ExtendWith({ MockitoExtension.class })
public class AuditoriaDaoTest {

    @InjectMocks
    @Autowired
    private AuditoriaDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    public void contar_historico_origem_debito() {

        UUID origemId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectOne(eq("AuditoriaDao.countHistorysUpdatesOrigemDebito"), anyMap())).thenReturn(1);

        int count = dao.countHistorysUpdates(origemId);

        assertNotNull(count);
        assertEquals(1, count);

    }

    @Test
    public void contar_historico_tipo_receita() {

        UUID tipoReceitaId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectOne(eq("AuditoriaDao.countHistorysUpdatesTipoReceita"), anyMap())).thenReturn(1);

        int count = dao.countHistorysUpdatesTipoReceita(tipoReceitaId);

        assertNotNull(count);
        assertEquals(1, count);

    }

    @Test
    public void contar_historico_produto_servico() {

        UUID produtoServicoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectOne(eq("AuditoriaDao.countHistorysUpdatesProdutoServico"), anyMap())).thenReturn(1);

        int count = dao.countHistorysUpdatesProdutoServico(produtoServicoId);

        assertNotNull(count);
        assertEquals(1, count);

    }

    @Test
    public void contar_historico_fase_divida() {

        UUID faseDividaId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectOne(eq("AuditoriaDao.countHistorysUpdatesFaseDivida"), anyMap())).thenReturn(1);

        int count = dao.countHistorysUpdatesFaseDivida(faseDividaId);

        assertNotNull(count);
        assertEquals(1, count);

    }

    @Test
    public void historico_origem_debito() throws JsonProcessingException {

        UUID origemDebitoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("AuditoriaDao.findHistorysUpdates"), anyMap())).thenReturn(List.of(AuditoriaDtoTest.getAuditoriaOrigemDebito()));

        var lista = dao.findHistorysUpdates(origemDebitoId, 1l, 10l, "OrigemDebito");

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(OrigemDebitoTest.getOrigem().toString(), lista.get(0).getDadosAntigos());

    }

    @Test
    public void historico_tipo_receita() throws JsonProcessingException {

        UUID tipoReceitaId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("AuditoriaDao.findHistorysUpdatesTipoReceita"), anyMap())).thenReturn(List.of(AuditoriaDtoTest.getAuditoriaTipoReceita()));

        var lista = dao.findHistorysUpdates(tipoReceitaId, 1l, 10l, "TipoReceita");

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(TipoReceitaTest.getTipoReceita().toStringMapper(), lista.get(0).getDadosAntigos());

    }

    @Test
    public void historico_produto_servico() throws JsonProcessingException {

        UUID produtoServicoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("AuditoriaDao.findHistorysUpdatesProdutoServico"), anyMap())).thenReturn(List.of(AuditoriaDtoTest.getAuditoriaProdutoServico()));

        var lista = dao.findHistorysUpdates(produtoServicoId, 1l, 10l, "ProdutoServico");

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(ProdutoServicoTest.getProdutoServico().toStringMapper(), lista.get(0).getDadosAntigos());

    }

    @Test
    public void historico_fase_divida() throws JsonProcessingException {

        UUID produtoServicoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("AuditoriaDao.findHistorysUpdatesFaseDivida"), anyMap())).thenReturn(List.of(AuditoriaDtoTest.getAuditoriaFaseDivida()));

        var lista = dao.findHistorysUpdates(produtoServicoId, 1l, 10l, "FaseDivida");

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(FaseDividaTest.getFaseDivida().toStringMapper(), lista.get(0).getDadosAntigos());

    }
    
    @Test
    public void historico_erro() {

        UUID produtoServicoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");

        Exception error = Assertions.assertThrows(BadValueException.class, () -> {
        	dao.findHistorysUpdates(produtoServicoId, 1l, 10l, "erro");
		});

		assertEquals("Chave desconhecida = erro", error.getMessage());

    }

}
