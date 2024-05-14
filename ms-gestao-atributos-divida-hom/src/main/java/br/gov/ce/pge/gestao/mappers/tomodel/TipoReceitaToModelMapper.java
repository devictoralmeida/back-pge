package br.gov.ce.pge.gestao.mappers.tomodel;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDto;
import br.gov.ce.pge.gestao.entity.TipoReceita;

public class TipoReceitaToModelMapper {

	TipoReceitaToModelMapper() {}
	public static TipoReceita toModel(TipoReceitaRequestDto request) {
		var tipoReceitaModel = new TipoReceita();
		BeanUtils.copyProperties(request, tipoReceitaModel);
		return tipoReceitaModel;
	}

	public static TipoReceita toModelUpdate(@Valid TipoReceitaUpdateRequestDto request, TipoReceita model) {
		BeanUtils.copyProperties(request, model, "id", "codigo");
		return model;
	}
}
