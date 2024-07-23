package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.DevedorUnicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.DevedorUnicoRequestDtoTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class DevedorDaoTest {
  @InjectMocks
  private DevedorDao dao;

  @Mock
  private transient SqlSession sqlSession;

  @Test
  void test_find_unique() {
    when(sqlSession.selectOne(eq("DevedorDao.findUnique"), anyMap())).thenReturn(1);

    DevedorUnicoRequestDto request = DevedorUnicoRequestDtoTest.getFilters();
    boolean result = dao.findUnique(request);

    assertTrue(result);
  }

  @Test
  void test_find_unique_false() {
    when(sqlSession.selectOne(eq("DevedorDao.findUnique"), anyMap())).thenReturn(0);

    DevedorUnicoRequestDto request = DevedorUnicoRequestDtoTest.getFilters();
    boolean result = dao.findUnique(request);

    assertFalse(result);
  }

}
