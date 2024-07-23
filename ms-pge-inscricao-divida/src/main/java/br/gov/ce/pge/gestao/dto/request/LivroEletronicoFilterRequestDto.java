package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class LivroEletronicoFilterRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 4, min = 4, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NOME_LIVRO_INVALIDO)
    private String nome;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CGF_APENAS_NUMEROS)
    @Size(max = 9, min = 9, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CGF_INVALIDO)
    private String cgf;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CPF_APENAS_NUMEROS)
    @Size(max = 11, min = 11, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CPF_INVALIDO)
    private String cpf;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CNPJ_APENAS_NUMEROS)
    @Size(min = 8, max = 14, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CNPJ_INVALIDO)
    private String cnpj;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_NUMERO_INSCRICAO_APENAS_NUMEROS)
    @Size(max = 13, min = 13, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_INSCRICAO_INVALIDO)
    private String numeroInscricao;

    @Size(max = 250, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NOME_RAZAO_SOCIAL_INVALIDO)
    private String nomeRazaoSocial;

    private List<String> livros;

    private SituacaoLivro situacao;

    @PastOrPresent(message = "A data de abertura não pode ser futura")
    private LocalDate dataAbertura;

    private LocalDate dataFechamento;

    public Map<String, Object> filters() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("nome", nome == null ? null : "%" + nome + "%");
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
