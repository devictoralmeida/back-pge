CREATE TABLE "tb_permissao" (
	"ci_permissao" UUID NOT NULL,
	"nm_permissao" VARCHAR(255) NULL DEFAULT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbpermissao" PRIMARY KEY ("ci_permissao")
);


CREATE TABLE "tb_permissao_aud" (
	"ci_permissao" UUID NOT NULL,
	"nm_permissao" VARCHAR(255) NULL DEFAULT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbpermissaoaud" PRIMARY KEY ("ci_permissao", "cd_auditoria"),
	CONSTRAINT "fk_tbpermissaoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE "tb_modulo" (
	"ci_modulo" UUID NOT NULL,
	"nm_modulo" VARCHAR(255) NULL DEFAULT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbmodulo" PRIMARY KEY ("ci_modulo")
);


CREATE TABLE "tb_modulo_aud" (
	"ci_modulo" UUID NOT NULL,
	"nm_modulo" VARCHAR(255) NULL DEFAULT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbmoduloaud" PRIMARY KEY ("ci_modulo", "cd_auditoria"),
	CONSTRAINT "fk_tbmoduloaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_modulo_permissao" (
	"cd_modulo" UUID NOT NULL,
	"cd_permissao" UUID NOT NULL,
	CONSTRAINT "fk_tbmodulopermissao_tbpermissao" FOREIGN KEY ("cd_permissao") REFERENCES "tb_permissao" ("ci_permissao") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbmodulopermissao_tbmodulo" FOREIGN KEY ("cd_modulo") REFERENCES "tb_modulo" ("ci_modulo") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_modulo_permissao_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_modulo" UUID NOT NULL,
	"cd_permissao" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbmodulopermissaoaud" PRIMARY KEY ("cd_auditoria", "cd_modulo", "cd_permissao"),
	CONSTRAINT "fk_tbmodulopermissaoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_sistema" (
	"ci_sistema" UUID NOT NULL,
	"nm_sistema" VARCHAR(255) NULL DEFAULT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbsistema" PRIMARY KEY ("ci_sistema")
);


CREATE TABLE "tb_sistema_aud" (
	"ci_sistema" UUID NOT NULL,
	"nm_sistema" VARCHAR(255) NULL DEFAULT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbsistemaaud" PRIMARY KEY ("ci_sistema", "cd_auditoria"),
	CONSTRAINT "fk_tbsistemaaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE "tb_sistema_modulo" (
	"cd_sistema" UUID NOT NULL,
	"cd_modulo" UUID NOT NULL,
	CONSTRAINT "fk_tbsistemamodulo_tbsistema" FOREIGN KEY ("cd_sistema") REFERENCES "tb_sistema" ("ci_sistema") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbsistemamodulo_tbmodulo" FOREIGN KEY ("cd_modulo") REFERENCES "tb_modulo" ("ci_modulo") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_sistema_modulo_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_sistema" UUID NOT NULL,
	"cd_modulo" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbsistemamoduloaud" PRIMARY KEY ("cd_auditoria", "cd_sistema", "cd_modulo"),
	CONSTRAINT "fk_tbsistemamoduloaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE "tb_perfil_acesso" (
	"ci_perfil_acesso" UUID NOT NULL,
	"nm_perfil_acesso" VARCHAR(150) NULL DEFAULT NULL,
	"ds_situacao_perfil_acesso" VARCHAR(30) NULL DEFAULT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbperfilacesso" PRIMARY KEY ("ci_perfil_acesso")
);


CREATE TABLE "tb_perfil_acesso_aud" (
	"ci_perfil_acesso" UUID NOT NULL,
	"nm_perfil_acesso" VARCHAR(150) NULL DEFAULT NULL,
	"ds_situacao_perfil_acesso" VARCHAR(30) NULL DEFAULT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbperfilacessoaud" PRIMARY KEY ("ci_perfil_acesso", "cd_auditoria"),
	CONSTRAINT "fk_tbperfilacessoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_perfil_acesso_sistema" (
	"cd_perfil_acesso" UUID NOT NULL,
	"cd_sistema" UUID NOT NULL,
	CONSTRAINT "fk_tbperfilacessosistema_tbperfilacesso" FOREIGN KEY ("cd_perfil_acesso") REFERENCES "tb_perfil_acesso" ("ci_perfil_acesso") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbperfilacessosistema_tbsistema" FOREIGN KEY ("cd_sistema") REFERENCES "tb_sistema" ("ci_sistema") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_perfil_acesso_sistema_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_perfil_acesso" UUID NOT NULL,
	"cd_sistema" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbperfilacessosistemaaud" PRIMARY KEY ("cd_auditoria", "cd_perfil_acesso", "cd_sistema"),
	CONSTRAINT "fk_tbperfilacessosistemaaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE "tb_perfil_permissao" (
	"cd_perfil_acesso" UUID NOT NULL,
	"cd_permissao" UUID NOT NULL,
	CONSTRAINT "fk_tbperfilpermissao_tbpermissao" FOREIGN KEY ("cd_permissao") REFERENCES "tb_permissao" ("ci_permissao") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbperfilpermissao_tbperfilacesso" FOREIGN KEY ("cd_perfil_acesso") REFERENCES "tb_perfil_acesso" ("ci_perfil_acesso") ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE "tb_perfil_permissao_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_perfil_acesso" UUID NOT NULL,
	"cd_permissao" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_perfilpermissaoaud" PRIMARY KEY ("cd_auditoria", "cd_perfil_acesso", "cd_permissao"),
	CONSTRAINT "fk_perfilpermissaoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);
