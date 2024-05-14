package br.gov.ce.pge.gestao.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "tb_usuario")
@Audited
@AuditTable("tb_usuario_aud")
public class Usuario extends AuditoriaUser {

	private static final long serialVersionUID = -7142266468622334374L;

	@Id
	@GeneratedValue
	@Column(name = "ci_usuario")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_usuario", nullable = false)
	private TipoUsuario tipoUsuario;

	@Column(name = "nr_cpf_usuario", length = 11, nullable = false)
	private String cpf;

	@Column(name = "nm_usuario", length = 250, nullable = false)
	private String nome;

	@Column(name = "ds_orgao_usuario", nullable = false)
	private String orgao;

	@Column(name = "ds_area_usuario", length = 250)
	private String area;

	@Column(name = "ds_email_usuario", length = 250, nullable = false)
	private String email;

	@Column(name = "nm_usuario_rede", length = 250)
	private String usuarioRede;

	@ManyToMany
	@JoinTable(name = "tb_usuario_sistema",
			joinColumns = @JoinColumn(name = "cd_usuario"),
			inverseJoinColumns = @JoinColumn(name = "cd_sistema"))
	private List<Sistema> sistemas;

	@ManyToMany
	@JoinTable(name = "tb_usuario_perfil_acesso",
			joinColumns = @JoinColumn(name = "cd_usuario"),
			inverseJoinColumns = @JoinColumn(name = "cd_perfil_acesso"))
	private List<PerfilAcesso> perfisAcessos;

	@Column(name = "ds_situacao_usuario", nullable = false)
	@Enumerated(EnumType.STRING)
	private SituacaoUsuario situacao;

	@Column(name = "ds_senha_usuario")
	private String senha;

	@ManyToMany
	@JoinTable(name = "tb_usuario_termo",
			joinColumns = @JoinColumn(name = "cd_usuario"),
			inverseJoinColumns = @JoinColumn(name = "cd_termo_condicao"))
	@NotAudited
	@JsonIgnore
	private List<TermoCondicao> termos;

	@OneToOne
	@NotAudited
	@JoinColumn(name = "cd_codigo_verificacao")
	private CodigoVerificacao codigoVerificacao;

	@NotAudited
	@Column(name = "ds_ultimas_senhas")
	private String ultimasSenhas;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NotAudited
	@Column(name = "dt_aceite_termo_portal_divida")
	private LocalDateTime dataAceiteTermoPortalDivida;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NotAudited
	@Column(name = "dt_aceite_termo_portal_origem")
	private LocalDateTime dataAceiteTermoPortalOrigens;

	@NotAudited
	@Column(name = "ds_token_acesso_unico")
	private UUID tokenAcessoUnico;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NotAudited
	@Column(name = "dt_hora_bloqueio_usuario")
	private LocalDateTime horaBloqueio;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NotAudited
	@Column(name = "dt_ultima_alteracao_senha")
	private LocalDateTime dataUltimaAlteracaoSenha;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NotAudited
	@Column(name = "dt_ultimo_acesso_usuario")
	private LocalDateTime dataUltimoAcesso;

	public String toStringMapper() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		return objectMapper.writeValueAsString(this);
	}

}
