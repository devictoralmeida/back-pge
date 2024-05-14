package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseClientDtoTest {
  @Test
  void testConstructorAndGettersSetters() {
    Integer status = 200;
    String mensagem = "OK";
    URI uri = URI.create("http://example.com");
    List<String> errors = Arrays.asList("error1", "error2");
    List<String> warns = Arrays.asList("warn1", "warn2");
    List<String> infos = Arrays.asList("info1", "info2");
    String data = "Some data";

    ResponseClientDto<String> dto = ResponseClientDto.fromData(data, status, mensagem, uri)
            .setErrors(errors)
            .setWarns(warns)
            .setInfos(infos);

    assertEquals(data, dto.getData());
    assertEquals(status, dto.getStatus());
    assertEquals(mensagem, dto.getMensagem());
    assertEquals(uri, dto.getUri());
    assertEquals(errors, dto.getErrors());
    assertEquals(warns, dto.getWarns());
    assertEquals(infos, dto.getInfos());
  }

  @Test
  void testAddError() {
    ResponseClientDto<String> dto = ResponseClientDto.fromData("Some data", 200, "OK");

    dto.addError("error1");
    dto.addError("error2");

    List<String> errors = dto.getErrors();
    assertNotNull(errors);
    assertEquals(2, errors.size());
    assertEquals("error1", errors.get(0));
    assertEquals("error2", errors.get(1));
  }

  @Test
  void testAddWarn() {
    ResponseClientDto<String> dto = ResponseClientDto.fromData("Some data", 200, "OK");

    dto.addWarn("warn1");
    dto.addWarn("warn2");

    List<String> warns = dto.getWarns();
    assertNotNull(warns);
    assertEquals(2, warns.size());
    assertEquals("warn1", warns.get(0));
    assertEquals("warn2", warns.get(1));
  }

  @Test
  void testAddInfo() {
    ResponseClientDto<String> dto = ResponseClientDto.fromData("Some data", 200, "OK");

    dto.addInfo("info1");
    dto.addInfo("info2");

    List<String> infos = dto.getInfos();
    assertNotNull(infos);
    assertEquals(2, infos.size());
    assertEquals("info1", infos.get(0));
    assertEquals("info2", infos.get(1));
  }
}
