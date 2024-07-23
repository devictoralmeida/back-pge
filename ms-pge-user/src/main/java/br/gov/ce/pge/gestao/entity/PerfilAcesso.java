package br.gov.ce.pge.gestao.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name = "tb_perfil_acesso")
@Audited
@AuditTable("tb_perfil_acesso_aud")
public class PerfilAcesso extends AuditoriaUser {
	
	private static final long serialVersionUID = 8094849596093365365L;
	
	@Id
	@GeneratedValue
	@Column(name = "ci_perfil_acesso")
	private UUID id;
	
	@Column(name = "nm_perfil_acesso")
	private String nome;
	
	@Column(name = "ds_situacao_perfil_acesso")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	@ManyToMany
	@JoinTable(name = "tb_perfil_acesso_sistema",
		joinColumns = @JoinColumn(name = "cd_perfil_acesso"),
        inverseJoinColumns = @JoinColumn(name = "cd_sistema"))
	private List<Sistema> sistemas;
	
	@ManyToMany
	@JoinTable(name = "tb_perfil_permissao",
		joinColumns = @JoinColumn(name = "cd_perfil_acesso"),
        inverseJoinColumns = @JoinColumn(name = "cd_permissao"))
	private List<Permissao> permissoes;

	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

}
