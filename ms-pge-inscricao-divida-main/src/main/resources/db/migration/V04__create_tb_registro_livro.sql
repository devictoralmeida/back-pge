CREATE TABLE "tb_registro_livro" (
	"ci_registro_livro" UUID NOT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
	"dt_criacao" TIMESTAMP NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,	
	"cd_livro_eletronico" UUID NULL DEFAULT NULL,
	"cd_inscricao" UUID NULL DEFAULT NULL,
	CONSTRAINT "pk_tbregistrolivro" PRIMARY KEY ("ci_registro_livro"),
	CONSTRAINT "fk_tbregistrolivro_tblivroeletronico" FOREIGN KEY ("cd_livro_eletronico") REFERENCES "tb_livro_eletronico" ("ci_livro_eletronico") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbregistrolivro_tbinscricao" FOREIGN KEY ("cd_inscricao") REFERENCES "tb_inscricao" ("ci_inscricao") ON UPDATE NO ACTION ON DELETE NO ACTION
);