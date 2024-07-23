package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.SistemaDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SistemaDao extends CommonsDao {

	public final SqlSession sqlSession;

	public SistemaDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int countHistorysUpdates(UUID id) {
		return this.sqlSession.selectOne("SistemaDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
	}

	@Override
	public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Integer limit) {
		return this.sqlSession.selectList("SistemaDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
	}

	public List<SistemaDto> findAllOrdenados() {
		return this.sqlSession.selectList("SistemaDao.findAll");
	}

}
