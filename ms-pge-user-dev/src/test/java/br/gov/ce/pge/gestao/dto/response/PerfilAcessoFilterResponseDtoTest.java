package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.entity.PerfilAcessoTest;

public class PerfilAcessoFilterResponseDtoTest {

	
	public static PerfilAcessoFilterResponseDto getPerfilAcessoFilter() {
		var filter = new PerfilAcessoFilterResponseDto();
		filter.setDataAtualizacao(LocalDateTime.now());
		filter.setDataCadastro(LocalDateTime.now());
		filter.setId(PerfilAcessoTest.getPerfilAcesso().getId().toString());
		filter.setNome(PerfilAcessoTest.getPerfilAcesso().getNome());
		filter.setSituacao(PerfilAcessoTest.getPerfilAcesso().getSituacao());
		filter.setIdUsuario("c4095434-f704-4209-be74-3d42d519d438");
		
		var listaSistemaFilter = new ArrayList<SistemaFilterResponseDto>();
		
		PerfilAcessoTest.getPerfilAcesso().getSistemas().stream().forEach(sistema -> {
			listaSistemaFilter.add(new SistemaFilterResponseDto(sistema.getId().toString(), sistema.getNome(),"0c69a9c8-0b43-480c-bcdf-48503788bfbe", "c4095434-f704-4209-be74-3d42d519d438"));
		});
		
		filter.setSistemas(listaSistemaFilter);
		
		return filter;
	}

	public static List<PerfilAcessoFilterResponseDto> getListPerfilAcessoFilter() {
		return List.of(getPerfilAcessoFilter());
	}
	
	@Test
	public void test_all_fields() {
		var dto = getPerfilAcessoFilter();
		
		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertNotNull(dto.equals(getPerfilAcessoFilter()));
		assertNotNull(dto.hashCode());
	}
}
