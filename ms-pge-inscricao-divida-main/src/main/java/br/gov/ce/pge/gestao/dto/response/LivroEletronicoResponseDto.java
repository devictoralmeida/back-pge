package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroEletronicoResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID id;

  private String nome;

  private SituacaoLivro situacao;

  private LocalDateTime dataAbertura;

  private LocalDateTime dataFechamento;

  private Integer paginas;

  private Integer totalLinhasUltimaPagina;
}
