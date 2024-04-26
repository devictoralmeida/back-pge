package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DebitoTest {
	
	
	public static Debito getDebito() {
		var model = new Debito();
		model.setReferenciaInicial("01/2024");
		model.setReferenciaFinal("10/2024");
		model.setDataVencimento(LocalDate.parse("2024-03-30"));
		model.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
		model.setValorPrincipal(BigDecimal.valueOf(150));
		model.setValorMulta(BigDecimal.valueOf(50));
		model.setInscricao(InscricaoTest.getIncricaoComDevedorDividaStatusCorresponsaveis());
		
		return model;
	}
	
	public static List<Debito> getDebitos() {
		return Arrays.asList(getDebito());
	}
	
	@Test
	void teste_debito() {
		var model = getDebito();
		assertEquals("01/2024", model.getReferenciaInicial());
		assertEquals("10/2024", model.getReferenciaFinal());
		assertEquals(LocalDate.parse("2024-03-30"), model.getDataVencimento());
		assertEquals(LocalDate.parse("2024-03-30"), model.getDataConstituicaoDefinitivaCredito());
		assertEquals(BigDecimal.valueOf(150), model.getValorPrincipal());
		assertEquals(BigDecimal.valueOf(50), model.getValorMulta());
		assertNotNull(model.getInscricao());
	}

}
