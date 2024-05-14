package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseDtoTest {

    @Test
    void test_from_data() {
        String data = "Test Data";
        URI uri = URI.create("http://localhost:8080/origem-debito/");
        HttpStatus status = HttpStatus.OK;
        String mensagem = "Sucesso ao Salvar Origem Debito";

        ResponseDto<String> responseDto = ResponseDto.fromData(data, status, mensagem, uri);


        assertEquals(data, responseDto.getData());
        assertEquals(uri, responseDto.getUri());
        assertEquals(status.value(), responseDto.getStatus());
        assertEquals(mensagem, responseDto.getMensagem());
    }

    @Test
    void test_get_data() {
        String data = "Test Data";

        ResponseDto<String> responseDto = new ResponseDto<String>().setData(data);

        String actualData = responseDto.getData();

        assertEquals(data, actualData);
    }

    @Test
    void test_set_data() {
        String data = "Test Data";
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setData(data);

        assertEquals(data, updatedResponseDto.getData());
    }

    @Test
    void test_get_status() {
        HttpStatus status = HttpStatus.OK;
        ResponseDto<String> responseDto = new ResponseDto<String>().setStatus(status);

        Integer actualStatus = responseDto.getStatus();

        assertEquals(status.value(), actualStatus);
    }

    @Test
    void test_set_status() {
        HttpStatus status = HttpStatus.OK;
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setStatus(status);

        assertEquals(status.value(), updatedResponseDto.getStatus());
    }

    @Test
    void test_get_mensagem() {
        String mensagem = "Sucesso ao Salvar Origem Debito";
        ResponseDto<String> responseDto = new ResponseDto<String>().setMensagem(mensagem);

        String actualMensagem = responseDto.getMensagem();

        assertEquals(mensagem, actualMensagem);
    }

    @Test
    void test_setmensagem() {
        String mensagem = "Sucesso ao Salvar Origem Debito";
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setMensagem(mensagem);

        assertEquals(mensagem, updatedResponseDto.getMensagem());
    }

    @Test
    void test_get_uri() {
        URI uri = URI.create("http://localhost:8080/origem-debito/");
        ResponseDto<String> responseDto = new ResponseDto<String>().setUri(uri);

        URI actualUri = responseDto.getUri();

        assertEquals(uri, actualUri);
    }

    @Test
    void test_set_uri() {

        URI uri = URI.create("http://localhost:8080/origem-debito/");
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setUri(uri);

        assertEquals(uri, updatedResponseDto.getUri());
    }

    @Test
    void test_get_errors() {
        List<String> errors = Arrays.asList("Erro ao Salvar Origem Debito", "Erro ao Salvar Origem Debito");
        ResponseDto<String> responseDto = new ResponseDto<String>().setErrors(errors);

        List<String> actualErrors = responseDto.getErrors();

        assertEquals(errors, actualErrors);
    }

    @Test
    void test_set_errors() {
        List<String> errors = Arrays.asList("Erro ao Salvar Origem Debito", "Erro ao Salvar Origem Debito");
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setErrors(errors);

        assertEquals(errors, updatedResponseDto.getErrors());
    }

    @Test
    void test_add_error() {
        String error = "Erro ao Salvar Origem Debito";
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.addError(error);

        assertNotNull(updatedResponseDto.getErrors());
        assertEquals(1, updatedResponseDto.getErrors().size());
        assertEquals(error, updatedResponseDto.getErrors().get(0));
    }

    @Test
    void test_get_warns() {
        List<String> warns = Arrays.asList("Warn 1", "Warn 2");
        ResponseDto<String> responseDto = new ResponseDto<String>().setWarns(warns);

        List<String> actualWarns = responseDto.getWarns();

        assertEquals(warns, actualWarns);
    }

    @Test
    void test_set_warns() {
        List<String> warns = Arrays.asList("Warn 1", "Warn 2");
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setWarns(warns);

        assertEquals(warns, updatedResponseDto.getWarns());
    }

    @Test
    void test_add_warn() {
        String warn = "Warn";
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.addWarn(warn);

        assertNotNull(updatedResponseDto.getWarns());
        assertEquals(1, updatedResponseDto.getWarns().size());
        assertEquals(warn, updatedResponseDto.getWarns().get(0));
    }

    @Test
    void test_get_infos() {
        List<String> infos = Arrays.asList("Info 1", "Info 2");
        ResponseDto<String> responseDto = new ResponseDto<String>().setInfos(infos);

        List<String> actualInfos = responseDto.getInfos();

        assertEquals(infos, actualInfos);
    }

    @Test
    void test_set_infos() {
        List<String> infos = Arrays.asList("Info 1", "Info 2");
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.setInfos(infos);

        assertEquals(infos, updatedResponseDto.getInfos());
    }

    @Test
    void test_add_info() {
        String info = "Info";
        ResponseDto<String> responseDto = new ResponseDto<String>();

        ResponseDto<String> updatedResponseDto = responseDto.addInfo(info);

        assertNotNull(updatedResponseDto.getInfos());
        assertEquals(1, updatedResponseDto.getInfos().size());
        assertEquals(info, updatedResponseDto.getInfos().get(0));
    }
}
