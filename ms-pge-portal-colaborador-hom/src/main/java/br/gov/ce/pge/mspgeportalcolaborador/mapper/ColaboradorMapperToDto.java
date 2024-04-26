package br.gov.ce.pge.mspgeportalcolaborador.mapper;

import static br.gov.ce.pge.mspgeportalcolaborador.utils.CpfUtil.cpfNumerico;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.mspgeportalcolaborador.dto.ColaboradorResponseDto;
import br.gov.ce.pge.mspgeportalcolaborador.entity.Colaborador;

public class ColaboradorMapperToDto {
	
	private ColaboradorMapperToDto() {}
	
	public static ColaboradorResponseDto toDto(Colaborador colaborador) {
		colaborador.setCpf(cpfNumerico(colaborador.getCpf()));
		
		var dto = new ColaboradorResponseDto();
		BeanUtils.copyProperties(colaborador, dto);
		return dto;
	}

}
