package br.gov.ce.pge.gestao.mappers.todto;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.DevedorResponseDto;
import br.gov.ce.pge.gestao.entity.Devedor;

public class DevedorMapperToDto {
	
	private DevedorMapperToDto() {}

	public static DevedorResponseDto converter(Devedor devedor) {
		var dto = new DevedorResponseDto();
		BeanUtils.copyProperties(devedor, dto);
		return dto;
	}
	
}
