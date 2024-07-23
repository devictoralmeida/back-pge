package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DevedorUnicoRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class DevedorDao {
  private final SqlSession sqlSession;

  public DevedorDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public boolean findUnique(DevedorUnicoRequestDto request) {
    int result = sqlSession.selectOne("DevedorDao.findUnique", request.filters());
    return result > SharedConstant.SOMA_INICIO;
  }

}
