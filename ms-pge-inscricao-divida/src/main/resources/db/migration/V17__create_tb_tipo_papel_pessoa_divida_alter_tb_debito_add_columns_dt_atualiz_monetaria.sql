CREATE TABLE "tb_tipo_papel_pessoa_divida"
(
    "ci_tipo_papel_pessoa_divida"         UUID         NOT NULL,
    "dt_atualizacao"                 TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                     TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"         VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"            VARCHAR(255) NULL DEFAULT NULL,
    "tp_papel_pessoa_divida"         VARCHAR(50)  NOT NULL,
    CONSTRAINT "pk_tbtipopapelpessoadivida" PRIMARY KEY ("ci_tipo_papel_pessoa_divida")
);

ALTER TABLE tb_papel_pessoa_divida
    ADD COLUMN cd_tipo_papel_pessoa_divida UUID NULL DEFAULT NULL;
ALTER TABLE tb_papel_pessoa_divida
    ADD CONSTRAINT fk_tb_papel_pessoa_divida_tb_tipo_papel_pessoa_divida FOREIGN KEY (cd_tipo_papel_pessoa_divida) REFERENCES tb_tipo_papel_pessoa_divida (ci_tipo_papel_pessoa_divida);
ALTER TABLE tb_papel_pessoa_divida
    DROP COLUMN vl_papel_pessoa_divida;

ALTER TABLE tb_debito
    ADD COLUMN dt_atualizacao_monetaria TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE tb_debito
    ADD COLUMN vl_principal_corrigido NUMERIC(19, 2) NULL DEFAULT NULL;
