package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDtoTest;
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
public class OrigemDebitoDaoTest {

    @InjectMocks
    @Autowired
    private OrigemDebitoDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    public void test_origem_debito_filter() {

        when(sqlSession.selectList(eq("OrigemDebitoDao.findOrigemDebitosByFilter"), anyMap())).thenReturn(List.of(OrigemDebitoConsultaResponseDtoTest.getOrigemDebito()));

        var lista = dao.findOrigemDebitosByFilter(OrigemDebitoFilterRequestDtoTest.getOrigemDebito());

        assertNotNull(lista);
        assertEquals(OrigemDebitoConsultaResponseDtoTest.getOrigemDebito(), lista.get(0));

    }

}
