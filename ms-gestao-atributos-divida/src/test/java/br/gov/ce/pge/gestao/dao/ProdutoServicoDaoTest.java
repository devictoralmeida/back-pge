package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDtoTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({ MockitoExtension.class })
public class ProdutoServicoDaoTest {

    @InjectMocks
    @Autowired
    private ProdutoServicoDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    public void test_produto_servico_filter() {

        when(sqlSession.selectList(eq("ProdutoServicoDao.findProdutoServicosByFilter"), anyMap())).thenReturn(List.of(ProdutoServicoConsultaFilterResponseDtoTest.getProdutoServico()));

        var lista = dao.findTipoReceitasByFilter(ProdutoServicoFilterRequestDtoTest.getProdutoServico());

        assertNotNull(lista);
        assertEquals(ProdutoServicoConsultaFilterResponseDtoTest.getProdutoServico(), lista.get(0));

    }

}
