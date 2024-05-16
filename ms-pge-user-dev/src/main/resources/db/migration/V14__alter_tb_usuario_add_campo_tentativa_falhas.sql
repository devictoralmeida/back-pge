ALTER TABLE tb_usuario
ADD COLUMN dt_ultima_alteracao_senha TIMESTAMP NULL DEFAULT NULL;

ALTER TABLE tb_usuario
ADD COLUMN dt_hora_bloqueio_usuario TIMESTAMP NULL DEFAULT NULL;

CREATE TABLE "tb_requisicao_logar" (
	"ci_requisicao_logar" UUID NOT NULL,
	"dt_hora_requisicao" TIMESTAMP NULL DEFAULT NULL,
	"fl_sucesso" BOOLEAN DEFAULT NULL,
	"cd_usuario" UUID NOT NULL,
	CONSTRAINT "pk_tbrequisicaologar" PRIMARY KEY ("ci_requisicao_logar"),
	CONSTRAINT "fk_tbrequisicaologar_tbusuario" FOREIGN KEY ("cd_usuario") REFERENCES "tb_usuario" ("ci_usuario") ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE tb_condicao_acesso ADD COLUMN
nr_intervalo_bloqueio INT DEFAULT NULL