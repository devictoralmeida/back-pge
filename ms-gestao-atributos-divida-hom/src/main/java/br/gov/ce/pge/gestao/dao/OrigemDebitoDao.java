package br.gov.ce.pge.gestao.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDto;

@Component
public class OrigemDebitoDao {
	
	private final SqlSession sqlSession;

    public OrigemDebitoDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    public List<OrigemDebitoConsultaResponseDto> findOrigemDebitosByFilter(OrigemDebitoFilterRequestDto request) {
		return this.sqlSession.selectList("OrigemDebitoDao.findOrigemDebitosByFilter", request.filters());
	}

}
