CREATE TABLE "tb_usuario" (
	"ci_usuario" UUID NOT NULL,
	"tp_usuario" VARCHAR(30) NOT NULL,
	"nr_cpf_usuario" VARCHAR(11) NOT NULL,
	"nm_usuario" VARCHAR(250) NOT NULL,
	"ds_orgao_usuario" VARCHAR(250) NOT NULL,
	"ds_area_usuario" VARCHAR(250) NULL DEFAULT NULL,
	"ds_email_usuario" VARCHAR(250) NOT NULL,
	"nm_usuario_rede" VARCHAR(250) NULL DEFAULT NULL,	
	"ds_situacao_usuario" VARCHAR(30) NOT NULL,
	"ds_senha_usuario" VARCHAR(250) NULL DEFAULT NULL,	
	"dt_ultimo_acesso_usario" TIMESTAMP NULL DEFAULT NULL,	
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbusuario" PRIMARY KEY ("ci_usuario")
);

CREATE TABLE "tb_usuario_aud" (
	"ci_usuario" UUID NOT NULL,
	"tp_usuario" VARCHAR(30) NULL DEFAULT NULL,
	"nr_cpf_usuario" VARCHAR(11) NULL DEFAULT NULL,
	"nm_usuario" VARCHAR(250) NULL DEFAULT NULL,
	"ds_orgao_usuario" VARCHAR(250) NULL DEFAULT NULL,
	"ds_area_usuario" VARCHAR(250) NULL DEFAULT NULL,
	"ds_email_usuario" VARCHAR(250) NULL DEFAULT NULL,
	"nm_usuario_rede" VARCHAR(250) NULL DEFAULT NULL,	
	"ds_situacao_usuario" VARCHAR(30) NULL DEFAULT NULL,
	"ds_senha_usuario" VARCHAR(250) NULL DEFAULT NULL,	
	"dt_ultimo_acesso_usario" TIMESTAMP NULL DEFAULT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbusuarioaud" PRIMARY KEY ("ci_usuario", "cd_auditoria"),
	CONSTRAINT "fk_tbusuarioaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_usuario_sistema" (
	"cd_usuario" UUID NOT NULL,
	"cd_sistema" UUID NOT NULL,
	CONSTRAINT "fk_tbusuariosistema_tbusuario" FOREIGN KEY ("cd_usuario") REFERENCES "tb_usuario" ("ci_usuario") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbusuariosistema_tbsistema" FOREIGN KEY ("cd_sistema") REFERENCES "tb_sistema" ("ci_sistema") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_usuario_sistema_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_usuario" UUID NOT NULL,
	"cd_sistema" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbusuariosistemaaud" PRIMARY KEY ("cd_auditoria", "cd_usuario", "cd_sistema"),
	CONSTRAINT "fk_tbusuariosistemaaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE "tb_usuario_perfil_acesso" (
	"cd_usuario" UUID NOT NULL,
	"cd_perfil_acesso" UUID NOT NULL,
	CONSTRAINT "fk_tbusuarioperfilacesso_tbusuario" FOREIGN KEY ("cd_usuario") REFERENCES "tb_usuario" ("ci_usuario") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbusuarioperfilacesso_tbperfilacesso" FOREIGN KEY ("cd_perfil_acesso") REFERENCES "tb_perfil_acesso" ("ci_perfil_acesso") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_usuario_perfil_acesso_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_usuario" UUID NOT NULL,
	"cd_perfil_acesso" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbusuarioperfilacessoaud" PRIMARY KEY ("cd_auditoria", "cd_usuario", "cd_perfil_acesso"),
	CONSTRAINT "fk_tbusuarioperfilacessoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);