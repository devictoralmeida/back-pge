package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_anexo")
public class Anexo extends AuditoriaUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_anexo")
    private UUID id;

    @Column(name = "nm_anexo", nullable = false)
    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_tipo_documento_anexo", nullable = false)
    private TipoDocumentoAnexo tipoDocumento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_divida", nullable = false)
    private Divida divida;
}
