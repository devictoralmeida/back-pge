alter table tb_inscricao add column dt_declaracao_ausencia_corresponsavel timestamp null default null;

alter table tb_inscricao ADD COLUMN cd_sucessor UUID NULL DEFAULT NULL;
alter table tb_inscricao ADD CONSTRAINT "fk_tbinscricao_tbsucessor" FOREIGN KEY ("cd_sucessor") REFERENCES "tb_sucessor" ("ci_sucessor") ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table tb_inscricao ADD COLUMN cd_fase_atual UUID NOT NULL;

alter table tb_inscricao ADD COLUMN cd_fase_anterior UUID NULL DEFAULT NULL;

alter table tb_inscricao drop column cd_devedor;
