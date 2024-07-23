package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_tipo_documento_anexo")
public class TipoDocumentoAnexo extends AuditoriaUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_tipo_documento_anexo")
    private UUID id;

    @Column(name = "tp_documento", length = 50, nullable = false)
    private String tipoDocumento;
}
