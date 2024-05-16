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

  private String numeroInscricao;

  @Size(max = 250, message = "O campo nome ou razão social deve conter no máximo {max} caracteres")
  private String nomeRazaoSocial;

  private List<String> livros;
  private SituacaoLivro situacao;

  @PastOrPresent(message = "A data de abertura não pode ser futura")
  private LocalDateTime dataAbertura;
  private LocalDateTime dataFechamento;

  public Map<String, Object> filters() {
    Map<String, Object> filter = new HashMap<>();
    filter.put("nome", nome == null ? null : nome);
    filter.put("cgf", cgf == null ? null : cgf);
    filter.put("cpf", cpf == null ? null : cpf);
    filter.put("cnpj", cnpj == null ? null : cnpj);
    filter.put("numeroInscricao", numeroInscricao == null ? null : numeroInscricao);
    filter.put("nomeRazaoSocial", nomeRazaoSocial == null || nomeRazaoSocial.isEmpty() ? null : "%" + nomeRazaoSocial.toUpperCase() + "%");
    filter.put("livros", livros == null || livros.isEmpty() ? null : livros);
    filter.put("situacao", situacao == null ? null : situacao);
    filter.put("dataAbertura", dataAbertura == null ? null : dataAbertura);
    filter.put("dataFechamento", dataFechamento == null ? null : dataFechamento);
    return filter;
  }
}
