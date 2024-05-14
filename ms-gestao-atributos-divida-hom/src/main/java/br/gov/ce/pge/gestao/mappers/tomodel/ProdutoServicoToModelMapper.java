package br.gov.ce.pge.gestao.mappers.tomodel;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDto;
import br.gov.ce.pge.gestao.entity.ProdutoServico;

public class ProdutoServicoToModelMapper {

	ProdutoServicoToModelMapper() {
	}

	public static ProdutoServico toModel(ProdutoServicoRequestDto request) {
		var tipoReceitaModel = new ProdutoServico();
		BeanUtils.copyProperties(request, tipoReceitaModel);
		return tipoReceitaModel;
	}

	public static ProdutoServico toModelUpdate(@Valid ProdutoServicoUpdateRequestDto request, ProdutoServico model) {
		BeanUtils.copyProperties(request, model, "id", "codigo");
		return model;
	}
}
