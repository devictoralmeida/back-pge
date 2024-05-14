CREATE TABLE "tb_fase_divida_tp_cobranca" (
	"cd_fase_divida" uuid NULL,
	"tp_cobranca" varchar(100) NULL
);

CREATE TABLE "tb_fase_divida_tp_cobranca_aud" (
	"cd_fase_divida" uuid NULL,
	"tp_cobranca" varchar(100) NULL,
	"cd_auditoria" int8 NOT NULL,
	"tp_movimento" int2 NULL
);