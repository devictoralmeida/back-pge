package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FaseDividaDao {

    private final SqlSession sqlSession;

    public FaseDividaDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<FaseDividaResponseDto> findFaseDividaByFilter(FaseDividaFilterRequestDto request) {
        return this.sqlSession.selectList("FaseDividaDao.findFaseDividaByFilter", request.filters());
    }
}
