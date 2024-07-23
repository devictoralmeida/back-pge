CREATE TABLE "tb_motivo_atualizacao_divida"
(
    "ci_motivo_atualizacao_divida" UUID         NOT NULL,
    "dt_atualizacao"               TIMESTAMP    NULL DEFAULT NULL,
    "dt_criacao"                   TIMESTAMP    NOT NULL,
    "nm_usuario_atualizacao"       VARCHAR(255) NULL DEFAULT NULL,
    "nm_usuario_cadastro"          VARCHAR(255) NULL DEFAULT NULL,
    "ds_motivo_atualizacao_divida" VARCHAR(500) NOT NULL,
    "nm_anexo"                     VARCHAR(255) NULL DEFAULT NULL,
    "cd_divida"                    UUID         NOT NULL,
    CONSTRAINT "pk_tb_motivo_atualizacao_divida" PRIMARY KEY ("ci_motivo_atualizacao_divida"),
    CONSTRAINT "fk_tb_motivo_atualizacao_divida_tb_divida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida")
);


UPDATE "tb_tipo_contato"
SET "nm_tipo_contato" = 'E-mail'
WHERE "nm_tipo_contato" = 'Email';
