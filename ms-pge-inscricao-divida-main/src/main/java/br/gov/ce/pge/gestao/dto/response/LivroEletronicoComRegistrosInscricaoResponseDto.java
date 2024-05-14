package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class LivroEletronicoComRegistrosInscricaoResponseDto implements Serializable {
  private static final long serialVersionUID = 1L;
  List<RegistroInscricaoResponseDto> registros;
  private UUID id;
  private String nome;
  private SituacaoLivro situacao;
  private LocalDateTime dataAbertura;
  private LocalDateTime dataFechamento;
}
