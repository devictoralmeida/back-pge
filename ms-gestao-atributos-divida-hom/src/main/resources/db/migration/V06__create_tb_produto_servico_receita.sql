CREATE TABLE "tb_produto_servico_receita" (
	"cd_produto_servico" UUID NOT NULL,
	"cd_tipo_receita" UUID NOT NULL,
	CONSTRAINT "fk_tbprodutoservicoreceita_tbtiporeceita" FOREIGN KEY ("cd_tipo_receita") REFERENCES "tb_tipo_receita" ("ci_tipo_receita") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbprodutoservicoreceita_tbprodutoservico" FOREIGN KEY ("cd_produto_servico") REFERENCES "tb_produto_servico" ("ci_produto_servico") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_produto_servico_receita_aud" (
	"cd_auditoria" BIGINT NOT NULL,
	"cd_produto_servico" UUID NOT NULL,
	"cd_tipo_receita" UUID NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbprodutoservicoreceitaaud" PRIMARY KEY ("cd_auditoria", "cd_produto_servico", "cd_tipo_receita"),
	CONSTRAINT "fk_tbprodutoservicoreceitaaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);