package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
public abstract class AuditoriaDto implements Serializable {

    private static final long serialVersionUID = 1128087875896396023L;

    private String dataMovimento;

    private String nomeUsuario;

    private String dadosAntigos;

    private Situacao situacao;

    private String descricao;

}
