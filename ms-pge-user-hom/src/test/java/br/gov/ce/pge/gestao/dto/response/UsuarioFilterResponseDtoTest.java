package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.enums.TipoUsuario;

public class UsuarioFilterResponseDtoTest {

	public static UsuarioFilterResponseDto getUsuarioFilter() {

		var filter = new UsuarioFilterResponseDto();
		filter.setDataAtualizacao(LocalDateTime.now());
		filter.setDataCadastro(LocalDateTime.now());
		filter.setId(UsuarioTest.getUsuario().getId().toString());
		filter.setNome(UsuarioTest.getUsuario().getNome());
		filter.setArea(UsuarioTest.getUsuario().getArea());
		filter.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());
		filter.setOrgao(UsuarioTest.getUsuario().getOrgao());
		filter.setCpf(UsuarioTest.getUsuario().getCpf());
		filter.setUsuarioRede(UsuarioTest.getUsuario().getUsuarioRede());
		filter.setSituacao(SituacaoUsuario.ATIVA);

		return filter;
	}

	public static UsuarioFilterResponseDto getUsuarioFilterComPerfil() {

		var filter = new UsuarioFilterResponseDto();
		filter.setDataAtualizacao(LocalDateTime.now());
		filter.setDataCadastro(LocalDateTime.now());
		filter.setId(UsuarioTest.getUsuario().getId().toString());
		filter.setNome(UsuarioTest.getUsuario().getNome());
		filter.setArea(UsuarioTest.getUsuario().getArea());
		filter.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());
		filter.setOrgao(UsuarioTest.getUsuario().getOrgao());
		filter.setCpf(UsuarioTest.getUsuario().getCpf());
		filter.setUsuarioRede(UsuarioTest.getUsuario().getUsuarioRede());
		filter.setSituacao(SituacaoUsuario.ATIVA);
		filter.setPerfisAcessos(PerfilAcessoFilterResponseDtoTest.getListPerfilAcessoFilter());

		return filter;
	}

	public static List<UsuarioFilterResponseDto> getListUsuarioFilter() {
		return List.of(getUsuarioFilter());
	}

	@Test
	public void test_getter_and_setter() {
		
		UsuarioFilterResponseDto dto = new UsuarioFilterResponseDto();

		dto.setId("123");
		dto.setTipoUsuario(TipoUsuario.EXTERNO);
		dto.setCpf("12345678901");
		dto.setNome("John Doe");
		dto.setOrgao("Orgao Teste");
		dto.setEmail("john@example.com");
		dto.setArea("Area Teste");
		dto.setUsuarioRede("johndoe");
		List<SistemaFilterResponseDto> sistemas = new ArrayList<>();
		sistemas.add(SistemaFilterResponseDtoTest.getSistemaFilterResponseDto());
		dto.setSistemas(sistemas);
		List<PerfilAcessoFilterResponseDto> perfisAcessos = new ArrayList<>();
		perfisAcessos.add(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter());
		dto.setPerfisAcessos(perfisAcessos);
		dto.setSituacao(SituacaoUsuario.ATIVA);
		LocalDateTime now = LocalDateTime.now();
		dto.setDataCadastro(now);
		dto.setDataAtualizacao(now);
		dto.setDataUltimoAcesso(now);

		assertEquals("123", dto.getId());
		assertEquals(TipoUsuario.EXTERNO, dto.getTipoUsuario());
		assertEquals("12345678901", dto.getCpf());
		assertEquals("John Doe", dto.getNome());
		assertEquals("Orgao Teste", dto.getOrgao());
		assertEquals("john@example.com", dto.getEmail());
		assertEquals("Area Teste", dto.getArea());
		assertEquals("johndoe", dto.getUsuarioRede());
		assertEquals(sistemas, dto.getSistemas());
		assertEquals(perfisAcessos, dto.getPerfisAcessos());
		assertEquals(SituacaoUsuario.ATIVA, dto.getSituacao());
		assertEquals(now, dto.getDataCadastro());
		assertEquals(now, dto.getDataAtualizacao());
		assertEquals(now, dto.getDataUltimoAcesso());
	}

	@Test
	public void test_serializable() {
		
		UsuarioFilterResponseDto dto = new UsuarioFilterResponseDto();

		boolean isSerializable = dto instanceof java.io.Serializable;

		assertTrue(isSerializable);
	}

}
