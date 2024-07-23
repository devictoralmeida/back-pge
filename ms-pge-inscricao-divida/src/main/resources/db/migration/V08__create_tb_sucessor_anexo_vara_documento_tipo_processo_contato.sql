create table "tb_sucessor"(
    "ci_sucessor" UUID NOT NULL,
    "dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
    "ds_bairro_sucessor" VARCHAR(100) NOT NULL,
    "nr_cep_sucessor" VARCHAR(15) NOT NULL,
    "nr_cgf_sucessor" VARCHAR(15) NULL DEFAULT NULL,
    "ds_complemento_sucessor" VARCHAR(255) NULL DEFAULT NULL,
    "ds_distrito_sucessor" VARCHAR(70) NULL DEFAULT NULL,
    "nr_documento_sucessor" VARCHAR(14) NOT NULL,
    "ds_logradouro_sucessor" VARCHAR(255) NOT NULL,
    "ds_municipio_sucessor" VARCHAR(100) NOT NULL,
    "nm_sucessor" VARCHAR(255) NOT NULL,
    "nr_endereco_sucessor" VARCHAR(10) NOT NULL,
    "ds_tipo_pessoa_sucessor" VARCHAR(50) NOT NULL,
    "ds_uf_sucessor" VARCHAR(20) NOT NULL,
    "dt_declaracao_ausencia_contato" TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT "pk_tbsucessor" PRIMARY KEY ("ci_sucessor")
);

create table "tb_tipo_documento_anexo" (
    "ci_tipo_documento_anexo" UUID NOT NULL,
    "dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
    "tp_documento" VARCHAR(50) NOT NULL,
    CONSTRAINT "pk_tb_tipodocumentoanexo" PRIMARY KEY ("ci_tipo_documento_anexo")
);

create table "tb_anexo" (
    "ci_anexo" UUID NOT NULL,
    "dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
    "nm_anexo" VARCHAR(255) NOT NULL,
    "cd_tipo_documento_anexo" UUID NOT NULL,
    CONSTRAINT "pk_tbanexo" PRIMARY KEY ("ci_anexo"),
    CONSTRAINT "fk_tbanexo_tb_tipodocumentoanexo" FOREIGN KEY ("cd_tipo_documento_anexo") REFERENCES "tb_tipo_documento_anexo" ("ci_tipo_documento_anexo") ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table "tb_vara_origem"(
    "ci_vara_origem" UUID NOT NULL,
    "dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
    "nm_vara_origem" VARCHAR(255) NOT NULL,
    CONSTRAINT "pk_tbvaraorigem" PRIMARY KEY ("ci_vara_origem")
);

create table "tb_tipo_processo" (
    "ci_tipo_processo" UUID NOT NULL,
    "dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
    "tp_processo" VARCHAR(50) NOT NULL,
    CONSTRAINT "pk_tb_tipoprocesso" PRIMARY KEY ("ci_tipo_processo")
);

create table "tb_contato" (
    "ci_contato" UUID NOT NULL,
    "dt_atualizacao" TIMESTAMP NULL DEFAULT NULL,
    "dt_criacao" TIMESTAMP NOT NULL,
    "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
    "tp_contato" VARCHAR(50) NOT NULL,
    "nr_contato" VARCHAR(15) NULL DEFAULT NULL,
    "ds_email" VARCHAR(255) NULL DEFAULT NULL,
    CONSTRAINT "pk_tb_contato" PRIMARY KEY ("ci_contato")
);
