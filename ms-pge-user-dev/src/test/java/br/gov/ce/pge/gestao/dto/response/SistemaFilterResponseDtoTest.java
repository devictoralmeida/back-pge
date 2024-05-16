package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SistemaFilterResponseDtoTest {

	public static SistemaFilterResponseDto getSistemaFilterResponseDto() {
		return new SistemaFilterResponseDto("e235b10f-1541-48b8-9b62-8f2399b5f7fe","Portal das Origens","0c69a9c8-0b43-480c-bcdf-48503788bfbe", "c4095434-f704-4209-be74-3d42d519d438");
	}
	
	public static SistemaFilterResponseDto getSistemaFilterResponseDtoComPortalDiferente() {
		return new SistemaFilterResponseDto("e235b10f-1541-48b8-9b62-8f2399b5f7fe","Portal das Origens","0c69a9c8-0b43-480c-bcdf-48503788bfci", null);
	}
	
	@Test
    public void test_get_sistema_filter_responseDto() {
        SistemaFilterResponseDto sistemaFilterResponseDto = SistemaFilterResponseDtoTest.getSistemaFilterResponseDto();
        assertEquals("e235b10f-1541-48b8-9b62-8f2399b5f7fe", sistemaFilterResponseDto.getId());
        assertEquals("Portal das Origens", sistemaFilterResponseDto.getNome());
    }

    @Test
    public void test_get_sistema_filter_responseDto_com_portal_diferente() {
        SistemaFilterResponseDto sistemaFilterResponseDto = SistemaFilterResponseDtoTest.getSistemaFilterResponseDtoComPortalDiferente();
        assertEquals("e235b10f-1541-48b8-9b62-8f2399b5f7fe", sistemaFilterResponseDto.getId());
        assertEquals("Portal das Origens", sistemaFilterResponseDto.getNome());
    }
    
    @Test
    public void test_getter_and_setter() {
        SistemaFilterResponseDto dto = new SistemaFilterResponseDto();
        dto.setId("1");
        dto.setNome("TestNome");
        dto.setIdPerfil("2");
        dto.setIdUsuario("3");

        assertEquals("1", dto.getId());
        assertEquals("TestNome", dto.getNome());
        assertEquals("2", dto.getIdPerfil());
        assertEquals("3", dto.getIdUsuario());
    }

}
