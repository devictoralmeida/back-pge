package br.gov.ce.pge.gestao.entity;

import java.io.Serializable;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Table(name = "tb_tipo_receita")
@AuditTable("tb_tipo_receita_aud")
public class TipoReceita extends AuditoriaUser implements Serializable {
	
	private static final long serialVersionUID = -3770169452237675929L;

	@Expose
	@Id
	@GeneratedValue
	@Column(name = "ci_tipo_receita")
	private UUID id;

	@Expose
	@Column(name = "cd_tipo_receita", unique = true)
	private String codigo;

	@Expose
	@Column(name = "ds_tipo_receita")
	private String descricao;

	@ManyToMany
	@JoinTable(name = "tb_tipo_receita_origem",
		joinColumns = @JoinColumn(name = "cd_tipo_receita"),
        inverseJoinColumns = @JoinColumn(name = "cd_origem_debito"))
	private List<OrigemDebito> origemDebitos;

	@Expose
	@Enumerated(EnumType.STRING)
	@Column(name = "ds_natureza_tipo_receita")
	private Natureza natureza;

	@Expose
	@Enumerated(EnumType.STRING)
	@Column(name = "ds_situacao_tipo_receita")
	private Situacao situacao;

	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
