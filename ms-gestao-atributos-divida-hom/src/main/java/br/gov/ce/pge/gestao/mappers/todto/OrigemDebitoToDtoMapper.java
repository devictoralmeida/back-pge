package br.gov.ce.pge.gestao.mappers.todto;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;

public class OrigemDebitoToDtoMapper {

	private OrigemDebitoToDtoMapper() {}
	
	public static OrigemDebitoResponseDto toDto(OrigemDebito model) {
		var origemDebitoDto = new OrigemDebitoResponseDto();
		BeanUtils.copyProperties(model, origemDebitoDto);
		return origemDebitoDto;
	}

}
