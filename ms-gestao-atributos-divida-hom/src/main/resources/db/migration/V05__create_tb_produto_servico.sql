CREATE TABLE "tb_produto_servico" (
	"ci_produto_servico" UUID NOT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	"cd_produto_servico" VARCHAR(5) NOT NULL,
	"ds_produto_servico" VARCHAR(300) NOT NULL,
	"ds_situacao_produto_servico" VARCHAR(100) NOT NULL,
	CONSTRAINT "pk_tbprodutoservico" PRIMARY KEY ("ci_produto_servico"),
	CONSTRAINT "uk_cdprodutoservico" UNIQUE("cd_produto_servico")
);


CREATE TABLE "tb_produto_servico_aud" (
	"ci_produto_servico" UUID NOT NULL,
	"cd_auditoria" BIGINT NOT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	"cd_produto_servico" VARCHAR(5) NULL DEFAULT NULL,
	"ds_produto_servico" VARCHAR(300) NULL DEFAULT NULL,
	"ds_situacao_produto_servico" VARCHAR(100) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbprodutoservicoaud" PRIMARY KEY ("ci_produto_servico", "cd_auditoria"),
	CONSTRAINT "fk_tbprodutoservicoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);