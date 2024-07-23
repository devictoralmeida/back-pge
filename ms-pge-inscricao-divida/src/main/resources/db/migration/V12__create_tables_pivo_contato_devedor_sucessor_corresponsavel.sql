create table "tb_sucessor_contato" (
    "cd_sucessor" UUID NOT NULL,
    "cd_contato" UUID NOT NULL,
    CONSTRAINT "pk_tbsucessorcontato-tbsucessor" FOREIGN KEY ("cd_sucessor") REFERENCES "tb_sucessor" ("ci_sucessor") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbsucessorcontato-tbcontato" FOREIGN KEY ("cd_contato") REFERENCES "tb_contato" ("ci_contato") ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table "tb_corresponsavel_contato" (
    "cd_corresponsavel" UUID NOT NULL,
    "cd_contato" UUID NOT NULL,
    CONSTRAINT "pk_tbcorresponsavelcontato-tbcorresponsavel" FOREIGN KEY ("cd_corresponsavel") REFERENCES "tb_corresponsavel" ("ci_corresponsavel") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbcorresponsavelcontato-tbcontato" FOREIGN KEY ("cd_contato") REFERENCES "tb_contato" ("ci_contato") ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table "tb_devedor_contato" (
    "cd_devedor" UUID NOT NULL,
    "cd_contato" UUID NOT NULL,
    CONSTRAINT "pk_tbdevedorcontato-tbdevedor" FOREIGN KEY ("cd_devedor") REFERENCES "tb_devedor" ("ci_devedor") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbdevedorcontato-tbcontato" FOREIGN KEY ("cd_contato") REFERENCES "tb_contato" ("ci_contato") ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table "tb_inscricao_devedor" (
    "cd_inscricao" UUID NOT NULL,
    "cd_devedor" UUID NOT NULL,
    CONSTRAINT "pk_tbinscricaodevedor-tbinscricao" FOREIGN KEY ("cd_inscricao") REFERENCES "tb_inscricao" ("ci_inscricao") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbinscricaodevedor-tbdevedor" FOREIGN KEY ("cd_devedor") REFERENCES "tb_devedor" ("ci_devedor") ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table "tb_divida_anexo" (
    "cd_divida" UUID NOT NULL,
    "cd_anexo" UUID NOT NULL,
    CONSTRAINT "pk_tbdividaanexo-tbdivida" FOREIGN KEY ("cd_divida") REFERENCES "tb_divida" ("ci_divida") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "pk_tbdividaanexo-tbanexo" FOREIGN KEY ("cd_anexo") REFERENCES "tb_anexo" ("ci_anexo") ON UPDATE NO ACTION ON DELETE NO ACTION
);
