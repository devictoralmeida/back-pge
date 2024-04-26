package br.gov.ce.pge.mspgeportalcolaborador.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ColaboradorResponseDto implements Serializable {

    private static final long serialVersionUID = -2238545506421270913L;

    private String nome;

    private String cpf;

    private String area;

    private Boolean desvinculado;

}
