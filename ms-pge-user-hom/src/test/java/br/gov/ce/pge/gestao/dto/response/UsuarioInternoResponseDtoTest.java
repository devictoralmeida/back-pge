package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioInternoResponseDtoTest {

    public static UsuarioInternoResponseDto getUsuarioInterno() {
        var response = new UsuarioInternoResponseDto();
        response.setArea("area teste");
        response.setNome("teste usuario");

        return response;
    }
    
    @Test
    public void teste_dto() {
    	var dto = getUsuarioInterno();
    	
    	assertEquals("area teste", dto.getArea());
    	assertEquals("teste usuario", dto.getNome());
    }

}
