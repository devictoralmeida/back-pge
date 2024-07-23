package br.gov.ce.pge.gestao.constants;

public class MessageTermoCondicaoConstant {

    private MessageTermoCondicaoConstant() {}

    public static final int VERSAO_SECUNDARIA_INICIAL = 0;
    public static final int VERSAO_SECUNDARIA_MAXIMA = 9;
    public static final String ALTERACAO_NAO_PERMITIDA = "Alteração só é permitida na versão vigente!";
    public static final String SEM_HISTORICO = "O termo selecionado não possui histórico de edições.";
    public static final String NAO_ENCONTRADO = "Registro não encontrado.";
    public static final String PORTAL_DIVIDA_ATIVA = "Portal da Dívida Ativa";

    public static final int INCREMENTO_PAGINA_PAR = 1;

    public static final String REGEX_SEPARATOR = "\\.";
    public static final String REGEX_VERSAO_TERMO = "%d.%d";

}
