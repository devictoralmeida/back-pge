package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevedorResponseDto implements Serializable {

	private static final long serialVersionUID = -6283169233842304094L;
	
	private UUID id;

	private TipoPessoa tipoPessoa;

	private String documento;

	private String nomeRazaoSocial;

	private String cgf;

	private TipoContato tipoContato;

	private String contato;

	private String email;

	private String cep;

	private String logradouro;

	private String numero;

	private String bairro;

	private String complemento;

	private String distrito;

	private String municipio;

	private String uf;

}
