CREATE TABLE "tb_tipo_documento"
(
    "ci_tipo_documento"      UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "nm_tipo_documento"      VARCHAR(10)  NOT NULL,
    CONSTRAINT "pk_tb_tipo_documento" PRIMARY KEY ("ci_tipo_documento")
);

CREATE TABLE "tb_status_debito"
(
    "ci_status_debito"       UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "nm_status_debito"       VARCHAR(20)  NOT NULL,
    CONSTRAINT "pk_tbstatusdebito" PRIMARY KEY ("ci_status_debito")
);

CREATE TABLE "tb_tipo_pessoa"
(
    "ci_tipo_pessoa"         UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "nm_tipo_pessoa"         VARCHAR(20)  NOT NULL,
    CONSTRAINT "pk_tb_tipo_pessoa" PRIMARY KEY ("ci_tipo_pessoa")
);

CREATE TABLE "tb_qualificacao_corresponsavel"
(
    "ci_qualificacao_corresponsavel" UUID         NOT NULL,
    "dt_atualizacao"                 TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                     TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"         VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"            VARCHAR(255) NULL DEFAULT NULL,
    "nm_qualificacao_corresponsavel" VARCHAR(30)  NOT NULL,
    CONSTRAINT "pk_tbqualificacaocorresponsavel" PRIMARY KEY ("ci_qualificacao_corresponsavel")
);

CREATE TABLE "tb_tipo_devedor"
(
    "ci_tipo_devedor"        UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "nm_tipo_devedor"        VARCHAR(30)  NOT NULL,
    CONSTRAINT "pk_tbtipodevedor" PRIMARY KEY ("ci_tipo_devedor")
);

