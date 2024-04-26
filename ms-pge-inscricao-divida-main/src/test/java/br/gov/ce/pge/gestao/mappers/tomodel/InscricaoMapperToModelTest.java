package br.gov.ce.pge.gestao.mappers.tomodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDtoTest;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.enums.StatusInscricao;
import br.gov.ce.pge.gestao.service.FileService;

class InscricaoMapperToModelTest {
	
	@Mock
	private FileService fileService;

	@Test
	void teste_converter() {
		
		Inscricao model = InscricaoMapperToModel.converter(InscricaoRequestDtoTest.getIncricaoCompleta(), TipoReceitaResponseDtoTest.getTipoReceita(), OrigemDebitoResponseDtoTest.getOrigem(), fileService);
		
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
