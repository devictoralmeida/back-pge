package br.gov.ce.pge.gestao.dto.request;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.MessagesSucessor;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SucessorRequestDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -8937227486050615581L;

	@NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_PESSOA)
	private UUID idTipoPessoa;

	@NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DOCUMENTO)
	@Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_DOCUMENTO_APENAS_NUMEROS)
	@Size(min = 11, max = 14, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_DOCUMENTO_INVALIDO)
	private String documento;

	@NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_NOME_RAZAO_SOCIAL)
	@Size(max = 250, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NOME_RAZAO_SOCIAL_INVALIDO)
	private String nomeRazaoSocial;

	@Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CGF_APENAS_NUMEROS)
	@Size(min = 9, max = 9, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CGF_INVALIDO)
	private String cgf;

	@NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_ENDERECO)
	private @Valid EnderecoRequestDto endereco;
	
	@NotNull(message = MessagesSucessor.MENSAGEM_INSCRICAO_OBRIGATORIO)
	@NotEmpty(message = MessagesSucessor.MENSAGEM_INSCRICAO_OBRIGATORIO)
	private List<UUID> idsDivida;

	private List<@Valid ContatoRequestDto> contatos;
	
	private Boolean declaracaoAusenciaContato;
	
	private Boolean liberaDividaComSucessor = false;
	private Boolean liberaDevedoresDistinto = false;
	private Boolean liberaDividaFaseFinalistica = false;

	public void validarContatos() {
		if (contatos != null && !contatos.isEmpty()) {
			contatos.stream().collect(Collectors.groupingBy(ContatoRequestDto::getIdTipoContato))
					.forEach((idTipoContato, contatosDoTipo) -> {
						long expectedSize = idTipoContato.equals(IdsConstants.ID_TIPO_CONTATO_EMAIL)
								? contatosDoTipo.stream().map(ContatoRequestDto::getValorContato).distinct().count()
								: contatosDoTipo.stream()
										.map(contato -> Arrays.asList(contato.getIdTipoContato(),
												contato.getNumeroDdiContato(), contato.getValorContato()))
										.distinct().count();

						if (expectedSize < contatosDoTipo.size()) {
							throw new NegocioException(MessageCommonsConstants.MSG_ERRO_CONTATO_CADASTRADO);
						}
					});
		}
	}
}
