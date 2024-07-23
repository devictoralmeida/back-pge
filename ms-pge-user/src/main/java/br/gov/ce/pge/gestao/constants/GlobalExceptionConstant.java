package br.gov.ce.pge.gestao.constants;

public class GlobalExceptionConstant {

    private GlobalExceptionConstant() {}

    public static final String MENSAGEM_PERMISSAO_UTILIZADO = "Não foi possível realizar a exclusão! A Permissão está sendo utilizada para um Módulo cadastrado.";

    public static final String MENSAGEM_MODULO_UTILIZADO = "Não foi possível realizar a exclusão! O Módulo está sendo utilizado para um Sistema cadastrado.";

    public static final String MENSAGEM_SISTEMA_UTILIZADO = "Não foi possível realizar a exclusão! O Sistema está sendo utilizado para um Perfil de Acesso cadastrado.";

    public static final String REGISTRO = "registro";

    public static final String CODIGO = "codigo";

    public static final String EMAIL = "email";

    public static final String CPF = "cpf";

    public static final String MENSAGEM_CPF_DUPLICADO = "Valor duplicado encontrado para o campo: CPF";

    public static final String MENSAGEM_EMAIL_DUPLICADO = "Valor duplicado encontrado para o campo: EMAIL";

    public static final String MENSAGEM_CODIGO_DUPLICADO = "Valor duplicado encontrado para o campo: CODIGO";

    public static final String MENSAGEM_VRIFICAR_CAMPOS = "Favor verifique todos os campos com validação";

    public static final String MENSAGEM_VALOR_INVALIDO = "Valor inválido para o tipo %s: %s. Valores aceitos são: %s";

    public static final String MENSAGEM_EMAIL_EXISTENTE = "Email já existe!";

    public static final String MENSAGEM_CPF_EXISTENTE = "CPF já existe!";

    public static final String MENSAGEM_USUARIO_REDE_EXISTENTE = "Usuário de rede já existe!";

}
