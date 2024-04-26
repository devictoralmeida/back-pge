package br.gov.ce.pge.gestao.mappers.todto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.DebitoResponseDto;
import br.gov.ce.pge.gestao.entity.Debito;

public class DebitoMapperToDto {
	
	private DebitoMapperToDto() {}
	
	public static List<DebitoResponseDto> converterList(List<Debito> debitos) {
		return debitos.stream().map(DebitoMapperToDto::converter).collect(Collectors.toList());
	}

	public static DebitoResponseDto converter(Debito debito) {
		var dto = new DebitoResponseDto();
		BeanUtils.copyProperties(debito, dto);
		dto.setSaldoDevedor(debito.getValorPrincipal().add(debito.getValorMulta()));
		return dto;
	}
}
