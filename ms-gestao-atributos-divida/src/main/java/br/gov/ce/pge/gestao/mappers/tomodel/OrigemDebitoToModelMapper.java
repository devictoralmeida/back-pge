package br.gov.ce.pge.gestao.mappers.tomodel;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;

public class OrigemDebitoToModelMapper {

	private OrigemDebitoToModelMapper() {}
	
	public static OrigemDebito toModel(OrigemDebitoRequestDto request, String nomeUsuario) {
		var origemDebitoModel = new OrigemDebito();
		BeanUtils.copyProperties(request, origemDebitoModel);
		origemDebitoModel.setNome(request.getNome().trim());
		origemDebitoModel.setNomeUsuario(nomeUsuario);
		return origemDebitoModel;
	}

	public static OrigemDebito toModelUpdate(OrigemDebitoRequestDto request, OrigemDebito model, String nomeUsuario) {
		BeanUtils.copyProperties(request, model, "id");
		model.setNome(request.getNome().trim());
		model.setNomeUsuario(nomeUsuario);
		return model;
	}
}
