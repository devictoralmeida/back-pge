INSERT INTO "tb_tipo_contato" ("ci_tipo_contato", "dt_criacao", "nm_usuario_cadastro", "nm_tipo_contato")
VALUES ('00503c81-8fd7-4910-9d01-20ce87d8d576', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Telefone'),
       ('842e4277-29d7-435e-853f-dfffd2682624', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Celular'),
       ('4ce0c397-8110-4198-a1e0-3f3faad9933f', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Email'),
       ('a6e221b5-e88a-4ff5-a4eb-abb4ceb17efd', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Whatsapp');

INSERT INTO "tb_tipo_pessoa" ("ci_tipo_pessoa", "dt_criacao", "nm_usuario_cadastro", "nm_tipo_pessoa")
VALUES ('22bb6280-17bd-41ff-b4df-17730fd7ac4f', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Pessoa Física'),
       ('5ba4891a-2940-441e-b6c9-3730ef5f2a93', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Pessoa Jurídica');

INSERT INTO "tb_qualificacao_corresponsavel" ("ci_qualificacao_corresponsavel", "dt_criacao", "nm_usuario_cadastro",
                                              "nm_qualificacao_corresponsavel")
VALUES ('67579895-553c-4900-ba31-01dd86f85995', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Administrador'),
       ('c7be9e81-e843-4b94-9b47-ee3306f96129', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Conselheiro de administração'),
       ('dae1e43c-45af-44b5-8776-612fb8e4817b', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Diretor'),
       ('84dd36d5-c297-4a4d-8c73-285189804cb9', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Presidente'),
       ('389f7a90-6047-439f-af84-946a22303d21', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Procurador'),
       ('a4e7c87a-5f1c-49c4-a564-72261cef29dc', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Sócio gerente'),
       ('569e8596-39c7-4725-8e1b-cdac1cd9650b', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Sócio administrador');

INSERT INTO "tb_tipo_devedor" ("ci_tipo_devedor", "dt_criacao", "nm_usuario_cadastro", "nm_tipo_devedor")
VALUES ('a948ce23-a065-4e2e-a249-463ea0f5dff2', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Principal'),
       ('373e9aa4-4001-46d1-9f70-d6f64e003d49', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Solidário'),
       ('6a75efa1-5725-4342-994d-d1a2fcd5b217', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Subsidiário'),
       ('84399d19-a3f3-4c16-bff8-f987aee944da', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Sucessor');

INSERT INTO "tb_tipo_documento" ("ci_tipo_documento", "dt_criacao", "nm_usuario_cadastro", "nm_tipo_documento")
VALUES ('1a25c081-1035-4dbd-87b2-2aede997c7d9', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'AI'),
       ('e46d79b2-425a-46b0-8a37-62e1dce96b6f', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'AIAM');


INSERT INTO "tb_status_debito" ("ci_status_debito", "dt_criacao", "nm_usuario_cadastro", "nm_status_debito")
VALUES ('5e5cbf14-f3b8-4604-8802-64a3237c8050', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Em débito'),
       ('b569c6a3-43f9-4d9a-8208-2f50ff78d68f', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Quitado'),
       ('c405babc-90bb-445e-b984-f139ea60a91b', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Extinto'),
       ('987c895b-da14-45cd-8f57-a0f97319f704', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Suspenso'),
       ('80d56404-fe02-495b-9e12-f3a6d3b4ea8e', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Remetido');

INSERT INTO "tb_tipo_acao_judicial" ("ci_tipo_acao_judicial", "dt_criacao", "nm_usuario_cadastro",
                                     "tp_acao_judicial")
VALUES ('959bcf14-41b0-4a77-9047-27e930c5af38', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Ação ordinária'),
       ('ccbc62d6-7387-46ab-91df-9ee125c695d0', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Ação ordinária com pedido de tutela antecipada'),
       ('b977c8d1-8963-4a7a-8721-0a4bbda2fa54', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Ação cautelar inominada'),
       ('d22442e4-3e95-42d4-a677-be4bbf62f1a8', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Ação cautelar fiscal'),
       ('5eff146e-e9c5-454c-a8b7-053b21ac74e7', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'IDPJ - Incidente de desconsideração da personalidade jurídica'),
       ('8bc22f85-dd96-4286-94c5-d137821e271b', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Ação de consignação em pagamento'),
       ('f3f3d740-797a-4d57-82d4-5d18918f0b64', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Execução fiscal'),
       ('0dd7a42c-ecf7-4570-a70d-7344d922118d', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Execução fiscal extrajudicial'),
       ('af87ac2a-2f10-4594-9a37-fda8df61a12a', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Mandado de segurança'),
       ('3a6e04aa-3d72-41d2-a689-30cd30f26bbd', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Embargos');

INSERT INTO "tb_providencia_acao_judicial" ("ci_providencia_acao_judicial", "dt_criacao", "nm_usuario_cadastro",
                                            "nm_providencia_acao_judicial")
VALUES ('d9421170-f289-4376-b560-6f7766364c87', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Débito garantido em ação judicial'),
       ('c8189af0-5b0a-4e46-8920-22796bcfa01b', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Determinar emissão de certidão'),
       ('10434654-89e3-4af6-8e93-a894e38931ea', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Exclusão de sócio do débito'),
       ('68cc93fa-43c1-4f57-8c90-d560233252c6', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Extinguir dívida'),
       ('144ac48e-c597-4115-aac4-8230f86b9ce1', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Redução do débito'),
       ('6d8732fc-238e-474a-8d5e-0eae8d4975a9', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Retomar parcelamento'),
       ('edee1618-e73d-45df-8e85-bfb75dacefed', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual', 'Retornar fase'),
       ('886b7799-be10-4357-856c-af0ed4191d9b', TIMESTAMP '2024-07-02T10:00:00', 'Assistente Virtual',
        'Suspensão de exigibilidade');

INSERT INTO "tb_motivo_atualizacao_fase" ("ci_motivo_atualizacao_fase", "dt_criacao", "nm_usuario_cadastro",
                                          "nm_motivo_atualizacao_fase")
VALUES ('5d9b9619-1edb-4c25-abba-b1e2bf24e9b4', TIMESTAMP '2024-07-02T10:00:00', 'Anistia',
        'Atualização de fase'),
       ('38a8150e-fdc3-4aaf-85b3-a1639c5c58a0', TIMESTAMP '2024-07-02T10:00:00', 'Sentença judicial',
        'Atualização de fase'),
       ('6e831ffb-5657-4cd4-8c5d-a79cc212c8c1', TIMESTAMP '2024-07-02T10:00:00', 'Prescrição',
        'Atualização de fase'),
       ('db5bcf9a-6228-4a4a-88bb-2f48c7d9b86d', TIMESTAMP '2024-07-02T10:00:00', 'Quitação',
        'Atualização de fase'),
       ('94b9fe59-e14c-4f3e-a784-398ac8a891b2', TIMESTAMP '2024-07-02T10:00:00', 'Outros',
        'Atualização de fase'),
       ('d4af37a9-365d-4040-9de6-c1d15e561032', TIMESTAMP '2024-07-02T10:00:00', 'Cadastramento indevido',
        'Atualização de fase'),
       ('a5afbb1a-dd1a-445d-9caa-ac147705a6bf', TIMESTAMP '2024-07-02T10:00:00', 'Liquidação antes de inscrito',
        'Atualização de fase'),
       ('4b059709-e485-4c03-8564-62b778407934', TIMESTAMP '2024-07-02T10:00:00', 'Compensação de créditos',
        'Atualização de fase'),
       ('d3c1d7ae-c24b-4dd8-8154-9c3b9715e643', TIMESTAMP '2024-07-02T10:00:00', 'Quitação - processamento de DAES',
        'Atualização de fase'),
       ('2725de0e-17cf-479d-92b7-7aa5ba14ffae', TIMESTAMP '2024-07-02T10:00:00', 'Remissão',
        'Atualização de fase');
