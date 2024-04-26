package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorresponsavelResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;

	private TipoPessoa tipoPessoa;

	private String documento;

	private String nomeRazaoSocial;

	private String cgf;

	private TipoContato tipoContato;

	private String contato;

	private String email;
	
	private String qualificacao;

	private String cep;

	private String logradouro;

	private String numero;

	private String bairro;

	private String complemento;

	private String distrito;

	private String municipio;

	private String uf;

}
