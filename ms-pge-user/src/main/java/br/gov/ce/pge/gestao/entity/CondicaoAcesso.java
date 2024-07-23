package br.gov.ce.pge.gestao.entity;

import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;

@Getter
@Setter
@Entity
@Table(name = "tb_condicao_acesso")
@Audited
@AuditTable("tb_condicao_acesso_aud")
public class CondicaoAcesso extends AuditoriaUser {

	private static final long serialVersionUID = -6908592951304703366L;

	@Id
	@GeneratedValue
	@Column(name = "ci_condicao_acesso")
	private UUID id;

	@Column(name = "nr_bloqueio_automatico")
	private int bloqueioAutomatico;

	@Column(name = "nr_alteracao_senha")
	private int alteracaoSenha;

	@Column(name = "nr_encerramento_sessao")
	private String encerramentoSessao;

	@Column(name = "nr_tentativas_invalida")
	private int tentativasInvalidas;

	@Column(name = "nr_senhas_cadastrada")
	private int senhasCadastradas;

	@NotAudited
	@Column(name = "nr_intervalo_bloqueio")
	private int intervaloBloqueio;

	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

}
