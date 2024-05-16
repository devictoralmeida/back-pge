package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistroInscricaoResponseDtoTest {

  public static RegistroInscricaoResponseDto getRegistroInscricaoPagina() {
    var registro = new RegistroInscricaoResponseDto();
    registro.setPagina(1);

    return registro;
  }

  public static RegistroInscricaoResponseDto getRegistroInscricaoLinha() {
    var registro = new RegistroInscricaoResponseDto();
    registro.setLinha(1);

    return registro;
  }

  @Test
  void test_getter_setter() {
    RegistroInscricaoResponseDto dto = new RegistroInscricaoResponseDto();
    UUID numeroInscricao = UUID.randomUUID();
    String documento = "12345678900";
    String cgf = "123456";
    String nomeRazaoSocial = "Fulano de Tal";
    String origemDebito = "Origem";
    BigDecimal valorPrincipal = BigDecimal.valueOf(1000.0);
    String nomeUsuario = "Usuário";
    LocalDateTime dataRegistro = LocalDateTime.now();

    dto.setNumeroInscricao(numeroInscricao.toString());
    dto.setDocumento(documento);
    dto.setCgf(cgf);
    dto.setNomeRazaoSocial(nomeRazaoSocial);
    dto.setOrigemDebito(origemDebito);
    dto.setValorPrincipal(valorPrincipal);
    dto.setNomeUsuario(nomeUsuario);
    dto.setDataRegistro(dataRegistro);

    asserts(dto, numeroInscricao, documento, cgf, nomeRazaoSocial, origemDebito, valorPrincipal, nomeUsuario, dataRegistro);
  }

  private void asserts(RegistroInscricaoResponseDto dto, UUID numeroInscricao, String documento, String cgf, String nomeRazaoSocial, String origemDebito, BigDecimal valorPrincipal, String nomeUsuario, LocalDateTime dataRegistro) {
    assertEquals(numeroInscricao.toString(), dto.getNumeroInscricao());
    assertEquals(documento, dto.getDocumento());
    assertEquals(cgf, dto.getCgf());
    assertEquals(nomeRazaoSocial, dto.getNomeRazaoSocial());
    assertEquals(origemDebito, dto.getOrigemDebito());
    assertEquals(valorPrincipal, dto.getValorPrincipal());
    assertEquals(nomeUsuario, dto.getNomeUsuario());
    assertEquals(dataRegistro, dto.getDataRegistro());
  }
}
