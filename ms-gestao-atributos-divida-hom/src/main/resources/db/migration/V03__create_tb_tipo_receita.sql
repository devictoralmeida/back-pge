CREATE TABLE "tb_tipo_receita" (
	"ci_tipo_receita" UUID NOT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	"cd_tipo_receita" VARCHAR(4) NOT NULL,
	"ds_tipo_receita" VARCHAR(300) NOT NULL,
	"ds_natureza_tipo_receita" VARCHAR(100) NOT NULL,
	"ds_situacao_tipo_receita" VARCHAR(100) NOT NULL,
	CONSTRAINT "pk_tbtiporeceita" PRIMARY KEY ("ci_tipo_receita"),
	CONSTRAINT "uk_cdtiporeceita" UNIQUE("cd_tipo_receita")
);

CREATE TABLE "tb_tipo_receita_aud" (
	"ci_tipo_receita" UUID NOT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	"cd_tipo_receita" VARCHAR(4) NULL DEFAULT NULL,
	"ds_tipo_receita" VARCHAR(300) NULL DEFAULT NULL,
	"ds_natureza_tipo_receita" VARCHAR(100) NULL DEFAULT NULL,
	"ds_situacao_tipo_receita" VARCHAR(100) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbtiporeceitaaud" PRIMARY KEY ("ci_tipo_receita", "cd_auditoria"),
	CONSTRAINT "fk_tbtiporeceitaaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);