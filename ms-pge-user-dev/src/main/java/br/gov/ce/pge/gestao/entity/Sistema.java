package br.gov.ce.pge.gestao.entity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Data;


@Data
@Entity
@Table(name = "tb_sistema")
@Audited
@AuditTable("tb_sistema_aud")
public class Sistema extends AuditoriaUser {
	
	private static final long serialVersionUID = -8541592559556886825L;
	
	@Id
	@GeneratedValue
	@Column(name = "ci_sistema")
	private UUID id;
	
	@Column(name = "nm_sistema")
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "tb_sistema_modulo",
		joinColumns = @JoinColumn(name = "cd_sistema"),
        inverseJoinColumns = @JoinColumn(name = "cd_modulo"))
	private List<Modulo> modulos;
	
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
		Sistema sistema = (Sistema) obj;
		return id.equals(sistema.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
