package br.gov.ce.pge.gestao.dto.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class FluxoFaseDividaResponseDto {

    private UUID id;

    private String nome;

    private Set<UUID> fasesPermitidas = new HashSet<>();
}
