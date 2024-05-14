package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TipoReceitaResponseDtoTest {

    public static TipoReceitaResponseDto getTipoReceita() {
        var dto = new TipoReceitaResponseDto();
        dto.setId(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"));
        dto.setCodigo("0001");
        dto.setDescricao("Receita 01");
        dto.setNatureza(Natureza.TRIBUTARIA);
        dto.setSituacao(Situacao.ATIVA);
        dto.setOrigemDebitos(List.of(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386")));
        return dto;
    }

    @Test
    void test_criacao_tipo_receita() {
        TipoReceitaResponseDto dto = getTipoReceita();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), dto.getId());
        Assertions.assertEquals("Receita 01", dto.getDescricao());
        Assertions.assertEquals("0001", dto.getCodigo());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
        Assertions.assertEquals(Natureza.TRIBUTARIA, dto.getNatureza());
        long tamanhoEsperado = 1;
        Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
    }

    @Test
    void testConstructorAndGettersSetters() {
        UUID id = UUID.randomUUID();
        String codigo = "COD123";
        String descricao = "Descrição do tipo de receita";
        List<UUID> origemDebitos = new ArrayList<>();
        origemDebitos.add(UUID.randomUUID());
        origemDebitos.add(UUID.randomUUID());
        Natureza natureza = Natureza.TRIBUTARIA;
        Situacao situacao = Situacao.ATIVA;
        LocalDateTime dataCriacao = LocalDateTime.now();
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        TipoReceitaResponseDto tipoReceita = new TipoReceitaResponseDto();
        tipoReceita.setId(id);
        tipoReceita.setCodigo(codigo);
        tipoReceita.setDescricao(descricao);
        tipoReceita.setOrigemDebitos(origemDebitos);
        tipoReceita.setNatureza(natureza);
        tipoReceita.setSituacao(situacao);
        tipoReceita.setDataCriacao(dataCriacao);
        tipoReceita.setDataAtualizacao(dataAtualizacao);

        Assertions.assertEquals(id, tipoReceita.getId());
        Assertions.assertEquals(codigo, tipoReceita.getCodigo());
        Assertions.assertEquals(descricao, tipoReceita.getDescricao());
        Assertions.assertEquals(origemDebitos, tipoReceita.getOrigemDebitos());
        Assertions.assertEquals(natureza, tipoReceita.getNatureza());
        Assertions.assertEquals(situacao, tipoReceita.getSituacao());
        Assertions.assertEquals(dataCriacao, tipoReceita.getDataCriacao());
        Assertions.assertEquals(dataAtualizacao, tipoReceita.getDataAtualizacao());
    }

    @Test
    void testDefaultConstructor() {
        TipoReceitaResponseDto tipoReceita = new TipoReceitaResponseDto();

        Assertions.assertNotNull(tipoReceita);
        Assertions.assertNull(tipoReceita.getId());
        Assertions.assertNull(tipoReceita.getCodigo());
        Assertions.assertNull(tipoReceita.getDescricao());
        Assertions.assertNull(tipoReceita.getOrigemDebitos());
        Assertions.assertNull(tipoReceita.getNatureza());
        Assertions.assertNull(tipoReceita.getSituacao());
        Assertions.assertNull(tipoReceita.getDataCriacao());
        Assertions.assertNull(tipoReceita.getDataAtualizacao());
    }
}
