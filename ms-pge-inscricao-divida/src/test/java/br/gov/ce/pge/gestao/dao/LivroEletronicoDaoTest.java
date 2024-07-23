package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDtoTest;
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

@ExtendWith({MockitoExtension.class})
class LivroEletronicoDaoTest {
    @InjectMocks
    private LivroEletronicoDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    void test_find_by_filter() {
        when(sqlSession.selectList(eq("LivroEletronicoDao.findByFilter"), anyMap())).thenReturn(List.of(LivroEletronicoFilterResponseDtoTest.get_livro_eletronico_filter()));

        LivroEletronicoFilterRequestDto requestFilter = LivroEletronicoFilterRequestDtoTest.getRequestFilter();
        List<LivroEletronicoFilterResponseDto> lista = dao.findByFilter(requestFilter);

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }
}
