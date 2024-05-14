CREATE TABLE "tb_fase_divida" (
	"ci_fase_divida" uuid NOT NULL,
	"cd_fase_divida" varchar(4) NOT NULL,
	"nm_fase_divida" varchar(150) NOT NULL,
	"ds_fase_divida" varchar(500) NOT NULL,
	"tp_movimentacao" varchar(100) NOT NULL,
	"ds_situacao_fase_divida" varchar(7) NOT NULL,
	"fl_exigivel_cobranca" bool NULL,
	"tp_cobranca" varchar(100) NULL,
	"dt_atualizacao" timestamp NULL,
	"dt_criacao" timestamp NOT NULL,
	"nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
	"nm_usuario_cadastro" VARCHAR(255) NULL DEFAULT NULL,
	CONSTRAINT "pk_tbfasedivida" PRIMARY KEY ("ci_fase_divida"),
	CONSTRAINT "uk_cdfasedivida" UNIQUE ("cd_fase_divida")
);

CREATE TABLE "tb_fase_divida_aud" (
	"ci_fase_divida" uuid NOT NULL,
	"cd_fase_divida" varchar(4) NOT NULL,
	"nm_fase_divida" varchar(150) NOT NULL,
	"ds_fase_divida" varchar(500) NOT NULL,
	"tp_movimentacao" varchar(100) NOT NULL,
	"ds_situacao_fase_divida" varchar(7) NOT NULL,
	"fl_exigivel_cobranca" bool NULL,
	"tp_cobranca" varchar(100) NULL,
	"cd_auditoria" int8 NOT NULL,
	"tp_movimento" int2 NULL
);