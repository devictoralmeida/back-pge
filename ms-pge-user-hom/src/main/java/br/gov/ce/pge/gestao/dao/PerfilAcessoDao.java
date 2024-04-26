package br.gov.ce.pge.gestao.dao;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.SistemaFilterResponseDto;

@Component
public class PerfilAcessoDao extends CommonsDao {
	
	public final SqlSession sqlSession;
	
	public PerfilAcessoDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int countHistorysUpdates(UUID id) {
		return this.sqlSession.selectOne("PerfilAcessoDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
	}

	@Override
	public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Long limit) {
		return this.sqlSession.selectList("PerfilAcessoDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
	}
	
	public List<PerfilAcessoFilterResponseDto> findByFilter(PerfilAcessoFilterRequestDto request) {
		List<PerfilAcessoFilterResponseDto> listaPerfil = this.sqlSession.selectList("PerfilAcessoDao.findByFilter", request.filters());
		List<SistemaFilterResponseDto> listaSistema = this.sqlSession.selectList("PerfilAcessoDao.findByFilterWithSystem", request.filters());
		
		listaPerfil.stream().forEach(perfil -> perfil.setSistemas(getSistemas(listaSistema, perfil)));
		
		return request.getOrderBy() != null && request.getOrderBy().contains("sistema")? ordenacaoPorSistema(request, listaPerfil) : listaPerfil;
	}

	private List<SistemaFilterResponseDto> getSistemas(List<SistemaFilterResponseDto> listaSistema, PerfilAcessoFilterResponseDto perfil) {
		return listaSistema.stream().filter(sistema -> sistema.getIdPerfil().equals(perfil.getId())).collect(Collectors.toList());
	}

	private List<PerfilAcessoFilterResponseDto> ordenacaoPorSistema(PerfilAcessoFilterRequestDto request, List<PerfilAcessoFilterResponseDto> listaPerfil) {
	    Comparator<PerfilAcessoFilterResponseDto> comparator;

	    if (request.getOrderBy().contains("desc")) {
	        comparator = Comparator.comparing(dto -> dto.getSistemas().get(0).getNome(), Comparator.reverseOrder());
	    } else {
	        comparator = Comparator.comparing(dto -> dto.getSistemas().get(0).getNome());
	    }

	    return listaPerfil.stream().sorted(comparator).collect(Collectors.toList());
	}


	public Integer countfindByFilter(PerfilAcessoFilterRequestDto request) {
		return this.sqlSession.selectOne("PerfilAcessoDao.countfindByFilter", request.filters());
	}

}
