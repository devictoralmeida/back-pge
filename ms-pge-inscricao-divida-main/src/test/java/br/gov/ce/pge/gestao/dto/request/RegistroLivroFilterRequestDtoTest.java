package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistroLivroFilterRequestDtoTest {
    public static RegistroLivroFilterRequestDto getRequestFilterPagina() {
        var request = new RegistroLivroFilterRequestDto();
        request.setIdLivro(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        request.setPagina(1);
        request.setOrderBy("pagina");

        return request;
    }

    public static RegistroLivroFilterRequestDto getRequestFilterLinha() {
        var request = new RegistroLivroFilterRequestDto();
        request.setIdLivro(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        request.setLinha(1);
        request.setOrderBy("linha");

        return request;
    }

    public static RegistroLivroFilterRequestDto getRequestFilterCpf() {
        var request = new RegistroLivroFilterRequestDto();
        request.setCpf("01234567789");
        request.setOrderBy("cpf");

        return request;
    }

    @Test
    void test_filters() {

        var filtro = new RegistroLivroFilterRequestDto();
        definirInformacoes(filtro);

        Map<String, Object> filters = filtro.filters();

        asserts(filters);

        assertOrdenacao(filtro, filters);

    }

    private void assertOrdenacao(RegistroLivroFilterRequestDto filtro, Map<String, Object> filters) {

        filtro.setOrderBy("pagina");
        filters = filtro.filters();
        assertEquals("r.nr_pagina", filters.get("orderBy"));

        filtro.setOrderBy("pagina-desc");
        filters = filtro.filters();
        assertEquals("r.nr_pagina desc", filters.get("orderBy"));

        filtro.setOrderBy("linha");
        filters = filtro.filters();
        assertEquals("r.nr_linha", filters.get("orderBy"));

        filtro.setOrderBy("linha-desc");
        filters = filtro.filters();
        assertEquals("r.nr_linha desc", filters.get("orderBy"));

        filtro.setOrderBy("cgf");
        filters = filtro.filters();
        assertEquals("dev.nr_cgf_devedor", filters.get("orderBy"));

        filtro.setOrderBy("cgf-desc");
        filters = filtro.filters();
        assertEquals("dev.nr_cgf_devedor desc", filters.get("orderBy"));

        filtro.setOrderBy("cpf");
        filters = filtro.filters();
        assertEquals("dev.nr_documento_devedor", filters.get("orderBy"));

        filtro.setOrderBy("cpf-desc");
        filters = filtro.filters();
        assertEquals("dev.nr_documento_devedor desc", filters.get("orderBy"));

        filtro.setOrderBy("cnpj");
        filters = filtro.filters();
        assertEquals("dev.nr_documento_devedor", filters.get("orderBy"));

        filtro.setOrderBy("cnpj-desc");
        filters = filtro.filters();
        assertEquals("dev.nr_documento_devedor desc", filters.get("orderBy"));

        filtro.setOrderBy("numeroInscricao");
        filters = filtro.filters();
        assertEquals("i.nr_inscricao", filters.get("orderBy"));

        filtro.setOrderBy("numeroInscricao-desc");
        filters = filtro.filters();
        assertEquals("i.nr_inscricao desc", filters.get("orderBy"));

        filtro.setOrderBy("nomeRazaoSocial");
        filters = filtro.filters();
        assertEquals("dev.nm_devedor", filters.get("orderBy"));

        filtro.setOrderBy("nomeRazaoSocial-desc");
        filters = filtro.filters();
        assertEquals("dev.nm_devedor desc", filters.get("orderBy"));

        filtro.setOrderBy("dataRegistro");
        filters = filtro.filters();
        assertEquals("r.dt_criacao", filters.get("orderBy"));

        filtro.setOrderBy("dataRegistro-desc");
        filters = filtro.filters();
        assertEquals("r.dt_criacao desc", filters.get("orderBy"));

        filtro.setOrderBy("valor");
        filters = filtro.filters();
        assertEquals("d.vl_principal_debito", filters.get("orderBy"));

        filtro.setOrderBy("valor-desc");
        filters = filtro.filters();
        assertEquals("d.vl_principal_debito desc", filters.get("orderBy"));

        filtro.setOrderBy("origemDebito");
        filters = filtro.filters();
        assertEquals("div.nm_origem_debito", filters.get("orderBy"));

        filtro.setOrderBy("origemDebito-desc");
        filters = filtro.filters();
        assertEquals("div.nm_origem_debito desc", filters.get("orderBy"));

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
            filtro.setOrderBy("outro");
            filtro.filters();
        });

        Assertions.assertEquals("Não é possível ordenar por: outro", error.getMessage());

    }

    private void asserts(Map<String, Object> filters) {
        assertEquals(1, filters.get("linha"));
        assertEquals(1, filters.get("pagina"));
        assertEquals(5L, filters.get("limit"));
        assertEquals(0L, filters.get("offset"));
        assertEquals("00000000000", filters.get("cpf"));
        assertEquals("000000000000", filters.get("cnpj"));
        assertEquals("00000000000", filters.get("cgf"));
        assertEquals("2024000000015", filters.get("numeroInscricao"));
        assertEquals("%TESTE%", filters.get("nomeRazaoSocial"));
        assertEquals("r.nr_linha desc", filters.get("orderBy"));
    }

    private void definirInformacoes(RegistroLivroFilterRequestDto filtro) {
        filtro.setIdLivro(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        filtro.setLinha(1);
        filtro.setCpf("00000000000");
        filtro.setPagina(1);
        filtro.setOrderBy("linha-desc");
        filtro.setLimit(5);
        filtro.setOffset(0);
        filtro.setCgf("00000000000");
        filtro.setCnpj("000000000000");
        filtro.setDataRegistro(LocalDate.now());
        filtro.setNomeRazaoSocial("teste");
        filtro.setNumeroInscricao("2024000000015");
    }
}