CREATE TABLE "tb_pessoa"
(
    "ci_pessoa"              UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "nm_pessoa"              VARCHAR(255) NOT NULL,
    "nr_cgf_pessoa"       VARCHAR(15) NULL DEFAULT NULL UNIQUE,
    "nr_documento_pessoa" VARCHAR(14) NOT NULL UNIQUE,
    "cd_tipo_pessoa"      UUID        NOT NULL,
    CONSTRAINT "pk_tbpessoa" PRIMARY KEY ("ci_pessoa"),
    CONSTRAINT "fk_tbpessoa_tbtipopessoa" FOREIGN KEY ("cd_tipo_pessoa") REFERENCES "tb_tipo_pessoa" ("ci_tipo_pessoa") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_endereco"
(
    "ci_endereco"            UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "ds_bairro"              VARCHAR(100) NOT NULL,
    "nr_cep"                 VARCHAR(15)  NOT NULL,
    "ds_complemento"         VARCHAR(255) NULL DEFAULT NULL,
    "ds_distrito"            VARCHAR(70)  NULL DEFAULT NULL,
    "ds_logradouro"          VARCHAR(255) NOT NULL,
    "ds_municipio"           VARCHAR(100) NOT NULL,
    "nr_endereco"            VARCHAR(10)  NOT NULL,
    "ds_uf"                  VARCHAR(20)  NOT NULL,
    "fl_principal"           BOOLEAN           DEFAULT FALSE,
    "cd_pessoa"              UUID         NOT NULL,
    CONSTRAINT "pk_tbendereco" PRIMARY KEY ("ci_endereco"),
    CONSTRAINT "fk_tbendereco_tbpessoa" FOREIGN KEY ("cd_pessoa") REFERENCES "tb_pessoa" ("ci_pessoa") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_papel_pessoa_divida"
(
    "ci_papel_pessoa_divida"         UUID         NOT NULL,
    "dt_atualizacao"                 TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                     TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"         VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"            VARCHAR(255) NULL DEFAULT NULL,
    "vl_papel_pessoa_divida"         VARCHAR(20)  NOT NULL,
    "cd_qualificacao_corresponsavel" UUID         NULL DEFAULT NULL,
    "cd_tipo_devedor"                UUID         NULL DEFAULT NULL,
    CONSTRAINT "pk_tbpapelpessoadivida" PRIMARY KEY ("ci_papel_pessoa_divida"),
    CONSTRAINT "fk_tbpapelpessoadivida_tbqualificacaocorresponsavel" FOREIGN KEY ("cd_qualificacao_corresponsavel") REFERENCES "tb_qualificacao_corresponsavel" ("ci_qualificacao_corresponsavel") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_tbpapelpessoadivida_tbtipodevedor" FOREIGN KEY ("cd_tipo_devedor") REFERENCES "tb_tipo_devedor" ("ci_tipo_devedor") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_divida_pessoa"
(
    "ci_divida_pessoa"       UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "dt_declaracao_ausencia_contato" TIMESTAMP    NULL DEFAULT NULL,
    "cd_divida"              UUID         NOT NULL,
    "cd_pessoa"                      UUID         NOT NULL,
    "cd_papel_pessoa_divida" UUID         NOT NULL,
    CONSTRAINT "pk_tbdividapessoa" PRIMARY KEY ("ci_divida_pessoa"),
    CONSTRAINT "pk_tbdividapessoa-tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbdividapessoa-tbpessoa" FOREIGN KEY ("cd_pessoa") REFERENCES "tb_pessoa" ("ci_pessoa") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbdividapessoa-tbpapelpessoadivida" FOREIGN KEY ("cd_papel_pessoa_divida") REFERENCES "tb_papel_pessoa_divida" ("ci_papel_pessoa_divida") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_tipo_contato"
(
    "ci_tipo_contato"        UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "nm_tipo_contato"        VARCHAR(10)  NOT NULL,
    CONSTRAINT "pk_tb_tipo_contato" PRIMARY KEY ("ci_tipo_contato")
);

CREATE TABLE "tb_motivo_atualizacao_fase"
(
    "ci_motivo_atualizacao_fase" UUID         NOT NULL,
    "dt_atualizacao"             TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                 TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"     VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"        VARCHAR(255) NULL DEFAULT NULL,
    "nm_motivo_atualizacao_fase" VARCHAR(255) NOT NULL,
    CONSTRAINT "pk_tbmotivoatualizacaofase" PRIMARY KEY ("ci_motivo_atualizacao_fase")
);

CREATE TABLE "tb_tipo_acao_judicial"
(
    "ci_tipo_acao_judicial"  UUID         NOT NULL,
    "dt_atualizacao"         TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"             TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
    "tp_acao_judicial"       VARCHAR(255) NOT NULL,
    CONSTRAINT "pk_tb_tipo_acao_judicial" PRIMARY KEY ("ci_tipo_acao_judicial")
);

CREATE TABLE "tb_providencia_acao_judicial"
(
    "ci_providencia_acao_judicial" UUID         NOT NULL,
    "dt_atualizacao"               TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                   TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"       VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"          VARCHAR(255) NULL DEFAULT NULL,
    "nm_providencia_acao_judicial" VARCHAR(255) NOT NULL,
    CONSTRAINT "pk_tb_providencia_acao_judicial" PRIMARY KEY ("ci_providencia_acao_judicial")
);

CREATE TABLE "tb_fase_divida"
(
    "ci_fase_divida"             UUID         NOT NULL,
    "dt_atualizacao"             TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                 TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"     VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"        VARCHAR(255) NULL DEFAULT NULL,
    "dt_fase_divida"             TIMESTAMP    NOT NULL,
    "nm_anexo"                   VARCHAR(255) NULL DEFAULT NULL,
    "ds_observacao"              VARCHAR(500) NOT NULL,
    "cd_divida"                  UUID         NOT NULL,
    "cd_motivo_atualizacao_fase" UUID         NOT NULL,
    "cd_fase"                    UUID         NOT NULL,
    CONSTRAINT "pk_tbfasedivida" PRIMARY KEY ("ci_fase_divida"),
    CONSTRAINT "fk_tbfasedivida_tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_tbfasedivida_tbmotivoatualizacaofase" FOREIGN KEY ("cd_motivo_atualizacao_fase") REFERENCES "tb_motivo_atualizacao_fase" ("ci_motivo_atualizacao_fase") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "tb_acao_judicial"
(
    "ci_acao_judicial"             UUID         NOT NULL,
    "dt_atualizacao"               TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                   TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"       VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"          VARCHAR(255) NULL DEFAULT NULL,
    "nr_ordem_judicial"            VARCHAR(50)  NOT NULL,
    "nm_juizo"                     VARCHAR(250) NOT NULL,
    "nm_reu"                       VARCHAR(250) NOT NULL,
    "nm_autor"                     VARCHAR(250) NOT NULL,
    "nm_anexo"                     VARCHAR(255) NOT NULL,
    "ds_observacao"                VARCHAR(500) NULL DEFAULT NULL,
    "cd_divida"                    UUID         NOT NULL,
    "cd_tipo_acao_judicial"        UUID         NOT NULL,
    "cd_providencia_acao_judicial" UUID         NOT NULL,
    "cd_fase_divida"               UUID         NOT NULL,
    CONSTRAINT "pk_tb_acao_judicial" PRIMARY KEY ("ci_acao_judicial"),
    CONSTRAINT "fk_tbacaojudicial_tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_tbacaojudicial_tbtipoacaojudicial" FOREIGN KEY ("cd_tipo_acao_judicial") REFERENCES "tb_tipo_acao_judicial" ("ci_tipo_acao_judicial") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_tbacaojudicial_tbprovidenciaacaojudicial" FOREIGN KEY ("cd_providencia_acao_judicial") REFERENCES "tb_providencia_acao_judicial" ("ci_providencia_acao_judicial") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_tbacaojudicial_tbfasedivida" FOREIGN KEY ("cd_fase_divida") REFERENCES "tb_fase_divida" ("ci_fase_divida") ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE tb_registro_livro
    ADD COLUMN cd_divida UUID NULL DEFAULT NULL;
ALTER TABLE tb_registro_livro
    ADD CONSTRAINT "fk_tbregistrolivro_tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tb_registro_livro
    DROP COLUMN cd_inscricao;


ALTER TABLE tb_divida
    ADD COLUMN nr_inscricao VARCHAR(13);
ALTER TABLE tb_divida
    ADD COLUMN dt_declaracao_ausencia_corresponsavel TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE tb_divida
    ADD COLUMN cd_tipo_documento UUID NULL DEFAULT NULL;
ALTER TABLE tb_divida
    ADD CONSTRAINT "fk_tbdivida_tbtipodocumento" FOREIGN KEY ("cd_tipo_documento") REFERENCES "tb_tipo_documento" ("ci_tipo_documento") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tb_divida
    DROP COLUMN tp_documento;

ALTER TABLE tb_debito
    ADD COLUMN cd_divida UUID NOT NULL;
ALTER TABLE tb_debito
    ADD COLUMN cd_status_debito UUID NOT NULL;
ALTER TABLE tb_debito
    ADD CONSTRAINT "fk_tbdebito_tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tb_debito
    ADD CONSTRAINT "fk_tbdebito_tbstatusdebito" FOREIGN KEY ("cd_status_debito") REFERENCES "tb_status_debito" ("ci_status_debito") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tb_debito
    DROP COLUMN cd_inscricao;


ALTER TABLE tb_contato
    ADD COLUMN cd_pessoa UUID NOT NULL;
ALTER TABLE tb_contato
    ADD COLUMN cd_tipo_contato UUID NOT NULL;
ALTER TABLE tb_contato
    DROP COLUMN nr_contato;
ALTER TABLE tb_contato
    DROP COLUMN ds_email;
ALTER TABLE tb_contato
    DROP COLUMN tp_contato;
ALTER TABLE tb_contato
    ADD COLUMN vl_contato VARCHAR(255) NOT NULL;
ALTER TABLE tb_contato
    ADD CONSTRAINT "fk_tbcontato_tbtipocontato" FOREIGN KEY ("cd_tipo_contato") REFERENCES "tb_tipo_contato" ("ci_tipo_contato") ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tb_contato
    ADD CONSTRAINT "fk_tbcontato_tbpessoa" FOREIGN KEY ("cd_pessoa") REFERENCES "tb_pessoa" ("ci_pessoa") ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE tb_anexo
    ADD COLUMN cd_divida UUID NOT NULL;
ALTER TABLE tb_anexo
    ADD CONSTRAINT "fk_tbanexo_tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION;
