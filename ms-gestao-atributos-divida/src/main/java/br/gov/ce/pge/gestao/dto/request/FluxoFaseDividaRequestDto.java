package br.gov.ce.pge.gestao.dto.request;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class FluxoFaseDividaRequestDto {

    private UUID id;

    private Set<String> fasesPermitidas = new HashSet<>();
}
