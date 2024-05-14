package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.entity.SistemaTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class PerfilAcessoFilterRequestDtoTest {

	public static PerfilAcessoFilterRequestDto getRequestFilter() {
		var request = new PerfilAcessoFilterRequestDto();
		request.setNome(null);
		request.setSituacao(null);
		request.setSistemas(null);
		return request;
	}
	
	@Test
	public void all_fields() {
		var request = new PerfilAcessoFilterRequestDto();
		request.setNome("admin");
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(SistemaTest.getSistema().getId().toString()));
		request.setOffset(1l);
		request.setLimit(10);
		request.setOrderBy("nome");
		
		assertNotNull(request);
		assertEquals("admin", request.getNome());
		assertEquals("f08cc600-1268-4e35-8279-63ecef41454b", request.getSistemas().get(0));
		assertEquals(1, request.getOffset());
		assertEquals(10, request.getLimit());
		assertEquals("nome", request.getOrderBy());
		
		var maps = request.filters();
		assertEquals("%ADMIN%", maps.get("nome"));
		assertEquals(Situacao.ATIVA, maps.get("situacao"));
		assertEquals(request.getSistemas(), maps.get("sistemas"));
		assertEquals(1l, maps.get("offset"));
		assertEquals(10l, maps.get("limit"));
		assertEquals("pa.nm_perfil_acesso", maps.get("orderBy"));
		
		request.setNome("");
		request.setSituacao(null);
		request.setSistemas(null);
		request.setOffset(1l);
		request.setLimit(10);
		
		assertNotNull(request);
		assertEquals("", request.getNome());
		assertEquals(null, request.getSistemas());
		assertEquals(null, request.getSituacao());
		assertEquals(1, request.getOffset());
		assertEquals(10, request.getLimit());
		
		maps = request.filters();
		assertEquals(null, maps.get("nome"));
		assertEquals(null, maps.get("situacao"));
		assertEquals(null, maps.get("sistemas"));
		assertEquals(1l, maps.get("offset"));
		assertEquals(10l, maps.get("limit"));
		
		request.setNome(null);
		request.setSituacao(null);
		request.setSistemas(List.of());
		request.setOffset(1l);
		request.setLimit(10);
		
		assertNotNull(request);
		assertEquals(null, request.getNome());
		assertEquals(List.of(), request.getSistemas());
		assertEquals(null, request.getSituacao());
		assertEquals(1, request.getOffset());
		assertEquals(10, request.getLimit());
		
		maps = request.filters();
		assertEquals(null, maps.get("nome"));
		assertEquals(null, maps.get("situacao"));
		assertEquals(null, maps.get("sistemas"));
		assertEquals(1l, maps.get("offset"));
		assertEquals(10l, maps.get("limit"));
		
		var requestOther = new PerfilAcessoFilterRequestDto();
		requestOther.setNome("admin");
		requestOther.setSituacao(Situacao.ATIVA);
		requestOther.setSistemas(List.of(SistemaTest.getSistema().getId().toString()));
		requestOther.setOffset(1l);
		requestOther.setLimit(10);
		requestOther.setOrderBy("nome-desc");
		
		var mapsOther = requestOther.filters();
		assertEquals("%ADMIN%", mapsOther.get("nome"));
		assertEquals(Situacao.ATIVA, mapsOther.get("situacao"));
		assertEquals(requestOther.getSistemas(), mapsOther.get("sistemas"));
		assertEquals(1l, mapsOther.get("offset"));
		assertEquals(10l, mapsOther.get("limit"));
		assertEquals("pa.nm_perfil_acesso desc", mapsOther.get("orderBy"));
		
		requestOther.setOrderBy(null);
		mapsOther = requestOther.filters();
		assertEquals("pa.nm_perfil_acesso", mapsOther.get("orderBy"));
		
		requestOther.setOrderBy("situacao");
		assertEquals("situacao", requestOther.getOrderBy());
		mapsOther = requestOther.filters();
		assertEquals("pa.ds_situacao_perfil_acesso", mapsOther.get("orderBy"));
		
		requestOther.setOrderBy("situacao-desc");
		mapsOther = requestOther.filters();
		assertEquals("pa.ds_situacao_perfil_acesso desc", mapsOther.get("orderBy"));
		
		requestOther.setOrderBy("sistema");
		mapsOther = requestOther.filters();
		assertEquals(null, mapsOther.get("orderBy"));
		
		requestOther.setOrderBy("sistema-desc");
		mapsOther = requestOther.filters();
		assertEquals(null, mapsOther.get("orderBy"));
		
		requestOther.setOrderBy("cadastrado_em");
		mapsOther = requestOther.filters();
		assertEquals("pa.dt_criacao", mapsOther.get("orderBy"));
		
		requestOther.setOrderBy("cadastrado_em-desc");
		mapsOther = requestOther.filters();
		assertEquals("pa.dt_criacao desc", mapsOther.get("orderBy"));
		
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			requestOther.setOrderBy("outro");
			requestOther.filters();
		});

		Assertions.assertEquals("Não é possível ordenar por: outro", error.getMessage());
		
	}
	
	@Test
    public void test_filters() {
        PerfilAcessoFilterRequestDto dto = new PerfilAcessoFilterRequestDto();
        dto.setNome("TestNome");
        dto.setSituacao(Situacao.ATIVA);
        List<String> sistemas = new ArrayList<>();
        sistemas.add("Sistema1");
        sistemas.add("Sistema2");
        dto.setSistemas(sistemas);
        dto.setOffset(10);
        dto.setLimit(20);
        dto.setOrderBy("nome-desc");

        assertEquals("TestNome", dto.getNome());
        assertEquals(Situacao.ATIVA, dto.getSituacao());
        assertEquals(sistemas, dto.getSistemas());
        assertEquals(10, dto.getOffset());
        assertEquals(20, dto.getLimit());
        assertEquals("nome-desc", dto.getOrderBy());

        assertEquals(dto.filters().get("nome"), "%TESTNOME%");
        assertEquals(dto.filters().get("situacao"), Situacao.ATIVA);
        assertEquals(dto.filters().get("sistemas"), sistemas);
        assertEquals(dto.filters().get("offset"), 10l);
        assertEquals(dto.filters().get("limit"), 20l);
        assertEquals(dto.filters().get("orderBy"), "pa.nm_perfil_acesso desc");
    }
	
	@Test
    public void test_constructor_and_getter_setter() {
        
        PerfilAcessoFilterRequestDto dto = new PerfilAcessoFilterRequestDto();
        dto.setNome("TestNome");
        dto.setSituacao(Situacao.ATIVA);
        List<String> sistemas = new ArrayList<>();
        sistemas.add("Sistema1");
        sistemas.add("Sistema2");
        dto.setSistemas(sistemas);
        dto.setOffset(10);
        dto.setLimit(20);
        dto.setOrderBy("nome-desc");

        assertEquals("TestNome", dto.getNome());
        assertEquals(Situacao.ATIVA, dto.getSituacao());
        assertEquals(sistemas, dto.getSistemas());
        assertEquals(10l, dto.getOffset());
        assertEquals(20l, dto.getLimit());
        assertEquals("nome-desc", dto.getOrderBy());
    }
	
	@Test
    public void test_equals_and_hashCode() {
        
        PerfilAcessoFilterRequestDto dto1 = new PerfilAcessoFilterRequestDto();
        dto1.setNome("TestNome");
        PerfilAcessoFilterRequestDto dto2 = new PerfilAcessoFilterRequestDto();
        dto2.setNome("TestNome");
        PerfilAcessoFilterRequestDto dto3 = new PerfilAcessoFilterRequestDto();
        dto3.setNome("DifferentNome");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertEquals(dto1.hashCode(), dto1.hashCode()); 
        assertEquals(dto1.equals(dto2), dto2.equals(dto1));
        assertEquals(dto1.equals(dto2) && dto2.equals(dto3), dto1.equals(dto3));
        assertEquals(dto1.equals(null), false);
        assertEquals(dto1.equals(new Object()), false);
    }
	
	@Test
    public void test_toString() {
        
        PerfilAcessoFilterRequestDto dto = new PerfilAcessoFilterRequestDto();
        dto.setNome("TestNome");
        dto.setSituacao(Situacao.ATIVA);
        List<String> sistemas = new ArrayList<>();
        sistemas.add("Sistema1");
        sistemas.add("Sistema2");
        dto.setSistemas(sistemas);
        dto.setOffset(10);
        dto.setLimit(20);
        dto.setOrderBy("nome-desc");

        assertEquals("PerfilAcessoFilterRequestDto(nome=TestNome, situacao=ATIVA, sistemas=[Sistema1, Sistema2], offset=10, limit=20, orderBy=nome-desc)", dto.toString());
    }
	
	
}
