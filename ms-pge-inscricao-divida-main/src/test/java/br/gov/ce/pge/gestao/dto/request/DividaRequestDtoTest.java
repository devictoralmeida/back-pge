package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;

public class DividaRequestDtoTest {

	public static DividaRequestDto getDivida() {
		var dto = new DividaRequestDto();
		dto.setId(null);
		dto.setIdOrigemDebito(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));
		dto.setIdTipoReceita(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"));
		dto.setDisposicoesLegais("teste");
		dto.setNaturezaFundamentacao(null);
		dto.setInexistenciaCausaSuspensivas(null);
		dto.setTipoDocumento(TipoDocumento.AIAM);
		dto.setNumeroDocumento("1234567890");
		dto.setDataDocumento(LocalDate.parse("2024-03-20"));
		dto.setTermoRevelia("123456789");
		dto.setDataTermoRevelia(LocalDate.parse("2024-03-20"));
		dto.setNumeroOficio("ABC123");
		dto.setTipoProcesso(TipoProcesso.CONAT);
		dto.setNumeroProcessoAdministrativo("12345/2024");
		dto.setDataProcesso(LocalDate.parse("2024-03-20"));
		dto.setNumeroAcordao("6789");
		dto.setDataTransitoJulgado(LocalDate.parse("2024-03-20"));
		dto.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-20"));
		dto.setPlacaVeiculo(null);
		dto.setNumeroChassi(null);
		dto.setGuiaItcd("XYZ789");
		dto.setSequencialParcelamento(null);
		dto.setProtocoloJudicial(null);
		dto.setNomeAnexoProcessoDigitalizado(null);

		return dto;
	}

	@Test
	public void teste_divida() {
		var dto = getDivida();
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
