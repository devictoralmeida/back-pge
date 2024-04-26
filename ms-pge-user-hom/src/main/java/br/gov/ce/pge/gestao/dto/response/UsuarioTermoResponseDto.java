package br.gov.ce.pge.gestao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioTermoResponseDto implements Serializable {

    private static final long serialVersionUID = 1421153145270029547L;

    private UUID id;
    private String nome;
    private String orgao;
    private LocalDateTime dataAceiteTermoPortalDivida;
    private LocalDateTime dataAceiteTermoPortalOrigens;

}
