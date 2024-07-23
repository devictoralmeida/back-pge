ALTER TABLE tb_fase_divida
    ADD COLUMN fl_fase_anterior BOOLEAN DEFAULT FALSE;
ALTER TABLE tb_fase_divida
    ADD COLUMN fl_fase_atual BOOLEAN DEFAULT FALSE;
