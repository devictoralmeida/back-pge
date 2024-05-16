ALTER TABLE tb_requisicao_logar DROP CONSTRAINT fk_tbrequisicaologar_tbusuario;

ALTER TABLE tb_requisicao_logar
ADD CONSTRAINT fk_tbrequisicaologar_tbusuario
FOREIGN KEY (cd_usuario)
REFERENCES tb_usuario (ci_usuario)
ON DELETE CASCADE;