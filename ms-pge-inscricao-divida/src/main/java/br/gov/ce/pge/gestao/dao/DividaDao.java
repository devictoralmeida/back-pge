package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaUnicaRequestDto;
import br.gov.ce.pge.gestao.dto.response.DividaFilterResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DividaDao {
  private final SqlSession sqlSession;

  public DividaDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public boolean findUnique(DividaUnicaRequestDto request) {
    int result = sqlSession.selectOne("DividaDao.findUnique", request.filters());
    return result > SharedConstant.SOMA_INICIO;
  }

  public List<DividaFilterResponseDto> findByFilterDivida(DividaFilterRequestDto request, Map<String, Object> map) {
    List<String> idsFases = (List<String>) map.get("idsFases");

    Map<String, Object> filters = request.filters();

    if((request.getFaseAtual() != null && !request.getFaseAtual().isEmpty()) || validaRequest(request)) {
      List<String>  filtroFaseAtual = idsFases.stream().filter(id -> request.getFaseAtual() != null && request.getFaseAtual().contains(id)).collect(Collectors.toList());
      filters.put("faseAtual", filtroFaseAtual.isEmpty() ? idsFases : filtroFaseAtual);
    }

    if(request.getFaseAnterior() != null && !request.getFaseAnterior().isEmpty()) {
      filters.put("faseAnterior", idsFases.stream().filter(id -> request.getFaseAnterior() != null && request.getFaseAnterior().contains(id)).collect(Collectors.toList()));
    }

    return this.sqlSession.selectList("DividaDao.findByDividaFilter", filters);
  }

  public Integer countfindByFilter(DividaFilterRequestDto request, Map<String, Object> map) {
    List<String> idsFases = (List<String>) map.get("idsFases");

    Map<String, Object> filters = request.filters();

    if((request.getFaseAtual() != null && !request.getFaseAtual().isEmpty()) || validaRequest(request)) {
      List<String> filtroFaseAtual = idsFases.stream().filter(id -> request.getFaseAtual() != null && request.getFaseAtual().contains(id)).collect(Collectors.toList());
      filters.put("faseAtual", filtroFaseAtual.isEmpty() ? idsFases : filtroFaseAtual);
    }

    if(request.getFaseAnterior() != null && !request.getFaseAnterior().isEmpty()) {
      filters.put("faseAnterior", idsFases.stream().filter(id -> request.getFaseAnterior() != null && request.getFaseAnterior().contains(id)).collect(Collectors.toList()));
    }

    return this.sqlSession.selectOne("DividaDao.countfindByFilter", filters);
  }

  private static boolean validaRequest(DividaFilterRequestDto request) {
    return (Boolean.FALSE.equals(request.getAjuizada()) || Boolean.FALSE.equals(request.getNotificada())
            || Boolean.FALSE.equals(request.getProtestada()) || Boolean.FALSE.equals(request.getQuitadoOuExtinto())
            || request.getCobrancaSuspensa() != null);
  }
}
