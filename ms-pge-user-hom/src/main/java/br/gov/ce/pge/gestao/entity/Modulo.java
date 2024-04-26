package br.gov.ce.pge.gestao.entity;

import java.util.List;
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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Entity
@Table(name = "tb_modulo")
@Audited
@AuditTable("tb_modulo_aud")
@ToString
public class Modulo extends AuditoriaUser {
	
	private static final long serialVersionUID = 6628053593304009069L;
	
	@Id
	@GeneratedValue
	@Column(name = "ci_modulo")
	private UUID id;
	
	@Column(name = "nm_modulo")
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "tb_modulo_permissao",
		joinColumns = @JoinColumn(name = "cd_modulo"),
        inverseJoinColumns = @JoinColumn(name = "cd_permissao"))
	private List<Permissao> permissoes;
	
	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

}
