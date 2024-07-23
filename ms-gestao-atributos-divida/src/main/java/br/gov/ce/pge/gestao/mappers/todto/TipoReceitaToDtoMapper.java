package br.gov.ce.pge.gestao.mappers.todto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.TipoReceita;

public class TipoReceitaToDtoMapper {

	TipoReceitaToDtoMapper() {
	}
	public static TipoReceitaResponseDto toDto(TipoReceita model) {
		var dto = new TipoReceitaResponseDto();
		BeanUtils.copyProperties(model, dto);
		
		List<UUID> idsOrigem = model.getOrigemDebitos().stream().map(origem -> origem.getId()).collect(Collectors.toList());
		dto.setOrigemDebitos(idsOrigem);
		
		return dto;
	}

}
