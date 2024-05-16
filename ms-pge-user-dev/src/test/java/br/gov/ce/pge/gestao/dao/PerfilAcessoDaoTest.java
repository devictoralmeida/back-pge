package br.gov.ce.pge.gestao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaPerfilAcessoDtoTest;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.SistemaFilterResponseDtoTest;

@ExtendWith({ MockitoExtension.class })
public class PerfilAcessoDaoTest {

	@InjectMocks
	@Autowired
	private PerfilAcessoDao dao;
	
	@Mock
	private transient SqlSession sqlSession;

	@Test
	public void contar_historico() {
		
		UUID permissaoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectOne(eq("PerfilAcessoDao.countHistorysUpdates"), anyMap())).thenReturn(1);

        int count = dao.countHistorysUpdates(permissaoId);

        assertNotNull(count);
        assertEquals(1, count);
		
	}
	
	@Test
	public void historico() throws JsonProcessingException {
		
		UUID permissaoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("PerfilAcessoDao.findHistorysUpdates"), anyMap())).thenReturn(List.of(AuditoriaPerfilAcessoDtoTest.getAuditoria()));

        var lista = dao.findHistorysUpdates(permissaoId, 1l, 10l);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(AuditoriaPerfilAcessoDtoTest.json(), lista.get(0).getDadosAntigos());
	}
	
	@Test
	public void contar_registros_consultas_por_filtro() {
		
        when(sqlSession.selectOne(eq("PerfilAcessoDao.countfindByFilter"), anyMap())).thenReturn(1);

        int count = dao.countfindByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter());

        assertNotNull(count);
        assertEquals(1, count);
	}
	
	
	@Test
	public void filter_list() {
		
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilter"), anyMap())).thenReturn(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()));
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilterWithSystem"), anyMap())).thenReturn(List.of(SistemaFilterResponseDtoTest.getSistemaFilterResponseDto()));

        PerfilAcessoFilterRequestDto requestFilter = PerfilAcessoFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("nome");
		var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("ADMIN", lista.get(0).getNome());
	}
	
	@Test
	public void filter_list_other_portal() {
		
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilter"), anyMap())).thenReturn(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()));
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilterWithSystem"), anyMap())).thenReturn(List.of(SistemaFilterResponseDtoTest.getSistemaFilterResponseDtoComPortalDiferente()));

        var lista = dao.findByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter());

        assertNotNull(lista);
        assertEquals(0, lista.get(0).getSistemas().size());
        
	}
	
	
	@Test
	public void filter_list_order_system() {
		
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilter"), anyMap())).thenReturn(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()));
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilterWithSystem"), anyMap())).thenReturn(List.of(SistemaFilterResponseDtoTest.getSistemaFilterResponseDtoComPortalDiferente()));

        PerfilAcessoFilterRequestDto requestFilter = PerfilAcessoFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("sistema");
		var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(0, lista.get(0).getSistemas().size());
        
        requestFilter = PerfilAcessoFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("sistema-desc");
		lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(0, lista.get(0).getSistemas().size());
        
        requestFilter = PerfilAcessoFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy(null);
		lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(0, lista.get(0).getSistemas().size());
        
	}
	
	@Test
	public void filter_list_empty() {
		
        when(sqlSession.selectList(eq("PerfilAcessoDao.findByFilter"), anyMap())).thenReturn(List.of());

        PerfilAcessoFilterRequestDto requestFilter = PerfilAcessoFilterRequestDtoTest.getRequestFilter();
        requestFilter.setOrderBy("sistema");
		var lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(0, lista.size());
	}
	
}
