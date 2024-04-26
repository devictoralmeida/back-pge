package br.gov.ce.pge.mspgeportalcolaborador.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ColaboradorNotFoundResponse implements Serializable {

    private static final long serialVersionUID = 8386942937611242110L;

    private Boolean success;

    private String message;
}
