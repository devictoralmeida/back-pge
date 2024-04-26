package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.StatusInscricao;

public class InscricaoRequestDtoTest {

	public static InscricaoRequestDto getIncricaoSomenteComDevedor() {
		var model = new InscricaoRequestDto();
		model.setDevedor(DevedorRequestDtoTest.getDevedorPessoaFisica());

		return model;
	}

	public static InscricaoRequestDto getIncricaoComDevedorDivida() {
		var model = new InscricaoRequestDto();
		model.setDevedor(DevedorRequestDtoTest.getDevedorPessoaFisica());
		model.setDivida(DividaRequestDtoTest.getDivida());

		return model;
	}

	public static InscricaoRequestDto getIncricaoComDevedorDividaStatus() {
		var model = new InscricaoRequestDto();
		model.setDevedor(DevedorRequestDtoTest.getDevedorPessoaFisica());
		model.setDivida(DividaRequestDtoTest.getDivida());
		model.setStatus(StatusInscricao.EM_ANALISE);

		return model;
	}
	
	public static InscricaoRequestDto getIncricaoComDevedorDividaStatusCorresponsaveis() {
		var model = new InscricaoRequestDto();
		model.setDevedor(DevedorRequestDtoTest.getDevedorPessoaFisica());
		model.setDivida(DividaRequestDtoTest.getDivida());
		model.setStatus(StatusInscricao.EM_ANALISE);
		model.setCorresponsaveis(CorresponsavelRequestDtoTest.getListCorresponsaveis());
		return model;
	}
	
	public static InscricaoRequestDto getIncricaoCompleta() {
		var model = new InscricaoRequestDto();
		model.setDevedor(DevedorRequestDtoTest.getDevedorPessoaFisica());
		model.setDivida(DividaRequestDtoTest.getDivida());
		model.setStatus(StatusInscricao.EM_ANALISE);
		model.setCorresponsaveis(CorresponsavelRequestDtoTest.getListCorresponsaveis());
		model.setDebitos(DebitoRequestDtoTest.getDebitos());
		return model;
	}

	@Test
	void teste_inscricao_incompleto() {
		var model = getIncricaoSomenteComDevedor();

		assertNotNull(model.getDevedor());
		assertNull(model.getDivida());
		assertNull(model.getStatus());

		model = getIncricaoComDevedorDivida();

		assertNotNull(model.getDevedor());
		assertNotNull(model.getDivida());
		assertEquals(null, model.getStatus());		
		
		model = getIncricaoComDevedorDividaStatus();

		assertNotNull(model.getDevedor());
		assertNotNull(model.getDivida());
		assertEquals(StatusInscricao.EM_ANALISE, model.getStatus());
		
		model = getIncricaoComDevedorDividaStatusCorresponsaveis();

		assertNotNull(model.getDevedor());
		assertNotNull(model.getDivida());
		assertEquals(StatusInscricao.EM_ANALISE, model.getStatus());
		assertNotNull(model.getCorresponsaveis());
		assertEquals(2, model.getCorresponsaveis().size());
		assertEquals("Kamila Lima", model.getCorresponsaveis().get(0).getNomeRazaoSocial());
		assertEquals("Osvaldo e Julio Marketing ME", model.getCorresponsaveis().get(1).getNomeRazaoSocial());
		
		model = getIncricaoCompleta();

		assertNotNull(model.getDevedor());
		assertNotNull(model.getDivida());
		assertEquals(StatusInscricao.EM_ANALISE, model.getStatus());
		assertNotNull(model.getCorresponsaveis());
		assertEquals(2, model.getCorresponsaveis().size());
		assertEquals("Kamila Lima", model.getCorresponsaveis().get(0).getNomeRazaoSocial());
		assertEquals("Osvaldo e Julio Marketing ME", model.getCorresponsaveis().get(1).getNomeRazaoSocial());
		assertEquals(BigDecimal.valueOf(150), model.getDebitos().get(0).getValorPrincipal());
		assertEquals(BigDecimal.valueOf(50), model.getDebitos().get(0).getValorMulta());
	}

}
