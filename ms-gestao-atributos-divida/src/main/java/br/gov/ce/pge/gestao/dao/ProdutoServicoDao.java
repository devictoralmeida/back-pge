package br.gov.ce.pge.gestao.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDto;

@Component
public class ProdutoServicoDao {
	
	private final SqlSession sqlSession;

    public ProdutoServicoDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    public List<ProdutoServicoConsultaFilterResponseDto> findTipoReceitasByFilter(ProdutoServicoFilterRequestDto request) {
		return this.sqlSession.selectList("ProdutoServicoDao.findProdutoServicosByFilter", request.filters());
	}

}
