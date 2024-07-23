package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaConsultaFilterResponseDtoTest;
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
public class TipoReceitaDaoTest {

    @InjectMocks
    @Autowired
    private TipoReceitaDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    public void test_tipo_receita_filter() {

        when(sqlSession.selectList(eq("TipoReceitaDao.findTipoReceitasByFilter"), anyMap())).thenReturn(List.of(TipoReceitaConsultaFilterResponseDtoTest.getResponseConsulta()));

        var lista = dao.findTipoReceitasByFilter(TipoReceitaFilterRequestDtoTest.getTipoReceita());

        assertNotNull(lista);
        assertEquals(TipoReceitaConsultaFilterResponseDtoTest.getResponseConsulta(), lista.get(0));

    }

}
