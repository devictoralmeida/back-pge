package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LivroEletronicoDao {
  private final SqlSession sqlSession;

  public LivroEletronicoDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public List<LivroEletronicoFilterResponseDto> findByFilter(LivroEletronicoFilterRequestDto request) {
    return this.sqlSession.selectList("LivroEletronicoDao.findByFilter", request.filters());
  }
}
