package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LivroEletronicoFilterRequestDtoTest {

    private LivroEletronicoFilterRequestDto livroFilterRequestDto;

    public static LivroEletronicoFilterRequestDto getRequestFilter() {
        LivroEletronicoFilterRequestDto request = new LivroEletronicoFilterRequestDto();
        request.setNome(null);
        request.setCgf(null);
        request.setCpf(null);
        request.setCnpj(null);
        request.setNumeroInscricao(null);
        request.setNomeRazaoSocial(null);
        request.setLivros(null);
        request.setSituacao(null);
        request.setDataAbertura(null);
        request.setDataFechamento(null);
        return request;
    }

    public static LivroEletronicoFilterRequestDto getRequestFilterComLivros() {
        LivroEletronicoFilterRequestDto request = new LivroEletronicoFilterRequestDto();
        request.setNome(null);
        request.setCgf(null);
        request.setCpf(null);
        request.setCnpj(null);
        request.setNumeroInscricao(null);
        request.setNomeRazaoSocial(null);
        request.setLivros(List.of("2f990d54-d6ff-4c36-a057-0492e7c59119"));
        request.setSituacao(null);
        request.setDataAbertura(null);
        request.setDataFechamento(null);
        return request;
    }

    @BeforeEach
    public void setUp() {
        livroFilterRequestDto = new LivroEletronicoFilterRequestDto();
        livroFilterRequestDto.setNome("2024");
        livroFilterRequestDto.setCgf("890123456");
        livroFilterRequestDto.setCpf("84096143081");
        livroFilterRequestDto.setCnpj("74691807000157");
        livroFilterRequestDto.setNumeroInscricao("e6772b0b-4c8a-4311-ae65-5f09c9f9d71d");
        livroFilterRequestDto.setNomeRazaoSocial("Nexus Systems Corporation");
        livroFilterRequestDto.setLivros(List.of("Livro1", "Livro2"));
        livroFilterRequestDto.setSituacao(SituacaoLivro.ABERTO);
        livroFilterRequestDto.setDataAbertura(LocalDate.now());
        livroFilterRequestDto.setDataFechamento(LocalDate.now());
    }

    @Test
    void test_getter_and_setters() {
        assertEquals("84096143081", livroFilterRequestDto.getCpf());
        assertEquals("2024", livroFilterRequestDto.getNome());
        assertEquals("890123456", livroFilterRequestDto.getCgf());
        assertEquals("74691807000157", livroFilterRequestDto.getCnpj());
        assertEquals("Nexus Systems Corporation", livroFilterRequestDto.getNomeRazaoSocial());
        assertEquals("e6772b0b-4c8a-4311-ae65-5f09c9f9d71d", livroFilterRequestDto.getNumeroInscricao());
        assertNotNull(livroFilterRequestDto.getDataAbertura());
        assertNotNull(livroFilterRequestDto.getDataFechamento());

        assertNotNull(livroFilterRequestDto.getLivros());
        assertEquals(livroFilterRequestDto.getLivros().size(), 2);
        assertEquals(SituacaoLivro.ABERTO, livroFilterRequestDto.getSituacao());
    }

    @Test
    void test_filters_method() {
        LivroEletronicoFilterRequestDto dto = new LivroEletronicoFilterRequestDto();

        final String nome = "Livro Teste";
        final String cgf = "123456789";
        final String numeroInscricao = "2023000000001";
        LocalDate dataAbertura = LocalDate.now();
        LocalDate dataFechamento = LocalDate.now().plusDays(7);

        dto.setNome(nome);
        dto.setCgf(cgf);
        dto.setNumeroInscricao(numeroInscricao);
        dto.setDataAbertura(dataAbertura);
        dto.setDataFechamento(dataFechamento);

        Map<String, Object> filters = dto.filters();

        asserts(cgf, numeroInscricao, dataAbertura, dataFechamento, filters);
    }

    private void asserts(String cgf, String numeroInscricao, LocalDate dataAbertura, LocalDate dataFechamento, Map<String, Object> filters) {
        assertEquals("%Livro Teste%", filters.get("nome"));
        assertEquals(cgf, filters.get("cgf"));
        assertEquals(numeroInscricao, filters.get("numeroInscricao"));
        assertEquals(dataAbertura, filters.get("dataAbertura"));
        assertEquals(dataFechamento, filters.get("dataFechamento"));
    }
}
