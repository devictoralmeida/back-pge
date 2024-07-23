CREATE SEQUENCE public.seq_auditoria
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1
    CACHE 1
    NO CYCLE;

-- Create the table with the sequence
CREATE TABLE "tb_auditoria" (
    "ci_auditoria" BIGINT DEFAULT nextval('public.seq_auditoria') NOT NULL,
    "ds_dados_antigos" VARCHAR(2048) NULL DEFAULT NULL,
    "dt_movimento" TIMESTAMP NULL DEFAULT NULL,
    "nm_usuario" VARCHAR(255) NULL DEFAULT NULL,
    "nr_timestamp" BIGINT NULL DEFAULT NULL,
    CONSTRAINT "pk_tbauditoria" PRIMARY KEY ("ci_auditoria")
);