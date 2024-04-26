package br.gov.ce.pge.gestao.mappers.todto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.CorresponsavelResponseDto;
import br.gov.ce.pge.gestao.entity.Corresponsavel;

public class CorresponsavelMapperToDto {

	private CorresponsavelMapperToDto() {
	}

	public static List<CorresponsavelResponseDto> converterList(List<Corresponsavel> corresponsaveis) {
		return corresponsaveis.stream().map(CorresponsavelMapperToDto::converter).collect(Collectors.toList());
	}

	public static CorresponsavelResponseDto converter(Corresponsavel corresponsavel) {
		var dto = new CorresponsavelResponseDto();
		BeanUtils.copyProperties(corresponsavel, dto);
		return dto;
	}

}
