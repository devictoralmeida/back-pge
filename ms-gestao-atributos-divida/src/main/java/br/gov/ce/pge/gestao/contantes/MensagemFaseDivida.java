package br.gov.ce.pge.gestao.contantes;

public class MensagemFaseDivida {


	private MensagemFaseDivida() {}

	public static final String MENSAGEM_FASE_TIPO_COBRANCA_INVALIDO = "Não é possível informar um tipo de cobrança para uma fase que não exige cobrança.";

	public static final String MENSAGEM_FASE_CADASTRADA = "O nome da fase é igual ao nome da fase %s já cadastrada.";

	public static final String MENSAGEM_FASE_SEMELHANTE = "O nome da fase é semelhante ao(s) registro(s) abaixo já cadastrado(s). Deseja prosseguir? %s";

	public static final String MENSAGEM_FASE_NAO_ENCONTRADA = "Fase da Dívida não encontrada.";

	public static final String MENSAGEM_FASE_FLUXO_IGUAL_INVALIDA = "Não é permitido alterar a fase para a mesma fase na matriz.";

}
