package br.gov.ce.pge.mspgeportalcolaborador.dto;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColaboradorResponseDtoTest {

	public static ColaboradorResponseDto getColaboradorResponse() {
		var response = new ColaboradorResponseDto();
		response.setNome("João");
		response.setCpf("054.598.133-60");
		response.setArea("ti");
		response.setDesvinculado(false);

		return response;
	}

	@Test
	void test_colaborador() {
		var dto = getColaboradorResponse();
		assertEquals("João", dto.getNome());
		assertEquals("ti", dto.getArea());
		assertEquals("054.598.133-60", dto.getCpf());
		assertEquals(false, dto.getDesvinculado());
	}

    public static List<ColaboradorResponseDto> getListColaboradorResponse() {
        return Arrays.asList(getColaboradorResponse());
    }

}
