DELETE
FROM tb_motivo_atualizacao_fase
WHERE ci_motivo_atualizacao_fase IN ('5d9b9619-1edb-4c25-abba-b1e2bf24e9b4', '38a8150e-fdc3-4aaf-85b3-a1639c5c58a0',
                                     '6e831ffb-5657-4cd4-8c5d-a79cc212c8c1', 'db5bcf9a-6228-4a4a-88bb-2f48c7d9b86d',
                                     '94b9fe59-e14c-4f3e-a784-398ac8a891b2', 'd4af37a9-365d-4040-9de6-c1d15e561032',
                                     'a5afbb1a-dd1a-445d-9caa-ac147705a6bf', '4b059709-e485-4c03-8564-62b778407934',
                                     'd3c1d7ae-c24b-4dd8-8154-9c3b9715e643', '2725de0e-17cf-479d-92b7-7aa5ba14ffae');

INSERT INTO "tb_motivo_atualizacao_fase" ("ci_motivo_atualizacao_fase", "dt_criacao",
                                          "nm_motivo_atualizacao_fase", "nm_usuario_cadastro")
VALUES ('5d9b9619-1edb-4c25-abba-b1e2bf24e9b4', TIMESTAMP '2024-07-02T10:00:00', 'Anistia',
        'Assistente Virtual'),
       ('38a8150e-fdc3-4aaf-85b3-a1639c5c58a0', TIMESTAMP '2024-07-02T10:00:00', 'Sentença judicial',
        'Assistente Virtual'),
       ('6e831ffb-5657-4cd4-8c5d-a79cc212c8c1', TIMESTAMP '2024-07-02T10:00:00', 'Prescrição',
        'Assistente Virtual'),
       ('db5bcf9a-6228-4a4a-88bb-2f48c7d9b86d', TIMESTAMP '2024-07-02T10:00:00', 'Quitação',
        'Assistente Virtual'),
       ('94b9fe59-e14c-4f3e-a784-398ac8a891b2', TIMESTAMP '2024-07-02T10:00:00', 'Outros',
        'Assistente Virtual'),
       ('d4af37a9-365d-4040-9de6-c1d15e561032', TIMESTAMP '2024-07-02T10:00:00', 'Cadastramento indevido',
        'Assistente Virtual'),
       ('a5afbb1a-dd1a-445d-9caa-ac147705a6bf', TIMESTAMP '2024-07-02T10:00:00', 'Liquidação antes de inscrito',
        'Assistente Virtual'),
       ('4b059709-e485-4c03-8564-62b778407934', TIMESTAMP '2024-07-02T10:00:00', 'Compensação de créditos',
        'Assistente Virtual'),
       ('d3c1d7ae-c24b-4dd8-8154-9c3b9715e643', TIMESTAMP '2024-07-02T10:00:00', 'Quitação - processamento de DAES',
        'Assistente Virtual'),
       ('2725de0e-17cf-479d-92b7-7aa5ba14ffae', TIMESTAMP '2024-07-02T10:00:00', 'Remissão',
        'Assistente Virtual');
