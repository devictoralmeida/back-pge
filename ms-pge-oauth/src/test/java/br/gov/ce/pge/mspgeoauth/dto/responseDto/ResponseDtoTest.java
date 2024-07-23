package br.gov.ce.pge.mspgeoauth.dto.responseDto;

import br.gov.ce.pge.mspgeoauth.constants.SharedConstant;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.entity.UsuarioTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertsParametros(responseDto, responseDtoTeste, responseDtoTesteErros, responseDtoTesteUri);

        assertsIgualdadeParametros(responseDto, responseDtoTeste, responseDtoTesteErros, responseDtoTesteUri);

        assertUsuario(responseDto.getData());
    }

    private void assertUsuario(Usuario data) {
        assertEquals(UsuarioTest.getUsuarioExterno().getId(), data.getId());
        assertEquals(UsuarioTest.getUsuarioExterno().getTipoUsuario(), data.getTipoUsuario());
        assertEquals(UsuarioTest.getUsuarioExterno().getCpf(), data.getCpf());
        assertEquals(UsuarioTest.getUsuarioExterno().getUsuarioRede(), data.getUsuarioRede());
        assertEquals(UsuarioTest.getUsuarioExterno().getSenha(), data.getSenha());
        assertEquals(UsuarioTest.getUsuarioExterno().getNome(), data.getNome());
        assertEquals(UsuarioTest.getUsuarioExterno().getSituacao(), data.getSituacao());
    }

    private void assertsIgualdadeParametros(ResponseDto<Usuario> responseDto, ResponseDto<Usuario> responseDtoTeste, ResponseDto<Usuario> responseDtoTesteErros, ResponseDto<Usuario> responseDtoTesteUri) {
        assertEquals("error", responseDto.getErrors().get(SharedConstant.INDICE_INICIAL));
        assertEquals("123", responseDto.getWarns().get(SharedConstant.INDICE_INICIAL));
        assertEquals("infos", responseDto.getInfos().get(SharedConstant.INDICE_INICIAL));
        assertEquals("mensagem", responseDto.getMensagem());
        assertEquals(URI.create("124"), responseDto.getUri());
        assertEquals(200, responseDto.getStatus());
    }

    private void assertsParametros(ResponseDto<Usuario> responseDto, ResponseDto<Usuario> responseDtoTeste, ResponseDto<Usuario> responseDtoTesteErros, ResponseDto<Usuario> responseDtoTesteUri) {
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
