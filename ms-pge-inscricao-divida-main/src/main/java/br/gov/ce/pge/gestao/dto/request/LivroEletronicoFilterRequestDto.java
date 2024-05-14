package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class LivroEletronicoFilterRequestDto implements Serializable {
  private static final long serialVersionUID = 1L;

  @Size(max = 4, min = 4, message = "O campo nome deve conter {max} caracteres")
  private String nome;

  @Size(max = 9, min = 9, message = "O campo cgf deve conter {max} caracteres")
  private String cgf;

  @Size(max = 11, min = 11, message = "O campo cpf deve conter {max} caracteres")
  private String cpf;

  @Size(max = 14, min = 14, message = "O campo cnpj deve conter {max} caracteres")
  private String cnpj;

  private UUID numeroInscricao;

  @Size(max = 250, message = "O campo nome ou razão social deve conter no máximo {max} caracteres")
  private String nomeRazaoSocial;

  private List<String> livros;
  private SituacaoLivro situacao;

  @PastOrPresent(message = "A data de abertura não pode ser futura")
  private LocalDateTime dataAbertura;
  private LocalDateTime dataFechamento;

  public Map<String, Object> filters() {
    Map<String, Object> filter = new HashMap<>();
    filter.put("nome", this.nome == null ? null : this.nome);
    filter.put("cgf", this.cgf == null ? null : this.cgf);
    filter.put("cpf", this.cpf == null ? null : this.cpf);
    filter.put("cnpj", this.cnpj == null ? null : this.cnpj);
    filter.put("numeroInscricao", this.numeroInscricao == null ? null : this.numeroInscricao);
    filter.put("nomeRazaoSocial", this.nomeRazaoSocial == null || this.nomeRazaoSocial.isEmpty() ? null : "%" + this.nomeRazaoSocial.toUpperCase() + "%");
    filter.put("livros", this.livros == null || this.livros.isEmpty() ? null : this.livros);
    filter.put("situacao", this.situacao == null ? null : this.situacao);
    filter.put("dataAbertura", this.dataAbertura == null ? null : this.dataAbertura);
    filter.put("dataFechamento", this.dataFechamento == null ? null : this.dataFechamento);
    return filter;
  }
}
