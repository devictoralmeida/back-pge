package br.gov.ce.pge.gestao.dao;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDto;

@Component
public class TermoCondicaoDao {

	private final SqlSession sqlSession;

	public TermoCondicaoDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<TermoCondicaoSistemaResponseDto> findBySistema() {
		return this.sqlSession.selectList("TermoCondicaoDao.findBySistema");
	}

    public Integer countHistorysUpdates(UUID id) {
		return this.sqlSession.selectOne("TermoCondicaoDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
    }

	public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, long limit) {
		return this.sqlSession.selectList("TermoCondicaoDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
	}
}
