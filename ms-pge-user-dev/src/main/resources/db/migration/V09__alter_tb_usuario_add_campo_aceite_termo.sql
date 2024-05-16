ALTER TABLE tb_usuario
ADD COLUMN dt_aceite_termo_portal_divida TIMESTAMP NULL DEFAULT NULL;

ALTER TABLE tb_usuario
ADD COLUMN dt_aceite_termo_portal_origem TIMESTAMP NULL DEFAULT NULL;

ALTER TABLE tb_usuario
DROP COLUMN fl_aceite_termo;