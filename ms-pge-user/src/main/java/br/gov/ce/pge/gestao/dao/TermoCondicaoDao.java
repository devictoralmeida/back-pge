package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TermoCondicaoDao {

	private final SqlSession sqlSession;

	public TermoCondicaoDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<TermoCondicaoSistemaResponseDto> findBySistema() {
		return sqlSession.selectList("TermoCondicaoDao.findBySistema");
	}

    public Integer countHistorysUpdates(UUID id) {
			return sqlSession.selectOne("TermoCondicaoDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
    }

	public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Integer limit) {
		return sqlSession.selectList("TermoCondicaoDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
	}
}
