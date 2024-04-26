package br.gov.ce.pge.gestao.mappers.tomodel;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;

public class OrigemDebitoToModelMapper {

	private OrigemDebitoToModelMapper() {}
	
	public static OrigemDebito toModel(OrigemDebitoRequestDto request) {
		var origemDebitoModel = new OrigemDebito();
		BeanUtils.copyProperties(request, origemDebitoModel);
		origemDebitoModel.setNome(request.getNome().trim());
		return origemDebitoModel;
	}

	public static OrigemDebito toModelUpdate(OrigemDebitoRequestDto request, OrigemDebito model) {
		BeanUtils.copyProperties(request, model, "id");
		model.setNome(request.getNome().trim());
		return model;
	}
}
