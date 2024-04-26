package br.gov.ce.pge.gestao.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.Expose;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_origem_debito")
@Audited
@AuditTable("tb_origem_debito_aud")
public class OrigemDebito extends AuditoriaUser implements Serializable {
	
	private static final long serialVersionUID = 6562544404491306202L;

	@Expose
	@Id
	@GeneratedValue
	@Column(name = "ci_origem_debito")
	private UUID id;

	@Expose
	@Column(name = "nm_origem_debito")
	private String nome;

	@Expose
	@Column(name = "ds_origem_debito",length = 300)
	private String descricao;

	@Expose
	@Enumerated(EnumType.STRING)
	@Column(name = "ds_situacao_origem_debito")
	private Situacao situacao;

	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

}
