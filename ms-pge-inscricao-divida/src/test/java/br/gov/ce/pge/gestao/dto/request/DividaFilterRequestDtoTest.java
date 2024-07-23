package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Operador;
import br.gov.ce.pge.gestao.enums.TipoValor;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DividaFilterRequestDtoTest {

    public static DividaFilterRequestDto getRequest() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setCpf("12345678912");
        request.setAjuizada(false);
        request.setNotificada(false);
        request.setProtestada(false);
        request.setQuitadoOuExtinto(false);
        request.setCobrancaSuspensa(false);

        return request;
    }

    public static DividaFilterRequestDto getRequestFilterCgf() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setCgf("000000000");

        return request;
    }

    public static DividaFilterRequestDto getRequestNomeDevedor() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setConsultaGenerica("Teste");

        return request;
    }

    public static DividaFilterRequestDto getRequestLoteNaoTributariaErrado() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setNatureza(Natureza.NAO_TRIBUTARIA);
        request.setLote("N12N4");

        return request;
    }

    public static DividaFilterRequestDto getRequestNaoTributaria() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setNatureza(Natureza.NAO_TRIBUTARIA);

        return request;
    }

    public static DividaFilterRequestDto getRequestLoteTributariaErrado() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setNatureza(Natureza.TRIBUTARIA);
        request.setLote("N1234");

        return request;
    }

    public static DividaFilterRequestDto getRequestTributaria() {

        DividaFilterRequestDto request = new DividaFilterRequestDto();
        request.setNatureza(Natureza.TRIBUTARIA);
        return request;
    }

    @Test
    void test_filters() {

        DividaFilterRequestDto filtro = new DividaFilterRequestDto();

        filtro.equals(null);
        filtro.hashCode();

        definirDados(filtro);

        Map<String, Object> filters = filtro.filters();

        asserts(filters);

        assertOrdenacao(filtro, filters);

    }

    private void assertOrdenacao(DividaFilterRequestDto filtro, Map<String, Object> filters) {

        filtro.setOrderBy("numeroInscricao");
        filters = filtro.filters();
        assertEquals("nr_inscricao", filters.get("orderBy"));

        filtro.setOrderBy("numeroInscricao-desc");
        filters = filtro.filters();
        assertEquals("nr_inscricao desc", filters.get("orderBy"));

        filtro.setOrderBy("documento");
        filters = filtro.filters();
        assertEquals("p.nr_documento_pessoa, p.nr_cgf_pessoa", filters.get("orderBy"));

        filtro.setOrderBy("documento-desc");
        filters = filtro.filters();
        assertEquals("p.nr_documento_pessoa desc, p.nr_cgf_pessoa desc", filters.get("orderBy"));

        filtro.setOrderBy("nomeDevedor");
        filters = filtro.filters();
        assertEquals("p.nm_pessoa", filters.get("orderBy"));

        filtro.setOrderBy("nomeDevedor-desc");
        filters = filtro.filters();
        assertEquals("p.nm_pessoa desc", filters.get("orderBy"));

        filtro.setOrderBy("arquivos");
        filters = filtro.filters();
        assertEquals("tem_anexo", filters.get("orderBy"));

        filtro.setOrderBy("arquivos-desc");
        filters = filtro.filters();
        assertEquals("tem_anexo desc", filters.get("orderBy"));

        filtro.setOrderBy("saldoDevedor");
        filters = filtro.filters();
        assertEquals("vl_saldo_devedor", filters.get("orderBy"));

        filtro.setOrderBy("saldoDevedor-desc");
        filters = filtro.filters();
        assertEquals("vl_saldo_devedor desc", filters.get("orderBy"));

        filtro.setOrderBy("numeroAI");
        filters = filtro.filters();
        assertEquals("nr_processo_administrativo", filters.get("orderBy"));

        filtro.setOrderBy("numeroAI-desc");
        filters = filtro.filters();
        assertEquals("nr_processo_administrativo desc", filters.get("orderBy"));

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            filtro.setOrderBy("outro");
            filtro.filters();
        });

        Assertions.assertEquals("Não é possível ordenar por: outro", error.getMessage());

    }

    private void asserts(Map<String, Object> filters) {
        assertEquals("PRINCIPAL", filters.get("tipoDevedor"));
        assertEquals("124324", filters.get("numeroProcesso"));
        assertEquals("123123434512", filters.get("cgf"));
        assertNotNull(filters.get("tipoReceita"));
        assertEquals("12213", filters.get("lote"));
        assertEquals("ITCD1234", filters.get("guiaItcd"));
        assertEquals("0879877986798678687", filters.get("cnpj"));
        assertEquals("1212342", filters.get("numeroInscricao"));
        assertEquals("%TESTE%", filters.get("nomeDevedor"));
        assertNotNull(filters.get("faseAnterior"));
        assertEquals(0L, filters.get("limit"));
        assertEquals(true, filters.get("cobrancaSuspensa"));
        assertEquals(Natureza.TRIBUTARIA, filters.get("natureza"));
        assertEquals("VALOR_PRINCIPAL", filters.get("tipoValor"));
        assertEquals("2131212", filters.get("placa"));
        assertEquals(true, filters.get("notificada"));
        assertNotNull(filters.get("faseAtual"));
        assertNotNull(filters.get("origemDebito"));
        assertNotNull(filters.get("produtoServico"));
        assertEquals(true, filters.get("ajuizada"));
        assertEquals("1212321434234", filters.get("chassi"));
        assertEquals("121234234243", filters.get("numeroAI"));
        assertEquals("ENTRE", filters.get("operador"));
        assertEquals("p.nm_pessoa", filters.get("orderBy"));
    }

    private void definirDados(DividaFilterRequestDto filtro) {
        filtro.setCpf("00012345578");
        filtro.setCgf("123123434512");
        filtro.setCnpj("0879877986798678687");
        filtro.setChassi("1212321434234");
        filtro.setGuiaItcd("ITCD1234");
        filtro.setTipoDevedor("PRINCIPAL");
        filtro.setAjuizada(true);
        filtro.setLote("12213");
        filtro.setCobrancaSuspensa(true);
        filtro.setFaseAnterior(Arrays.asList("c0c9ec63-6b64-4e33-8804-ed1be0b2c621"));
        filtro.setFaseAtual(Arrays.asList("c0c9ec63-6b64-4e33-8804-ed1be0b2c621"));
        filtro.setTipoReceita(Arrays.asList("c0c9ec63-6b64-4e33-8804-ed1be0b2c621"));
        filtro.setOrigemDebito(Arrays.asList("c0c9ec63-6b64-4e33-8804-ed1be0b2c621"));
        filtro.setProdutoServico(Arrays.asList("c0c9ec63-6b64-4e33-8804-ed1be0b2c621"));
        filtro.setNatureza(Natureza.TRIBUTARIA);
        filtro.setNomeDevedor("Teste");
        filtro.setNumeroAI("121234234243");
        filtro.setValorUm(new BigDecimal(1));
        filtro.setValorDois(new BigDecimal(2));
        filtro.setTipoValor(TipoValor.VALOR_PRINCIPAL);
        filtro.setOperador(Operador.ENTRE);
        filtro.setQuitadoOuExtinto(true);
        filtro.setProtestada(true);
        filtro.setPlaca("2131212");
        filtro.setNotificada(true);
        filtro.setNumeroProcesso("124324");
        filtro.setNumeroInscricao("1212342");
        filtro.setProtocoloJudicial("1212312");
    }

    @Test
    void teste_consulta_generica_nome_devedor() {

        DividaFilterRequestDto dto = new DividaFilterRequestDto();
        dto.setConsultaGenerica("Teste Razao Social");

        Map<String, Object> filters = dto.filters();

        assertEquals("%TESTE RAZAO SOCIAL%", filters.get("nomeDevedor"));
    }

    @Test
    void teste_consulta_generica() {

        DividaFilterRequestDto dto = new DividaFilterRequestDto();
        dto.setConsultaGenerica("12345");

        Map<String, Object> filters = dto.filters();

        assertEquals("12345", filters.get("consultaGenerica"));
    }
}
