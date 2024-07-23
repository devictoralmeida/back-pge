
package br.gov.ce.pge.gestao.constants;

public class SharedConstant {


  private SharedConstant() {
  }

    public static final int REGISTROS_POR_PAGINA = 50;

    public static final int INCREMENTO = 1;

    public static final String NUMERO_INSCRICAO_INICIAL = "00000001";

    public static final String NUMERO_LIMITE = "99999999";

    public static final int SOMA_INICIO = 0;

    public static final int PESO_INICIAL = 2;

    public static final int LIMITE_PESO = 9;

    public static final int NUMERO_MODULO = 11;

    public static final int INICIO_INDEX = 0;

    public static final int LIMITE_INDEX = 12;

    public static final char LIMITE_DIGITO_NUMERO = '9';

    public static final String INICIO_DIGITO_NUMERO = "0";

    public static final int STATUS_BAD_REQUEST = 400;

    public static final int STATUS_NOT_FOUND = 404;

    public static final String REGEX_APENAS_NUMEROS = "^\\d+$";

    public static final String REGEX_APENAS_NUMEROS_POSITIVOS = "^(?!0+$)\\d+$";

    public static final String REGEX_FORMATO_DATA_MM_YYYY = "^(0[1-9]|1[0-2])/\\d{4}$";

    public static final String REGEX_FORMATO_PLACA_VEICULO = "^[A-Za-z]{3}\\d{1}[A-Za-z0-9]{1}\\d{2,3}$";

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  public static final int TAMANHO_MAXIMO_EMAIL = 250;

    public static final int QUANTIDADE_MINIMA_DEVEDOR_TIPO_SOLIDARIO = 2;

    public static final int TAMANHO_MINIMO_CONTATO_NUMERICO = 10;

    public static final int TAMANHO_MAXIMO_CONTATO_NUMERICO = 11;

    public static final int TAMANHO_MAXIMO_DDI_CONTATO = 4;

    public static final String REFERENCIA_PRIMEIRO_DIA_MES = "01";

    public static final int QUANTIDADE_MAXIMA_DEVEDOR_TIPO_PRINCIPAL = 1;

    public static final int TAMANHO_MINIMO_CNPJ_INCOMPLETO = 8;

    public static final int TAMANHO_MAXIMO_CNPJ = 14;

    public static final int TAMANHO_KB = 1024;

    public static final int TAMANHO_MAXIMO_UPLOAD_ARQUIVO = 500;

    public static final int END_INDEX_MESSAGE = -1;

    public static final String BUCKET_DIVIDA = "bucket_inscricao_divida";

    public static final String NOME_QUITADO = "Quitados";

    public static final String NOME_EXTINTO = "Extintos";

    public static final String REGEX_NAO_TRIBUTARIA = "^N\\d{4}$";

    public static final String REGEX_TRIBUTARIA = "^\\d{1,5}$";

    public static final String DIVIDAS = "dividas";

    public static final String FASE_INICIAL = "Fase Inicial";

    public static final String TOTAL_REGISTROS = "totalRegistros";

    public static final String REGEX_DATA_DIA_MES_ANO = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

    public static final String REGEX_NUMERICO = "^-?\\d+(\\.\\d+)?$";

    public static final String CAMPO_CONSULTA_GENERICA = "consultaGenerica";

    public static final String HORARIO_INICIA_PROCESSO_ABERTURA_LIVRO = "0 0 0 1 1 ?";

    public static final String HORARIO_INICIA_PROCESSO_FECHAMENTO_LIVRO = "0 59 23 31 12 ?";

    public static final String REGEX_HORARIO = "^(\\d+):([0-5]\\d)$";

    public static final String REGEX_APENAS_LETRAS = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";

    public static final String CONTEM_APENAS_LETRAS_NUMEROS = "[\\p{L}0-9\\s-.,]+";

    public static final String REGEX_PLACA = "^[A-Z]{3}\\d[A-Z0-9]\\d{2}|[A-Z]{3}\\d{4}$";

    public static final int TAMANHO_NOME_PROVIDENCIA = 250;

    public static final String NOME_USUARIO = "nomeUsuario";
}
