package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ce.pge.gestao.dto.response.DividaResponseDto;
import br.gov.ce.pge.gestao.entity.DividaTest;
import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;
import br.gov.ce.pge.gestao.service.impl.FileServiceGCPImpl;

@ExtendWith(MockitoExtension.class)
class DividaMapperToDtoTest {
	
	@Mock
	FileServiceGCPImpl fileService;

    @Test
    void teste_converter() {

        DividaResponseDto dto = DividaMapperToDto.converter(DividaTest.get_divida(), fileService);
        assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), dto.getIdOrigemDebito());
        assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), dto.getIdTipoReceita());
        assertEquals("teste", dto.getDisposicoesLegais());
        assertEquals(null, dto.getNaturezaFundamentacao());
        assertEquals(null, dto.getInexistenciaCausaSuspensivas());
        assertEquals(TipoDocumento.AIAM, dto.getTipoDocumento());
        assertEquals("1234567890", dto.getNumeroDocumento());
        assertEquals(LocalDate.parse("2024-03-20"), dto.getDataDocumento());
        assertEquals("123456789", dto.getTermoRevelia());
        assertEquals(LocalDate.parse("2024-03-20"), dto.getDataTermoRevelia());
        assertEquals("ABC123", dto.getNumeroOficio());
        assertEquals(TipoProcesso.CONAT, dto.getTipoProcesso());
        assertEquals("12345/2024", dto.getNumeroProcessoAdministrativo());
        assertEquals(LocalDate.parse("2024-03-20"), dto.getDataProcesso());
        assertEquals("6789", dto.getNumeroAcordao());
        assertEquals(LocalDate.parse("2024-03-20"), dto.getDataTransitoJulgado());
        assertEquals(LocalDate.parse("2024-03-20"), dto.getDataConstituicaoDefinitivaCredito());
        assertEquals(null, dto.getPlacaVeiculo());
        assertEquals(null, dto.getNumeroChassi());
        assertEquals("XYZ789", dto.getGuiaItcd());
        assertEquals(null, dto.getSequencialParcelamento());
        assertEquals(null, dto.getProtocoloJudicial());
        assertEquals(null, dto.getNomeAnexoProcessoDigitalizado());

    }

}
