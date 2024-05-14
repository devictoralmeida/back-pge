package br.gov.ce.pge.mspgeoauth.dto.responseDto;

import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.entity.UsuarioTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseDtoTest {

    @Test
    void test_response_dto() {

        ResponseDto<Usuario> responseDto = new ResponseDto<>();

        responseDto.setData(UsuarioTest.getUsuarioExterno());
        responseDto.setErrors(Arrays.asList("error"));
        responseDto.setInfos(Arrays.asList("infos"));
        responseDto.setMensagem("mensagem");
        responseDto.setStatus(200);
        responseDto.setWarns(Arrays.asList("123"));
        responseDto.setUri(URI.create("124"));

        ResponseDto<Usuario> responseDtoTeste = ResponseDto.fromData(UsuarioTest.getUsuarioInativo(), 200, "teste");
        ResponseDto<Usuario> responseDtoTesteErros = ResponseDto.fromData(UsuarioTest.getUsuarioInativo(), 200, "teste", Arrays.asList("erros"));
        ResponseDto<Usuario> responseDtoTesteUri = ResponseDto.fromData(UsuarioTest.getUsuarioInativo(), 200, "teste", URI.create("123"));

        assertNotNull(responseDto.getData());
        assertNotNull(responseDto.getErrors());
        assertNotNull(responseDto.getInfos());
        assertNotNull(responseDto.getMensagem());
        assertNotNull(responseDto.getWarns());
        assertNotNull(responseDto.getUri());
        assertNotNull(responseDtoTeste);
        assertNotNull(responseDtoTesteErros);
        assertNotNull(responseDtoTesteUri);
    }

}
