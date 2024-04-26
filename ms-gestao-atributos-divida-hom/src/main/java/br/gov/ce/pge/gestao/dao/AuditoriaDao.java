package br.gov.ce.pge.gestao.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.enums.TipoMovimento;
import br.gov.ce.pge.gestao.shared.exception.BadValueException;

@Component
public class AuditoriaDao {

    private static final String TIPO_MOVIMENTO = "tipoMovimento";
    
	private final SqlSession sqlSession;

    public AuditoriaDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int countHistorysUpdates(UUID idOrigemDebito) {
        Map<String, Object> params = new HashMap<>();
        params.put("idOrigemDebito", idOrigemDebito);
        params.put(TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
        return this.sqlSession.selectOne("AuditoriaDao.countHistorysUpdatesOrigemDebito", params);
    }

    public int countHistorysUpdatesFaseDivida(UUID idFaseDivida) {
        Map<String, Object> params = new HashMap<>();
        params.put("idFaseDivida", idFaseDivida);
        params.put(TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
        return this.sqlSession.selectOne("AuditoriaDao.countHistorysUpdatesFaseDivida", params);
    }

    public int countHistorysUpdatesProdutoServico(UUID idProdutoServico) {
        Map<String, Object> params = new HashMap<>();
        params.put("idProdutoServico", idProdutoServico);
        params.put(TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
        return this.sqlSession.selectOne("AuditoriaDao.countHistorysUpdatesProdutoServico", params);
    }

    public int countHistorysUpdatesTipoReceita(UUID idTipoReceita) {
        Map<String, Object> params = new HashMap<>();
        params.put("idTipoReceita", idTipoReceita);
        params.put(TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
        return this.sqlSession.selectOne("AuditoriaDao.countHistorysUpdatesTipoReceita", params);
    }

    public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Long limit, String source) {

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("tipoMovimentoInsert", TipoMovimento.CADASTRAR.getValue());
        params.put("tipoMovimentoDelete", TipoMovimento.DELETAR.getValue());

        switch (source) {
            case "ProdutoServico":
                params.put("idProdutoServico", id);
                return this.sqlSession.selectList("AuditoriaDao.findHistorysUpdatesProdutoServico", params);
            case "TipoReceita":
                params.put("idTipoReceita", id);
                return this.sqlSession.selectList("AuditoriaDao.findHistorysUpdatesTipoReceita", params);
            case "OrigemDebito":
                params.put("idOrigemDebito", id);
                return this.sqlSession.selectList("AuditoriaDao.findHistorysUpdates", params);
            case "FaseDivida":
                params.put("idFaseDivida", id);
                return this.sqlSession.selectList("AuditoriaDao.findHistorysUpdatesFaseDivida", params);
            default:
                throw new BadValueException("Chave desconhecida = " + source);
        }
    }

}
