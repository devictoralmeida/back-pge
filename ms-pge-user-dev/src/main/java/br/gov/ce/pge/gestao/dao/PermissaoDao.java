package br.gov.ce.pge.gestao.dao;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;

@Component
public class PermissaoDao extends CommonsDao {
	
	public final SqlSession sqlSession;

	public PermissaoDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int countHistorysUpdates(UUID id) {
		return this.sqlSession.selectOne("PermissaoDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
	}

	@Override
	public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Long limit) {
		return this.sqlSession.selectList("PermissaoDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
	}

}
