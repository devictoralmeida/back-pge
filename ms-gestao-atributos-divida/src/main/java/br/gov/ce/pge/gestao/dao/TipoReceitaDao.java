package br.gov.ce.pge.gestao.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaConsultaFilterResponseDto;

@Component
public class TipoReceitaDao {
	
	private final SqlSession sqlSession;

    public TipoReceitaDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    public List<TipoReceitaConsultaFilterResponseDto> findTipoReceitasByFilter(TipoReceitaFilterRequestDto request) {
		return this.sqlSession.selectList("TipoReceitaDao.findTipoReceitasByFilter", request.filters());
	}

}
