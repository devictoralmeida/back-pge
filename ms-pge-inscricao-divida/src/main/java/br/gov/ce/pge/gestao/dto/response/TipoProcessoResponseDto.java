package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class TipoProcessoResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -2182897787620289948L;

    private UUID id;
    private String nome;
}
