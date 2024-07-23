package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.DividaUnicaRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaUnicaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.DividaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.DividaFilterResponseDtoTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class DividaDaoTest {
  @InjectMocks
  private DividaDao dao;

  @Mock
  private transient SqlSession sqlSession;

  @Test
  void test_find_unique() {
    when(sqlSession.selectOne(eq("DividaDao.findUnique"), anyMap())).thenReturn(1);

    DividaUnicaRequestDto request = DividaUnicaRequestDtoTest.getFilters();
    boolean result = dao.findUnique(request);

    assertTrue(result);
  }

  @Test
  void test_find_unique_false() {
    when(sqlSession.selectOne(eq("DividaDao.findUnique"), anyMap())).thenReturn(0);

    DividaUnicaRequestDto request = DividaUnicaRequestDtoTest.getFilters();
    boolean result = dao.findUnique(request);

    assertFalse(result);
  }

  @Test
  void test_count_filter() {
    when(sqlSession.selectOne(eq("DividaDao.countfindByFilter"), anyMap())).thenReturn(1);

    DividaFilterRequestDto request = DividaFilterRequestDtoTest.getRequest();

    Map<String, Object> filters = request.filters();
    filters.put("idsFases", Arrays.asList("19e36372-0d0a-44ad-8a7c-f5ba842bcabb"));

    Integer totalRegistros = dao.countfindByFilter(request, filters);

    assertEquals(1, totalRegistros);
  }

  @Test
  void test_find_by_filter() {
    when(sqlSession.selectList(eq("DividaDao.findByDividaFilter"), anyMap())).thenReturn(List.of(DividaFilterResponseDtoTest.getDividaFilter()));

    DividaFilterRequestDto request = DividaFilterRequestDtoTest.getRequest();

    Map<String, Object> filters = request.filters();
    filters.put("idsFases", Arrays.asList("19e36372-0d0a-44ad-8a7c-f5ba842bcabb"));

    List<DividaFilterResponseDto> filter = dao.findByFilterDivida(request, filters);

    asserts(filter);
    assertNotNull(filter);
  }

  private void asserts(List<DividaFilterResponseDto> filter) {
    assertNotNull(filter);
    assertEquals("1215d61e-5154-48a8-845e-b6be24f7a7bc", filter.get(SharedConstant.INICIO_INDEX).getId());
    assertEquals(true, filter.get(SharedConstant.INICIO_INDEX).getStatusCobranca());
    assertEquals("12434", filter.get(SharedConstant.INICIO_INDEX).getNumeroInscricao());
    assertEquals("12345678912", filter.get(SharedConstant.INICIO_INDEX).getDocumento());
    assertEquals("123456789", filter.get(SharedConstant.INICIO_INDEX).getCgf());
  }

}
