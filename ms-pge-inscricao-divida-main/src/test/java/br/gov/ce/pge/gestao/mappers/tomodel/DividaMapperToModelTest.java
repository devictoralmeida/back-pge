package br.gov.ce.pge.gestao.mappers.tomodel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ce.pge.gestao.dto.request.DividaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDtoTest;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;
import br.gov.ce.pge.gestao.service.FileService;

@ExtendWith({ MockitoExtension.class })
class DividaMapperToModelTest {
	
	@Mock
	private FileService fileService;

	@Test
	void teste_converter() {
		Divida model = DividaMapperToModel.converter(DividaRequestDtoTest.getDivida(),
				TipoReceitaResponseDtoTest.getTipoReceita(), OrigemDebitoResponseDtoTest.getOrigem(), fileService);
		
		assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), model.getIdOrigemDebito());
		assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), model.getIdTipoReceita());
		assertEquals("teste", model.getDisposicoesLegais());
		assertEquals(null, model.getNaturezaFundamentacao());
		assertEquals(null, model.getInexistenciaCausaSuspensivas());
		assertEquals(TipoDocumento.AIAM.name(), model.getTipoDocumento());
		assertEquals("1234567890", model.getNumeroDocumento());
		assertEquals(LocalDate.parse("2024-03-20"), model.getDataDocumento());
		assertEquals("123456789", model.getTermoRevelia());
		assertEquals(LocalDate.parse("2024-03-20"), model.getDataTermoRevelia());
		assertEquals("ABC123", model.getNumeroOficio());
		assertEquals(TipoProcesso.CONAT, model.getTipoProcesso());
		assertEquals("12345/2024", model.getNumeroProcessoAdministrativo());
		assertEquals(LocalDate.parse("2024-03-20"), model.getDataProcesso());
		assertEquals("6789", model.getNumeroAcordao());
		assertEquals(LocalDate.parse("2024-03-20"), model.getDataTransitoJulgado());
		assertEquals(LocalDate.parse("2024-03-20"), model.getDataConstituicaoDefinitivaCredito());
		assertEquals(null, model.getPlacaVeiculo());
		assertEquals(null, model.getNumeroChassi());
		assertEquals("XYZ789", model.getGuiaItcd());
		assertEquals(null, model.getSequencialParcelamento());
		assertEquals(null, model.getProtocoloJudicial());
		assertEquals(null, model.getNomeAnexoProcessoDigitalizado());
	}

}
