package br.gov.ce.pge.gestao.constants;

public class SharedConstant {

    private SharedConstant() {}

    public static final long ITENS_POR_PAGINA = 10;

    public static final int INDICE_INICIAL = 0;

    public static final int UNICO_REGISTRO = 1;

    public static final int SUBTRACAO_INDICE = 1;

    public static final int TAMANHO_JANELA = 30;

    public static final int TAXA_FALHA = 80;

    public static final int NUMERO_MINIMO_CHAMADAS = 10;

    public static final int DURACAO_ABRIR_ESTADO = 30;

    public static final int NUMERO_PERMITIDOS_CHAMADAS = 5;

    public static final String PROPERTIES = "/application.properties";

    public static final String LIMIT = "limit";

    public static final String OFFSET = "offset";

    public static final String TIPO_MOVIMENTO = "tipoMovimento";

    public static final String ID = "id";

    public static final String APENAS_NUMEROS = "^[1-9]\\d*$";

    public static final String FORMATO_DE_HORA = "^(\\d+):([0-5]\\d)$";

    public static final String ZONE_ID = "America/Sao_Paulo";

    public static final String REGEX_VALIDA_HORA = "^(\\d+):([0-5]\\d)$";

    public static final int NUMERO_MAXIMO = 900000;

    public static final int NUMERO_MINIMO = 100000;

    public static final String REGEX_APENAS_LETRAS = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";

    public static final String REGEX_APENAS_NUMEROS = "^\\d+$";

    public static final String REGEX_APENAS_LETRAS_NUMEROS = "[\\p{L}0-9\\s-.,]+";

    public static final int SEGUNDO_INDICE = 1;

    public static final String DOMINIO_MINUSCULO = "gov.br";

    public static final String DOMINIO_MAIUSCULO = "GOV.BR";

    public static final String REGEX_VALIDA_SENHA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$";

}
