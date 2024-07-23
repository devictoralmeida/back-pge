CREATE TABLE "tb_livro_eletronico" (
	"ci_livro_eletronico" UUID NOT NULL,
	"nm_livro_eletronico" VARCHAR(4) NOT NULL,
	"ds_situacao_livro" VARCHAR(10) NOT NULL,
	"dt_abertura_livro" TIMESTAMP NOT NULL,
    "dt_fechamento_livro" TIMESTAMP NULL default NULL,
	"nm_usuario_responsavel" VARCHAR(30) NOT NULL,
	CONSTRAINT "pk_tblivroeletronico" PRIMARY KEY ("ci_livro_eletronico")
);
