ALTER TABLE tb_devedor add column dt_declaracao_ausencia_contato TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE tb_devedor add column tp_devedor VARCHAR(50) NOT NULL DEFAULT 1;

ALTER TABLE tb_devedor drop column ds_tipo_contato_devedor;
ALTER TABLE tb_devedor drop column nr_contato_devedor;
ALTER TABLE tb_devedor drop column ds_email_devedor;

ALTER TABLE tb_corresponsavel drop column ds_tipo_contato_corresponsavel;
ALTER TABLE tb_corresponsavel drop column nr_contato_corresponsavel;
ALTER TABLE tb_corresponsavel drop column ds_email_corresponsavel;
