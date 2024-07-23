package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivroEletronicoComRegistrosInscricaoResponseDtoTest {
    public static LivroEletronicoComRegistrosInscricaoResponseDto get_livro_eletronico_com_registros_inscricao() {
        LivroEletronicoComRegistrosInscricaoResponseDto dto = new LivroEletronicoComRegistrosInscricaoResponseDto();
        UUID id = UUID.fromString(("c4095434-f704-4209-be74-3d42d519d438"));
        final String nome = "2024";
        final SituacaoLivro situacao = SituacaoLivro.ABERTO;
        LocalDateTime dataAbertura = LocalDateTime.now();

        List<RegistroInscricaoResponseDto> registros = List.of(
                new RegistroInscricaoResponseDto(UUID.randomUUID().toString(), "documento", "cgf", "nome", "adaa9502-913e-40aa-92e6-dd4bd5846ce9", BigDecimal.ONE, "usuario", LocalDateTime.now(), null, null)
        );

        dto.setId(id);
        dto.setNome(nome);
        dto.setSituacao(situacao);
        dto.setDataAbertura(dataAbertura);
        dto.setDataFechamento(null);
        dto.setRegistros(registros);

        return dto;
    }

    @Test
    void test_getter_setter() {
        LivroEletronicoComRegistrosInscricaoResponseDto dto = new LivroEletronicoComRegistrosInscricaoResponseDto();
        UUID id = UUID.randomUUID();
        final String nome = "Livro de Teste";
        final SituacaoLivro situacao = SituacaoLivro.ABERTO;
        LocalDateTime dataAbertura = LocalDateTime.now();
        LocalDateTime dataFechamento = LocalDateTime.now();
        List<RegistroInscricaoResponseDto> registros = List.of(
                new RegistroInscricaoResponseDto(UUID.randomUUID().toString(), "documento", "cgf", "nome", "adaa9502-913e-40aa-92e6-dd4bd5846ce9", BigDecimal.ONE, "usuario", LocalDateTime.now(), null, null)
        );

        dto.setId(id);
        dto.setNome(nome);
        dto.setSituacao(situacao);
        dto.setDataAbertura(dataAbertura);
        dto.setDataFechamento(dataFechamento);
        dto.setRegistros(registros);

        asserts(dto, id, nome, situacao, dataAbertura, dataFechamento, registros);
    }

    private void asserts(LivroEletronicoComRegistrosInscricaoResponseDto dto, UUID id, String nome, SituacaoLivro situacao, LocalDateTime dataAbertura, LocalDateTime dataFechamento, List<RegistroInscricaoResponseDto> registros) {
        assertEquals(id, dto.getId());
        assertEquals(nome, dto.getNome());
        assertEquals(situacao, dto.getSituacao());
        assertEquals(dataAbertura, dto.getDataAbertura());
        assertEquals(dataFechamento, dto.getDataFechamento());
        assertEquals(registros, dto.getRegistros());
    }
}
