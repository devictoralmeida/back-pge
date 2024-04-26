package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDto;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import org.springframework.beans.BeanUtils;

public class FaseDividaToModelMapper {

    private FaseDividaToModelMapper() {}

    public static FaseDivida toModel(FaseDividaRequestDto request) {
        var faseDividaModel = new FaseDivida();
        BeanUtils.copyProperties(request, faseDividaModel);
        faseDividaModel.setNome(request.getNome().trim());
        return faseDividaModel;
    }

    public static FaseDivida toModelUpdate(FaseDividaRequestDto request, FaseDivida model) {
        BeanUtils.copyProperties(request, model, "id", "fasesPermitidas");
        model.setNome(request.getNome().trim());
        return model;
    }
}
