package br.gov.ce.pge.gestao.constants;

public class MessagePerfilAcessoConstant {

    private MessagePerfilAcessoConstant() {}

    public static final String NAO_ENCONTRADO = "Não existe esse Perfil de Acesso";

    public static final String DEPENDENCIA_USUARIO = "fk_tbusuarioperfilacesso_tbperfilacesso";

    public static final String MSG_DEPENDENCIA_USUARIO = "Não foi possível realizar a exclusão! O Perfil de Acesso está sendo utilizado para um ou mais usuários cadastrados.";

    public static final String SEM_HISTORICO = "O Perfil de acesso selecionado não possui histórico de edições.";

    public static final String REGISTRO_CADASTRADO = "O registro já foi cadastrado anteriormente!";

    public static final String PERMISSAO_NAO_RELACIONADA = " não faz parte dos sistemas selecionados";

}
