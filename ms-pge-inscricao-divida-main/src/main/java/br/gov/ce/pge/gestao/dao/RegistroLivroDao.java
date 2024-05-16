package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistroLivroDao {

    private final SqlSession sqlSession;

    public RegistroLivroDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Integer countfindByFilter(RegistroLivroFilterRequestDto request) {
        return this.sqlSession.selectOne("RegistroLivroDao.countfindByFilter", request.filters());
    }

    public List<RegistroInscricaoResponseDto> findByFilterRegistroInscricao(RegistroLivroFilterRequestDto request) {
        return this.sqlSession.selectList("RegistroLivroDao.findByRegistroInscricaoFilter", request.filters());
    }

}
