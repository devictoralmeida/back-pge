CREATE TABLE "tb_termo_condicao" (
	"ci_termo_condicao" UUID NOT NULL,
	"ds_versao" VARCHAR(10) NOT NULL,
	"ds_conteudo" VARCHAR(7000) NOT NULL,
	"cd_sistema" UUID NOT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbtermocondicao" PRIMARY KEY ("ci_termo_condicao"),
	CONSTRAINT "fk_tbtermocondicao_tbsistema" FOREIGN KEY ("cd_sistema") REFERENCES "tb_sistema" ("ci_sistema") ON UPDATE NO ACTION ON DELETE NO ACTION
);