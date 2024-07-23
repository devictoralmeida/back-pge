ALTER TABLE tb_contato
    ADD COLUMN nr_ddi_contato VARCHAR(4) DEFAULT NULL;

ALTER TABLE tb_debito
    ALTER COLUMN ds_referencia_final DROP NOT NULL;

DELETE
FROM tb_tipo_devedor
WHERE nm_tipo_devedor = 'Sucessor';
