package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.SistemaFilterResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.UsuarioFilterResponseDtoTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({ MockitoExtension.class })
class UsuarioDaoTest {

    @InjectMocks
    @Autowired
    private UsuarioDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    void contar_registros_consultas_por_filtro() {

        when(sqlSession.selectOne(eq("UsuarioDao.countfindByFilter"), anyMap())).thenReturn(1);

        Integer count = dao.countfindByFilter(UsuarioFilterRequestDtoTest.getRequestFilter());

        assertNotNull(count);
        assertEquals(1, count);
    }

    @Test
    public void filter_list() {

        when(sqlSession.selectList(eq("UsuarioDao.findByFilter"), anyMap())).thenReturn(List.of(UsuarioFilterResponseDtoTest.getUsuarioFilter()));
        when(sqlSession.selectList(eq("UsuarioDao.findByFilterSystem"), anyMap())).thenReturn(List.of(SistemaFilterResponseDtoTest.getSistemaFilterResponseDto()));
        when(sqlSession.selectList(eq("UsuarioDao.findByFilterPerfil"), anyMap())).thenReturn(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()));

        UsuarioFilterRequestDto requestFilter = UsuarioFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("nome");
        var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("teste usuario", lista.get(0).getNome());
    }

    @Test
    public void filter_list_perfil() {

        when(sqlSession.selectList(eq("UsuarioDao.findByFilter"), anyMap())).thenReturn(List.of(UsuarioFilterResponseDtoTest.getUsuarioFilterComPerfil()));
        when(sqlSession.selectList(eq("UsuarioDao.findByFilterSystem"), anyMap())).thenReturn(List.of(SistemaFilterResponseDtoTest.getSistemaFilterResponseDto()));
        when(sqlSession.selectList(eq("UsuarioDao.findByFilterPerfil"), anyMap())).thenReturn(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()));

        UsuarioFilterRequestDto requestFilter = UsuarioFilterRequestDtoTest.getRequestFilterComPerfil();
        requestFilter.setOrderBy("nome");
        var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("teste usuario", lista.get(0).getNome());
    }

    @Test
    public void filter_list_order_system() {

        when(sqlSession.selectList(eq("UsuarioDao.findByFilter"), anyMap())).thenReturn(List.of(UsuarioFilterResponseDtoTest.getUsuarioFilter()));
        when(sqlSession.selectList(eq("UsuarioDao.findByFilterSystem"), anyMap())).thenReturn(List.of(SistemaFilterResponseDtoTest.getSistemaFilterResponseDto()));
        when(sqlSession.selectList(eq("UsuarioDao.findByFilterPerfil"), anyMap())).thenReturn(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()));

        UsuarioFilterRequestDto requestFilter = UsuarioFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("sistema");
        var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.get(0).getSistemas().size());

        requestFilter = UsuarioFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("sistema-desc");
        lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.get(0).getSistemas().size());

        requestFilter = UsuarioFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy(null);
        lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.get(0).getSistemas().size());

    }

    @Test
    public void filter_list_empty() {

        when(sqlSession.selectList(eq("UsuarioDao.findByFilter"), anyMap())).thenReturn(List.of());

        UsuarioFilterRequestDto requestFilter = UsuarioFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("sistema");
        var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(0, lista.size());
    }

    @Test
    public void test_count_historys_updates() {

        when(sqlSession.selectOne(eq("UsuarioDao.countHistorysUpdates"), anyMap())).thenReturn(1);

        Integer count = dao.countHistorysUpdates(UUID.randomUUID());

        assertNotNull(count);
        assertEquals(1, count);
    }

    @Test
	public void historico() throws JsonProcessingException {

        UUID id = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("UsuarioDao.findHistorysUpdates"), anyMap())).thenReturn(List.of());

        var lista = dao.findHistorysUpdates(id, 1l, 10);

        assertNotNull(lista);
        assertEquals(0, lista.size());
	}

}
