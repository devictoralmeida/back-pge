package br.gov.ce.pge.gestao.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.enums.TipoMovimento;

@Component
public abstract class CommonsDao {
	
	private static final String LIMIT = "limit";
	private static final String OFFSET = "offset";
	private static final String TIPO_MOVIMENTO = "tipoMovimento";
	private static final String ID = "id";

	public abstract int countHistorysUpdates(UUID id);
	
	public abstract List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Long limit);
	
	public static Map<String, Object> getParamsCount(UUID id) {
		Map<String, Object> params = new HashMap<>();
		params.put(ID, id);
		params.put(TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
		return params;
	}
	
	public static Map<String, Object> getParamsHistorys(UUID id, Long offset, Long limit) {
		Map<String, Object> params = new HashMap<>();
		params.put(OFFSET, offset);
		params.put(LIMIT, limit);

		params.put(ID, id);
		params.put(TIPO_MOVIMENTO, TipoMovimento.DELETAR.getValue());
		return params;
	}
	
}
