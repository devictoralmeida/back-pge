package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_livro_eletronico")
@Getter
@Setter
public class LivroEletronico implements Serializable {
  @Serial
  private static final long serialVersionUID = -7142266468622334374L;

  @Id
  @GeneratedValue
  @Column(name = "ci_livro_eletronico")
  private UUID id;

  @Column(name = "nm_livro_eletronico")
  private String nome;

  @Column(name = "ds_situacao_livro")
  @Enumerated(EnumType.STRING)
  private SituacaoLivro situacao;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @Column(name = "dt_abertura_livro")
  private LocalDateTime dataAbertura;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dt_fechamento_livro")
  private LocalDateTime dataFechamento;

  @Column(name = "nm_usuario_responsavel", length = 30, nullable = false)
  private String usuarioResponsavel;

  @OneToMany(mappedBy = "livro")
  private List<RegistroLivro> registros;

  public String toStringMapper() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    return objectMapper.writeValueAsString(this);
  }
}
