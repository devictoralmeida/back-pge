package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class UsuarioFilterRequestDtoTest {
	
	public static UsuarioFilterRequestDto getRequestFilter() {

		var request = new UsuarioFilterRequestDto();
		request.setNome(null);
		request.setEmail(null);
		request.setSistemas(null);
		request.setPerfisAcessos(null);
		request.setSituacao(null);
		request.setCpf(null);
		request.setTipoUsuario(null);
		request.setArea(null);
		request.setOrgao(null);

		return request;
	}

	public static UsuarioFilterRequestDto getRequestFilterComPerfil() {
		var request = new UsuarioFilterRequestDto();

		request.setNome(null);
		request.setEmail(null);
		request.setSistemas(Arrays.asList("2f990d54-d6ff-4c36-a057-0492e7c59119"));
		request.setPerfisAcessos(null);
		request.setSituacao(null);
		request.setCpf(null);
		request.setTipoUsuario(null);
		request.setArea(null);
		request.setOrgao(null);
		request.setPerfisAcessos(Arrays.asList("200204cf-4a55-4c10-8934-18def377bc12"));

		return request;
	}

	@Test
	public void test_filters() {
		
		UsuarioFilterRequestDto dto = new UsuarioFilterRequestDto();
		dto.setTipoUsuario(TipoUsuario.EXTERNO);
		dto.setSistemas(List.of("sistema1", "sistema2"));
		dto.setPerfisAcessos(List.of("perfil1", "perfil2"));
		dto.setCpf("12345678901");
		dto.setNome("John");
		dto.setOrgao("Orgao Teste");
		dto.setArea("Area Teste");
		dto.setEmail("john@example.com");
		dto.setSituacao(SituacaoUsuario.ATIVA);
		dto.setOffset(0);
		dto.setLimit(10);
		dto.setOrderBy("nome-desc");

		Map<String, Object> filters = dto.filters();

		assertEquals(TipoUsuario.EXTERNO, filters.get("tipoUsuario"));
		assertEquals(List.of("sistema1", "sistema2"), filters.get("sistemas"));
		assertEquals(List.of("perfil1", "perfil2"), filters.get("perfisAcessos"));
		assertEquals("12345678901", filters.get("cpf"));
		assertEquals("%JOHN%", filters.get("nome"));
		assertEquals("%JOHN@EXAMPLE.COM%", filters.get("email"));
		assertEquals(SituacaoUsuario.ATIVA, filters.get("situacao"));
		assertEquals(0L, filters.get("offset"));
		assertEquals(10L, filters.get("limit"));
		assertEquals("u.nm_usuario desc", filters.get("orderBy"));

		dto.setOrderBy("nome");
		filters = dto.filters();
		assertEquals("u.nm_usuario", filters.get("orderBy"));

		dto.setOrderBy("situacao");
		filters = dto.filters();
		assertEquals("u.ds_situacao_usuario", filters.get("orderBy"));

		dto.setOrderBy("situacao-desc");
		filters = dto.filters();
		assertEquals("u.ds_situacao_usuario desc", filters.get("orderBy"));

		dto.setOrderBy("ultimo_acesso");
		filters = dto.filters();
		assertEquals("u.dt_ultimo_acesso_usuario", filters.get("orderBy"));

		dto.setOrderBy("ultimo_acesso-desc");
		filters = dto.filters();
		assertEquals("u.dt_ultimo_acesso_usuario desc", filters.get("orderBy"));

		dto.setOrderBy("orgao");
		filters = dto.filters();
		assertEquals("u.ds_orgao_usuario", filters.get("orderBy"));

		dto.setOrderBy("orgao-desc");
		filters = dto.filters();
		assertEquals("u.ds_orgao_usuario desc", filters.get("orderBy"));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			dto.setOrderBy("outro");
			dto.filters();
		});
		Assertions.assertEquals("Não é possível ordenar por: outro", error.getMessage());

		dto.setSistemas(List.of());
		dto.setPerfisAcessos(List.of());
		dto.setCpf("");
		dto.setNome("");
		dto.setEmail("");
		dto.setSituacao(null);
		dto.setOrderBy(null);

		Map<String, Object> filters2 = dto.filters();

		assertEquals(null, filters2.get("sistemas"));
		assertEquals(null, filters2.get("perfisAcessos"));
		assertEquals(null, filters2.get("cpf"));
		assertEquals(null, filters2.get("nome"));
		assertEquals(null, filters2.get("email"));

		dto.setNome(null);
		dto.setEmail(null);

		filters2 = dto.filters();

		assertEquals(null, filters2.get("cpf"));
		assertEquals(null, filters2.get("nome"));
	}

	@Test
	public void test_constructor_and_getter_setter() {
		
		UsuarioFilterRequestDto dto = new UsuarioFilterRequestDto();
		dto.setTipoUsuario(TipoUsuario.EXTERNO);
		List<String> sistemas = List.of("Sistema1", "Sistema2");
		dto.setSistemas(sistemas);
		List<String> perfisAcessos = List.of("Perfil1", "Perfil2");
		dto.setPerfisAcessos(perfisAcessos);
		dto.setCpf("12345678901");
		dto.setNome("TestNome");
		dto.setOrgao("TestOrgao");
		dto.setArea("TestArea");
		dto.setEmail("test@example.com");
		dto.setSituacao(SituacaoUsuario.ATIVA);
		dto.setOffset(10);
		dto.setLimit(20);
		dto.setOrderBy("nome-desc");

		assertEquals(TipoUsuario.EXTERNO, dto.getTipoUsuario());
		assertEquals(sistemas, dto.getSistemas());
		assertEquals(perfisAcessos, dto.getPerfisAcessos());
		assertEquals("12345678901", dto.getCpf());
		assertEquals("TestNome", dto.getNome());
		assertEquals("TestOrgao", dto.getOrgao());
		assertEquals("TestArea", dto.getArea());
		assertEquals("test@example.com", dto.getEmail());
		assertEquals(SituacaoUsuario.ATIVA, dto.getSituacao());
		assertEquals(10, dto.getOffset());
		assertEquals(20, dto.getLimit());
		assertEquals("nome-desc", dto.getOrderBy());
	}

	@Test
	public void test_equals_and_hashCode() {
		
		UsuarioFilterRequestDto dto1 = new UsuarioFilterRequestDto();
		dto1.setNome("TestNome");
		UsuarioFilterRequestDto dto2 = new UsuarioFilterRequestDto();
		dto2.setNome("TestNome");
		UsuarioFilterRequestDto dto3 = new UsuarioFilterRequestDto();
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
		
		UsuarioFilterRequestDto dto = new UsuarioFilterRequestDto();
		dto.setNome("TestNome");
		dto.setSituacao(SituacaoUsuario.ATIVA);
		dto.setEmail("test@example.com");

		assertEquals(
				"UsuarioFilterRequestDto(tipoUsuario=null, sistemas=null, perfisAcessos=null, cpf=null, nome=TestNome, orgao=null, area=null, email=test@example.com, situacao=ATIVA, offset=0, limit=0, orderBy=null)",
				dto.toString());
	}

	@Test
	public void test_filters_ok() {
		
		UsuarioFilterRequestDto dto = new UsuarioFilterRequestDto();
		dto.setNome("TestNome");
		dto.setSituacao(SituacaoUsuario.ATIVA);
		dto.setEmail("test@example.com");
		dto.setOffset(10);
		dto.setLimit(20);
		dto.setOrderBy("nome-desc");

		Map<String, Object> expectedFilter = new HashMap<>();
		expectedFilter.put("tipoUsuario", null);
		expectedFilter.put("sistemas", null);
		expectedFilter.put("perfisAcessos", null);
		expectedFilter.put("cpf", null);
		expectedFilter.put("area", null);
		expectedFilter.put("orgao", null);

		expectedFilter.put("nome", "%TESTNOME%");
		expectedFilter.put("email", "%TEST@EXAMPLE.COM%");
		expectedFilter.put("situacao", SituacaoUsuario.ATIVA);
		expectedFilter.put("offset", 10);
		expectedFilter.put("limit", 20);
		expectedFilter.put("orderBy", "u.nm_usuario desc");

		assertEquals(expectedFilter.toString(), dto.filters().toString());

		dto.setOrderBy("tipoUsuario");
		Map<String, Object> filters2 = dto.filters();
		assertEquals("u.tp_usuario", filters2.get("orderBy"));

		dto.setOrderBy("tipoUsuario-desc");
		Map<String, Object> filters3 = dto.filters();
		assertEquals("u.tp_usuario desc", filters3.get("orderBy"));

		dto.setOrderBy("sistema");
		Map<String, Object> filters4 = dto.filters();
		assertEquals(null, filters4.get("orderBy"));

		dto.setOrderBy("sistema-desc");
		Map<String, Object> filters5 = dto.filters();
		assertEquals(null, filters5.get("orderBy"));
	}

}
