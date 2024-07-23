package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PessoaResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 4960653342604043073L;

  private UUID id;

  private String documento;

  private String nomeRazaoSocial;

  private String cgf;

  private UUID idTipoPessoa;

  private EnderecoResponseDto endereco;

  private List<ContatoResponseDto> contatos;
}
