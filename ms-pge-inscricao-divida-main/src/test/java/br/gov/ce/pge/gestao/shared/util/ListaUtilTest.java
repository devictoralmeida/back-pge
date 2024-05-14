package br.gov.ce.pge.gestao.shared.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListaUtilTest {
  @Test
  void test_is_equals_with_equal_lists() {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = Arrays.asList(3, 2, 1);

    assertTrue(ListaUtil.isEquals(list1, list2));
  }

  @Test
  void test_is_equals_with_different_lists() {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = Arrays.asList(4, 5, 6);

    assertFalse(ListaUtil.isEquals(list1, list2));
  }

  @Test
  void test_is_equals_with_null_lists() {
    List<Integer> list1 = null;
    List<Integer> list2 = null;

    assertTrue(ListaUtil.isEquals(list1, list2));
  }

  @Test
  void test_is_equals_with_one_null_lists() {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = null;

    assertFalse(ListaUtil.isEquals(list1, list2));
  }
}
