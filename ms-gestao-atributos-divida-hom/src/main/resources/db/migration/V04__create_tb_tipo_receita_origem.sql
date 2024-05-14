CREATE TABLE "tb_tipo_receita_origem" (
	"cd_tipo_receita" UUID NOT NULL,
	"cd_origem_debito" UUID NOT NULL,
	CONSTRAINT "fk_tbtiporeceitaorigem_tborigemdebito" FOREIGN KEY ("cd_origem_debito") REFERENCES "tb_origem_debito" ("ci_origem_debito") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbtiporeceitaorigem_tbtiporeceita" FOREIGN KEY ("cd_tipo_receita") REFERENCES "tb_tipo_receita" ("ci_tipo_receita") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_tipo_receita_origem_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_tipo_receita" UUID NOT NULL,
	"cd_origem_debito" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbtiporeceitaorigemaud" PRIMARY KEY ("cd_auditoria", "cd_tipo_receita", "cd_origem_debito"),
	CONSTRAINT "fk_tbtiporeceitaorigemaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);