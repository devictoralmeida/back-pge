package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.FluxoFaseDividaResponseDto;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class FaseDividaToDtoMapper {

    FaseDividaToDtoMapper() {}

    public static FaseDividaResponseDto toDto(FaseDivida model) {
        var faseDividaDto = new FaseDividaResponseDto();
        BeanUtils.copyProperties(model, faseDividaDto);
        faseDividaDto.setId(model.getId().toString());
        return faseDividaDto;
    }

    public static FluxoFaseDividaResponseDto toFluxoFase(FaseDivida model) {
        FluxoFaseDividaResponseDto fluxo = new FluxoFaseDividaResponseDto();
        fluxo.setNome(model.getNome());
        fluxo.setId(model.getId());
        fluxo.setFasesPermitidas(model.getFasesPermitidas().stream().map(FaseDivida::getId).collect(Collectors.toSet()));
        return fluxo;
    }
}
