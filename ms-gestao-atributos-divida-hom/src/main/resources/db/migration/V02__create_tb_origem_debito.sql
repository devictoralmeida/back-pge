CREATE TABLE "tb_origem_debito" (
	"ci_origem_debito" UUID NOT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	"ds_origem_debito" VARCHAR(300) NOT NULL,
	"nm_origem_debito" VARCHAR(255) NOT NULL,
	"ds_situacao_origem_debito" VARCHAR(100) NOT NULL,
	CONSTRAINT "pk_tborigemdebito" PRIMARY KEY ("ci_origem_debito")
);

CREATE TABLE "tb_origem_debito_aud" (
	"ci_origem_debito" UUID NOT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	"ds_origem_debito" VARCHAR(300) NULL DEFAULT NULL,
	"nm_origem_debito" VARCHAR(255) NULL DEFAULT NULL,
	"ds_situacao_origem_debito" VARCHAR(100) NULL DEFAULT NULL,
	CONSTRAINT "pk_tborigemdebitoaud" PRIMARY KEY ("ci_origem_debito", "cd_auditoria"),
	CONSTRAINT "fk_tborigemdebitoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);