package br.gov.ce.pge.gestao.shared.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ValidateDadosUtilTest {

  @Test
  void test_com_apenas_letra() {
    var teste = ValidateDadosUtil.contemApenasLetras("teste do ivo");
    assertEquals(teste, true);
  }

  @Test
  void test_com_apenas_letra_erro_usando_numero() {
    var teste = ValidateDadosUtil.contemApenasLetras("teste do ivo 01");
    assertEquals(teste, false);
  }

  @Test
  void test_com_apenas_letra_erro_usando_caracter_especial() {
    var teste = ValidateDadosUtil.contemApenasLetras("teste do ivo@gmail");
    assertEquals(teste, false);
  }

  @Test
  void test_com_apenas_numero() {
    var teste = ValidateDadosUtil.contemApenasNumeros("123");
    assertEquals(teste, true);
  }

  @Test
  void test_com_apenas_numero_erro_usando_letra() {
    var teste = ValidateDadosUtil.contemApenasNumeros("123a");
    assertEquals(teste, false);
  }

  @Test
  void test_com_apenas_numero_erro_usando_caractere_especial() {
    var teste = ValidateDadosUtil.contemApenasNumeros("123#");
    assertEquals(teste, false);
  }

  @Test
  void test_com_apenas_letras_numeros() {
    var teste = ValidateDadosUtil.contemApenasLetrasEnumero("teste 01");
    assertEquals(teste, true);
  }

  @Test
  void test_com_apenas_letras_numeros_erro_usando_caractere_especial() {
    var teste = ValidateDadosUtil.contemApenasLetrasEnumero("teste $$");
    assertEquals(teste, false);
  }

  @Test
  void test_is_null_or_vazio_retorna_true_lista_nula() {
    var result = ValidateDadosUtil.isNullOrVazio(null);
    assertTrue(result);
  }

  @Test
  void test_is_null_or_vazio_retorna_true_lista_vazia() {
    var result = ValidateDadosUtil.isNullOrVazio(new ArrayList<>());
    assertTrue(result);
  }

  @Test
  void test_is_null_or_vazio_retorna_false_lista_preenchida() {
    var result = ValidateDadosUtil.isNullOrVazio(Arrays.asList("Hello", "World"));
    assertFalse(result);
  }

}
