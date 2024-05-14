package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.DebitoRequestDto;
import br.gov.ce.pge.gestao.dto.request.DebitoRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.InscricaoTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class DebitoMapperToModelTest {

    @Test
    void teste_converter() {

        Debito model = DebitoMapperToModel.converter(DebitoRequestDtoTest.getDebito(), InscricaoTest.get_inscricao_completa());

        assertEquals("01/2024", model.getReferenciaInicial());
        assertEquals("10/2024", model.getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-30"), model.getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), model.getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), model.getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), model.getValorMulta());
        assertNotNull(model.getInscricao());
    }

    @Test
    void teste_converter_lista() {

        List<Debito> lista = DebitoMapperToModel.converterList(DebitoRequestDtoTest.get_debitos_list(), InscricaoTest.get_inscricao_completa());

        assertEquals("01/2024", lista.get(0).getReferenciaInicial());
        assertEquals("10/2024", lista.get(0).getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-30"), lista.get(0).getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), lista.get(0).getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), lista.get(0).getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), lista.get(0).getValorMulta());
        assertNotNull(lista.get(0).getInscricao());
    }

    @Test
    void testConverterListUpdate() {
        Inscricao inscricaoMock = mock(Inscricao.class);
        List<DebitoRequestDto> requestList = DebitoRequestDtoTest.get_debitos_list();

        List<Debito> debitos = DebitoMapperToModel.converterListUpdate(requestList, inscricaoMock);

        assertNotNull(debitos);
        assertEquals(1, debitos.size());
        assertEquals("01/2024", debitos.get(0).getReferenciaInicial());
        assertEquals("10/2024", debitos.get(0).getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-30"), debitos.get(0).getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), debitos.get(0).getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), debitos.get(0).getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), debitos.get(0).getValorMulta());
        assertNotNull(debitos.get(0).getInscricao());
    }
}
