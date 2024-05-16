CREATE TABLE "tb_condicao_acesso" (
	"ci_condicao_acesso" UUID NOT NULL,
	"nr_bloqueio_automatico" INT NOT NULL,
	"nr_alteracao_senha" INT NOT NULL,
	"nr_encerramento_sessao" VARCHAR(255) NOT NULL,
	"nr_tentativas_invalida" INT NOT NULL,
	"nr_senhas_cadastrada" INT NOT NULL,
	"dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbcondicaoacesso" PRIMARY KEY ("ci_condicao_acesso")
);

CREATE TABLE "tb_condicao_acesso_aud" (
	"ci_condicao_acesso" UUID NULL,
	"nr_bloqueio_automatico" INT NULL,
    "nr_alteracao_senha" INT NULL,
    "nr_encerramento_sessao" VARCHAR(255) NULL,
    "nr_tentativas_invalida" INT NULL,
    "nr_senhas_cadastrada" INT NULL,
    "cd_auditoria" BIGINT NULL,
	"tp_movimento" SMALLINT NULL DEFAULT NULL,
	CONSTRAINT "pk_tbcondicaoacessoaud" PRIMARY KEY ("ci_condicao_acesso", "cd_auditoria"),
	CONSTRAINT "fk_tbcondicaoacessoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);