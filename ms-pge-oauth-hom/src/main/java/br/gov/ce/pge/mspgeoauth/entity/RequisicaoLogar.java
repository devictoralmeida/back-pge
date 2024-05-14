package br.gov.ce.pge.mspgeoauth.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RequisicaoLogar {

    private UUID id;

    private LocalDateTime horaRequisicao;

    private boolean sucesso;

    private Usuario usuario;
}
