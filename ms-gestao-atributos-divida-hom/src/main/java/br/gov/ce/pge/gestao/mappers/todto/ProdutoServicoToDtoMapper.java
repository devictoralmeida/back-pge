package br.gov.ce.pge.gestao.mappers.todto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.entity.ProdutoServico;

public class ProdutoServicoToDtoMapper {

	ProdutoServicoToDtoMapper() {
	}
	
	public static ProdutoServicoResponseDto toDto(ProdutoServico model) {
		var dto = new ProdutoServicoResponseDto();
		BeanUtils.copyProperties(model, dto);
		dto.setIdsTipoReceitas(getIdsTipoReceita(model));
		return dto;
	}

	private static List<UUID> getIdsTipoReceita(ProdutoServico model) {
		return model.getTipoReceitas().stream().map(tipoReceita -> tipoReceita.getId()).collect(Collectors.toList());
	}

}
