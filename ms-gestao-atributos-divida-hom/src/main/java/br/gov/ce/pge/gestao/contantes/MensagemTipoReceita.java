package br.gov.ce.pge.gestao.contantes;

public class MensagemTipoReceita {
	
	private MensagemTipoReceita() {}
	
	public static final String MENSAGEM_TIPORECEITA_CADASTRADA = "O registro já foi cadastrado anteriormente!";
	
	public static final String MENSAGEM_TIPORECEITA_NAO_ENCONTRADA = "Tipo de Receita não encontrada.";
	
	public static final String MENSAGEM_TIPORECEITA_SEM_HISTORICO_EDICAO = "Tipo Receita selecionado não possui histórico de edições.";
	
	public static final String MENSAGEM_TIPORECEITA_ERRO_DELETE = "Não foi possível realizar a exclusão! O Tipo receita está sendo utilizado para um ou mais Produto/Serviço cadastrado.";

}
