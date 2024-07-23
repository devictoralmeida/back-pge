package br.gov.ce.pge.gestao.constants;

import java.util.UUID;

public class IdsConstants {
    private IdsConstants() {
    }

    public static final UUID ID_TIPO_PESSOA_FISICA = UUID.fromString("22bb6280-17bd-41ff-b4df-17730fd7ac4f");
    public static final UUID ID_TIPO_PESSOA_JURIDICA = UUID.fromString("5ba4891a-2940-441e-b6c9-3730ef5f2a93");

    public static final UUID ID_TIPO_DEVEDOR_PRINCIPAL = UUID.fromString("a948ce23-a065-4e2e-a249-463ea0f5dff2");
    public static final UUID ID_TIPO_DEVEDOR_SOLIDARIO = UUID.fromString("373e9aa4-4001-46d1-9f70-d6f64e003d49");
    public static final UUID ID_TIPO_DEVEDOR_SUBSIDIARIO = UUID.fromString("6a75efa1-5725-4342-994d-d1a2fcd5b217");

    public static final UUID ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR = UUID.fromString("1e451c33-ae09-4912-a293-6a30f6d04116");
    public static final UUID ID_TIPO_PAPEL_PESSOA_DIVIDA_SUCESSOR = UUID.fromString("353d0b0b-ac4e-4615-aa2a-b7c9cdf4ea59");
    public static final UUID ID_TIPO_PAPEL_PESSOA_DIVIDA_CORRESPONSAVEL = UUID.fromString("9f26c891-1659-494a-a13e-407e11084410");

    public static final UUID ID_TIPO_DOCUMENTO_AI = UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9");
    public static final UUID ID_TIPO_DOCUMENTO_AIAM = UUID.fromString("e46d79b2-425a-46b0-8a37-62e1dce96b6f");

    public static final UUID ID_ORIGEM_DEBITO_AI = UUID.fromString("9890c7cc-d3dc-4330-950e-84be33df904d");
    public static final UUID ID_ORIGEM_DEBITO_AIAM = UUID.fromString("dc816fd4-b7e1-46c9-8c0c-092a1eea12f3");
    public static final UUID ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO = UUID.fromString("197abebc-4714-4ce0-97cb-019734a2e96b");
    public static final UUID ID_ORIGEM_DEBITO_ITCD = UUID.fromString("b21ab54e-df02-4295-a2b2-28b2fcd5d9e6");
    public static final UUID ID_ORIGEM_DEBITO_IPVA = UUID.fromString("274e732c-d6db-4aee-8773-d4d9be141a81");
    public static final UUID ID_ORIGEM_DEBITO_ICMS = UUID.fromString("7ba8597c-1746-4d4b-9c3c-de2ab4aad5d7");
    public static final UUID ID_ORIGEM_DEBITO_DETRAN = UUID.fromString("79394bbf-b5a1-41b0-bd0c-b0cb9a22309b");
    public static final UUID ID_ORIGEM_DEBITO_CUSTAS_JUDICIAIS = UUID.fromString("5171b483-a84b-41fd-9cbb-9e89f4476050");
    public static final UUID ID_ORIGEM_DEBITO_TCE = UUID.fromString("4f1455a6-9f9f-426a-aff2-6aa7a4fcad0f");
    public static final UUID ID_ORIGEM_DEBITO_TJ = UUID.fromString("8256fef5-abe5-4be3-befb-ae6f192aec99");

    public static final UUID ID_TIPO_CONTATO_TELEFONE = UUID.fromString("00503c81-8fd7-4910-9d01-20ce87d8d576");
    public static final UUID ID_TIPO_CONTATO_CELULAR = UUID.fromString("842e4277-29d7-435e-853f-dfffd2682624");
    public static final UUID ID_TIPO_CONTATO_EMAIL = UUID.fromString("4ce0c397-8110-4198-a1e0-3f3faad9933f");
    public static final UUID ID_TIPO_CONTATO_WHATSAPP = UUID.fromString("a6e221b5-e88a-4ff5-a4eb-abb4ceb17efd");
}
