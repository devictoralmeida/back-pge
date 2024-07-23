package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.enums.TipoMovimento;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public abstract class CommonsDao {

	public abstract int countHistorysUpdates(UUID id);

	public abstract List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Integer limit);

	public static Map<String, Object> getParamsCount(UUID id) {
		Map<String, Object> params = new HashMap<>();
		params.put(SharedConstant.ID, id);
		params.put(SharedConstant.TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
		return params;
	}

	public static Map<String, Object> getParamsHistorys(UUID id, Long offset, Integer limit) {
		Map<String, Object> params = new HashMap<>();
		params.put(SharedConstant.OFFSET, offset);
		params.put(SharedConstant.LIMIT, limit);

		params.put(SharedConstant.ID, id);
		params.put(SharedConstant.TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
		return params;
	}

}
