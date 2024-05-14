package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.StatusInscricao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InscricaoResponseDtoTest {

  public static InscricaoResponseDto getIncricaoSomenteComDevedor() {
    var dto = new InscricaoResponseDto();
    dto.setDevedor(DevedorResponseDtoTest.get_devedor_pessoa_fisica());

    return dto;
  }

  public static InscricaoResponseDto getIncricaoComDevedorDivida() {
    var dto = new InscricaoResponseDto();
    dto.setDevedor(DevedorResponseDtoTest.get_devedor_pessoa_fisica());
    dto.setDivida(DividaResponseDtoTest.get_divida());

    return dto;
  }

  public static InscricaoResponseDto getIncricaoComDevedorDividaStatus() {
    var dto = new InscricaoResponseDto();
    dto.setDevedor(DevedorResponseDtoTest.get_devedor_pessoa_fisica());
    dto.setDivida(DividaResponseDtoTest.get_divida());
    dto.setStatus(StatusInscricao.EM_ANALISE);

    return dto;
  }

  public static InscricaoResponseDto getIncricaoComDevedorDividaStatusCorresponsaveis() {
    var dto = new InscricaoResponseDto();
    dto.setDevedor(DevedorResponseDtoTest.get_devedor_pessoa_fisica());
    dto.setDivida(DividaResponseDtoTest.get_divida());
    dto.setStatus(StatusInscricao.EM_ANALISE);
    dto.setCorresponsaveis(CorresponsavelResponseDtoTest.getListCorresponsaveis());
    return dto;
  }

  public static InscricaoResponseDto getIncricaoCompleta() {
    var dto = new InscricaoResponseDto();
    dto.setId(UUID.fromString("c8bf3eff-2d65-46f7-bb6a-8f4c93be7886"));
    dto.setDevedor(DevedorResponseDtoTest.get_devedor_pessoa_fisica());
    dto.setDivida(DividaResponseDtoTest.get_divida());
    dto.setStatus(StatusInscricao.EM_ANALISE);
    dto.setCorresponsaveis(CorresponsavelResponseDtoTest.getListCorresponsaveis());
    dto.setDebitos(DebitoResponseDtoTest.getDebitos());
    return dto;
  }

  @Test
  void teste_inscricao_incompleto() {
    var dto = getIncricaoSomenteComDevedor();

    assertNotNull(dto.getDevedor());
    assertNull(dto.getDivida());
    assertNull(dto.getStatus());

    dto = getIncricaoComDevedorDivida();

    assertNotNull(dto.getDevedor());
    assertNotNull(dto.getDivida());
    assertEquals(null, dto.getStatus());

    dto = getIncricaoComDevedorDividaStatus();

    assertNotNull(dto.getDevedor());
    assertNotNull(dto.getDivida());
    assertEquals(StatusInscricao.EM_ANALISE, dto.getStatus());

    dto = getIncricaoComDevedorDividaStatusCorresponsaveis();

    assertNotNull(dto.getDevedor());
    assertNotNull(dto.getDivida());
    assertEquals(StatusInscricao.EM_ANALISE, dto.getStatus());
    assertNotNull(dto.getCorresponsaveis());
    assertEquals(2, dto.getCorresponsaveis().size());
    assertEquals("Kamila Lima", dto.getCorresponsaveis().get(0).getNomeRazaoSocial());
    assertEquals("Osvaldo e Julio Marketing ME", dto.getCorresponsaveis().get(1).getNomeRazaoSocial());

    dto = getIncricaoCompleta();

    assertNotNull(dto.getDevedor());
    assertNotNull(dto.getDivida());
    assertEquals(StatusInscricao.EM_ANALISE, dto.getStatus());
    assertNotNull(dto.getCorresponsaveis());
    assertEquals(2, dto.getCorresponsaveis().size());
    assertEquals("Kamila Lima", dto.getCorresponsaveis().get(0).getNomeRazaoSocial());
    assertEquals("Osvaldo e Julio Marketing ME", dto.getCorresponsaveis().get(1).getNomeRazaoSocial());
    assertEquals(BigDecimal.valueOf(150), dto.getDebitos().get(0).getValorPrincipal());
    assertEquals(BigDecimal.valueOf(50), dto.getDebitos().get(0).getValorMulta());
  }

}
