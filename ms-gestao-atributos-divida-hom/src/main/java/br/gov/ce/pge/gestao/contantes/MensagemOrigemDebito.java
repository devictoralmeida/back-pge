package br.gov.ce.pge.gestao.contantes;

public class MensagemOrigemDebito {
	
	private MensagemOrigemDebito() {}
	
	public static final String MENSAGEM_ORIGEM_CADASTRADA = "O registro já foi cadastrado anteriormente!";
	
	public static final String MENSAGEM_ORIGEM_NAO_ENCONTRADA = "Origem débito não encontrada.";
	
	public static final String MENSAGEM_ORIGEM_SEM_HISTORICO_EDICAO = "Origem de Débito selecionada não possui histórico de edições.";
	
	public static final String MENSAGEM_ORIGEM_ERRO_DELETE = "Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.";

}
