CREATE TABLE "tb_codigo_verificacao" (
	"ci_codigo_verificacao" UUID NOT NULL,
	"ds_codigo" VARCHAR(6) NULL DEFAULT NULL,
	"dt_expiracao" TIMESTAMP NULL DEFAULT NULL,
	CONSTRAINT "pk_tbcodigoverificacao" PRIMARY KEY ("ci_codigo_verificacao")
);

CREATE TABLE "tb_recuperacao_senha" (
	"ci_recuperacao_senha" UUID NOT NULL,
	"ds_email" VARCHAR NULL DEFAULT NULL,
	"dt_hora_requisicao" TIMESTAMP NULL DEFAULT NULL
);

ALTER TABLE tb_usuario
ADD COLUMN cd_codigo_verificacao UUID;

ALTER TABLE tb_usuario
ADD CONSTRAINT fk_tbusuario_tbcodigoverificacao
FOREIGN KEY (cd_codigo_verificacao)
REFERENCES tb_codigo_verificacao(ci_codigo_verificacao) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE tb_condicao_acesso
ADD COLUMN cd_usuario UUID;

ALTER TABLE tb_condicao_acesso
ADD CONSTRAINT fk_tbcondicaoacesso_tbusuario
FOREIGN KEY (cd_usuario)
REFERENCES tb_usuario(ci_usuario) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE tb_usuario
ADD COLUMN ds_ultimas_senhas VARCHAR;

ALTER TABLE tb_condicao_acesso_aud
ADD COLUMN cd_usuario UUID;
