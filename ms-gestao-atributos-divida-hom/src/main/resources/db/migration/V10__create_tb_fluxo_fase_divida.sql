CREATE TABLE "tb_fluxo_fase_divida" (
	"cd_fase_divida_atual" uuid NOT NULL,
	"cd_fase_divida_permitida" uuid NOT NULL,
	CONSTRAINT "fk_fluxofasedividaatual_fasedivida" FOREIGN KEY ("cd_fase_divida_atual") REFERENCES "tb_fase_divida" ("ci_fase_divida"),
	CONSTRAINT "fk_fluxofasedividapermitida_fasedivida" FOREIGN KEY ("cd_fase_divida_atual") REFERENCES "tb_fase_divida" ("ci_fase_divida")
);