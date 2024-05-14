package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.LivroEletronicoComRegistrosInscricaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LivroEletronicoComRegistrosInscricaotoDtoTest {

  @Test
  void testMapToDto() {
    List<Object[]> results = new ArrayList<>();
    Object[] result1 = {UUID.randomUUID(), "Livro Exemplo", SituacaoLivro.ABERTO,
            LocalDateTime.now().minusDays(5), LocalDateTime.now(),
            UUID.randomUUID(), "Documento 1", "CGF 1", "Nome 1", "Origem 1",
            BigDecimal.valueOf(100.00), "Usuário 1", LocalDateTime.now(),
            BigDecimal.valueOf(10.00)};
    Object[] result2 = {UUID.randomUUID(), "Livro Exemplo", SituacaoLivro.ABERTO,
            LocalDateTime.now().minusDays(5), LocalDateTime.now(),
            UUID.randomUUID(), "Documento 2", "CGF 2", "Nome 2", "Origem 2",
            BigDecimal.valueOf(200.00), "Usuário 2", LocalDateTime.now(),
            BigDecimal.valueOf(20.00)};
    results.add(result1);
    results.add(result2);

    LivroEletronicoComRegistrosInscricaoResponseDto dto = LivroEletronicoComRegistrosInscricaotoDto.mapToDto(results);

    assertNotNull(dto);

    assertEquals(result1[0], dto.getId());
    assertEquals(result1[1], dto.getNome());
    assertEquals(result1[2], dto.getSituacao());
    assertEquals(result1[3], dto.getDataAbertura());
    assertEquals(result1[4], dto.getDataFechamento());

    List<RegistroInscricaoResponseDto> registros = dto.getRegistros();
    assertNotNull(registros);
    assertEquals(2, registros.size());

    RegistroInscricaoResponseDto registro1 = registros.get(0);
    assertEquals(result1[5], registro1.getNumeroInscricao());
    assertEquals(result1[6], registro1.getDocumento());
    assertEquals(result1[7], registro1.getCgf());
    assertEquals(result1[8], registro1.getNomeRazaoSocial());
    assertEquals(result1[9], registro1.getOrigemDebito());
    assertEquals(((BigDecimal) result1[10]).add((BigDecimal) result1[13]), registro1.getValorPrincipal());
    assertEquals(result1[11], registro1.getNomeUsuario());
    assertEquals(result1[12], registro1.getDataRegistro());

    RegistroInscricaoResponseDto registro2 = registros.get(1);
    assertEquals(result2[5], registro2.getNumeroInscricao());
    assertEquals(result2[6], registro2.getDocumento());
    assertEquals(result2[7], registro2.getCgf());
    assertEquals(result2[8], registro2.getNomeRazaoSocial());
    assertEquals(result2[9], registro2.getOrigemDebito());
    assertEquals(((BigDecimal) result2[10]).add((BigDecimal) result2[13]), registro2.getValorPrincipal());
    assertEquals(result2[11], registro2.getNomeUsuario());
    assertEquals(result2[12], registro2.getDataRegistro());
  }
}
