package br.gov.ce.pge.gestao.entity;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Data;
import lombok.ToString;


@Data
@Entity
@Table(name = "tb_permissao")
@Audited
@AuditTable("tb_permissao_aud")
@ToString
public class Permissao extends AuditoriaUser {
	
	private static final long serialVersionUID = 7901047435748597530L;

	@Id
	@GeneratedValue
	@Column(name = "ci_permissao")
	private UUID id;
	
	@Column(name = "nm_permissao")
	private String nome;

    @Column(name = "ds_codigo_identificador")
    private String codigoIdentificador;
	
	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Permissao permissao = (Permissao) obj;
        return Objects.equals(id, permissao.id) && Objects.equals(nome, permissao.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

}
