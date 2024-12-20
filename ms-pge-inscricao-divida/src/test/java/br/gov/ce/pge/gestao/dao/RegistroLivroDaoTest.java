package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDtoTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({ MockitoExtension.class })
class RegistroLivroDaoTest {

  @InjectMocks
  private RegistroLivroDao dao;

  @Mock
  private transient SqlSession sqlSession;

  @Test
  void test_count_by_filter() {
    when(sqlSession.selectOne(eq("RegistroLivroDao.countfindByFilter"), anyMap())).thenReturn(1);

    Integer count = dao.countfindByFilter(RegistroLivroFilterRequestDtoTest.getRequestFilterPagina());

    assertNotNull(count);
    assertEquals(1, count);
  }

  @Test
  void test_find_by_filter() {
    when(sqlSession.selectList(eq("RegistroLivroDao.findByRegistroInscricaoFilter"), anyMap())).thenReturn(List.of(RegistroInscricaoResponseDtoTest.getRegistroInscricaoPagina()));
    List<RegistroInscricaoResponseDto> registros = dao.findByFilterRegistroInscricao(RegistroLivroFilterRequestDtoTest.getRequestFilterPagina());

    assertNotNull(registros);
    assertEquals(1, registros.size());
  }

}
