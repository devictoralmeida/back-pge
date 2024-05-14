package br.gov.ce.pge.gestao.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FluxoFaseDividaWrapperRequestDto {
    private List<FluxoFaseDividaRequestDto> fases = new ArrayList<>();
}
