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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Table(name = "tb_produto_servico")
@AuditTable("tb_produto_servico_aud")
public class ProdutoServico extends AuditoriaUser implements Serializable {
	
	private static final long serialVersionUID = -3770169452237675929L;
	
	@Id
	@GeneratedValue
	@Column(name = "ci_produto_servico")
	private UUID id;
	
	@Column(name = "cd_produto_servico", unique = true)
	private String codigo;
	
	@Column(name = "ds_produto_servico")
	private String descricao;
	
	@ManyToMany
	@JoinTable(name = "tb_produto_servico_receita",
		joinColumns = @JoinColumn(name = "cd_produto_servico"),
        inverseJoinColumns = @JoinColumn(name = "cd_tipo_receita"))
	private List<TipoReceita> tipoReceitas;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ds_situacao_produto_servico")
	private Situacao situacao;

	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
