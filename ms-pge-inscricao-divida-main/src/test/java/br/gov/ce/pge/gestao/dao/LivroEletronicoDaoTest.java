package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDtoTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class LivroEletronicoDaoTest {
  @InjectMocks
  @Autowired
  private LivroEletronicoDao dao;

  @Mock
  private transient SqlSession sqlSession;

  @Test
  void testFindByFilter() {
    when(this.sqlSession.selectList(eq("LivroEletronicoDao.findByFilter"), anyMap())).thenReturn(List.of(LivroEletronicoFilterResponseDtoTest.getLivroEletronicoFilter()));

    LivroEletronicoFilterRequestDto requestFilter = LivroEletronicoFilterRequestDtoTest.getRequestFilter();
    var lista = this.dao.findByFilter(requestFilter);

    assertNotNull(lista);
    assertEquals(1, lista.size());
  }
}
