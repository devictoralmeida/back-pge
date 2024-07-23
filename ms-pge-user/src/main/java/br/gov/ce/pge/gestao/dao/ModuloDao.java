package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ModuloDao extends CommonsDao {

	public final SqlSession sqlSession;

	public ModuloDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int countHistorysUpdates(UUID id) {
		return this.sqlSession.selectOne("ModuloDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
	}

	@Override
	public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Integer limit) {
		return this.sqlSession.selectList("ModuloDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
	}
}
