alter table tb_divida ADD COLUMN cd_produto_servico UUID NOT NULL;

alter table tb_divida ADD COLUMN cd_vara_origem UUID NULL DEFAULT NULL;
alter table tb_divida ADD CONSTRAINT "fk_tbdivida_tbvaraorigem" FOREIGN KEY ("cd_vara_origem") REFERENCES "tb_vara_origem" ("ci_vara_origem") ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table tb_divida ADD COLUMN cd_tipo_processo UUID NOT NULL;
alter table tb_divida ADD CONSTRAINT "fk_tbdivida_tbtipoprocesso" FOREIGN KEY ("cd_tipo_processo") REFERENCES "tb_tipo_processo" ("ci_tipo_processo") ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table tb_divida DROP COLUMN nm_anexo_processo;
