package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class LivroEletronicoFilterResponseDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String nome;
  private SituacaoLivro situacao;
  private LocalDateTime dataAbertura;
  private LocalDateTime dataFechamento;
  private int paginas;
}
