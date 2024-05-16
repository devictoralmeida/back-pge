CREATE TABLE "tb_usuario_termo" (
	"cd_usuario" UUID NOT NULL,
	"cd_termo_condicao" UUID NOT NULL,
	CONSTRAINT "fk_tbusuariotermo_tbusuario" FOREIGN KEY ("cd_usuario") REFERENCES "tb_usuario" ("ci_usuario") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_tbusuariotermo_tbtermocondicao" FOREIGN KEY ("cd_termo_condicao") REFERENCES "tb_termo_condicao" ("ci_termo_condicao") ON UPDATE NO ACTION ON DELETE NO ACTION
);