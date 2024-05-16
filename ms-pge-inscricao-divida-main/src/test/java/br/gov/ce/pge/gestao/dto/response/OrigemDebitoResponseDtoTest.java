package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrigemDebitoResponseDtoTest {

    public static OrigemDebitoResponseDto getOrigem() {
        var response = new OrigemDebitoResponseDto();
        response.setId(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));
        response.setDescricao("teste");
        response.setSituacao(Situacao.ATIVA);
        response.setNome("teste nome origem");
        return response;
    }

    @Test
    void test_criacao_origem() {
        OrigemDebitoResponseDto dto = getOrigem();

        assertNotNull(dto);
        Assertions.assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), dto.getId());
        Assertions.assertEquals("teste nome origem", dto.getNome());
        Assertions.assertEquals("teste", dto.getDescricao());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
    }

    @Test
    void test_constructor_and_getters_setters() {
        UUID id = UUID.randomUUID();
        String nome = "Origem Teste";
        String descricao = "Descrição da origem";
        Situacao situacao = Situacao.ATIVA;
        LocalDateTime dataCriacao = LocalDateTime.now();
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        OrigemDebitoResponseDto origem = new OrigemDebitoResponseDto();
        origem.setId(id);
        origem.setNome(nome);
        origem.setDescricao(descricao);
        origem.setSituacao(situacao);
        origem.setDataCriacao(dataCriacao);
        origem.setDataAtualizacao(dataAtualizacao);

        asserts(id, nome, descricao, situacao, dataCriacao, dataAtualizacao, origem);
    }

    private void asserts(UUID id, String nome, String descricao, Situacao situacao, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, OrigemDebitoResponseDto origem) {
        Assertions.assertEquals(id, origem.getId());
        Assertions.assertEquals(nome, origem.getNome());
        Assertions.assertEquals(descricao, origem.getDescricao());
        Assertions.assertEquals(situacao, origem.getSituacao());
        Assertions.assertEquals(dataCriacao, origem.getDataCriacao());
        Assertions.assertEquals(dataAtualizacao, origem.getDataAtualizacao());
    }

    @Test
    void test_default_constructor() {
        OrigemDebitoResponseDto origem = new OrigemDebitoResponseDto();

        assertNotNull(origem);
        assertNull(origem.getId());
        assertNull(origem.getNome());
        assertNull(origem.getDescricao());
        assertNull(origem.getSituacao());
        assertNull(origem.getDataCriacao());
        assertNull(origem.getDataAtualizacao());
    }

}
