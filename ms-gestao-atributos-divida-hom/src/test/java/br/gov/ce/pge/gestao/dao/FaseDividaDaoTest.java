package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDtoTest;
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
public class FaseDividaDaoTest {

    @InjectMocks
    @Autowired
    private FaseDividaDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    public void test_fase_divida_filter() {

        when(sqlSession.selectList(eq("FaseDividaDao.findFaseDividaByFilter"), anyMap())).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        var lista = dao.findFaseDividaByFilter(FaseDividaFilterRequestDtoTest.getFilterRequest());

        assertNotNull(lista);
        assertEquals(FaseDividaResponseDtoTest.getResponse(), lista.get(0));

    }
}
