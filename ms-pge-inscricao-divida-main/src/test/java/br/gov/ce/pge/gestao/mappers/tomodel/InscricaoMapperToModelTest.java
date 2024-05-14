package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDtoTest;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.InscricaoTest;
import br.gov.ce.pge.gestao.enums.StatusInscricao;
import br.gov.ce.pge.gestao.service.FileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class InscricaoMapperToModelTest {

    @Mock
    private FileService fileService;

    @Test
    void teste_converter() {

        Inscricao model = InscricaoMapperToModel.converter(InscricaoRequestDtoTest.get_inscricao_completa(), TipoReceitaResponseDtoTest.getTipoReceita(), OrigemDebitoResponseDtoTest.getOrigem(), this.fileService);

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

    @Test
    void teste_converter_update() {
        Inscricao inscricao = InscricaoTest.get_inscricao_completa();
        InscricaoRequestDto requestDto = InscricaoRequestDtoTest.get_inscricao_completa_update();

        Inscricao updatedInscricao = InscricaoMapperToModel.converterUpdate(requestDto, inscricao, TipoReceitaResponseDtoTest.getTipoReceita(), OrigemDebitoResponseDtoTest.getOrigem(), this.fileService);

        assertEquals(requestDto.getStatus(), updatedInscricao.getStatus());
        assertEquals(requestDto.getDevedor().getDocumento(), updatedInscricao.getDevedor().getDocumento());
        assertEquals(requestDto.getDivida().getId(), updatedInscricao.getDivida().getId());
        assertEquals(requestDto.getCorresponsaveis().size(), updatedInscricao.getCorresponsaveis().size());
        assertEquals(requestDto.getDebitos().size(), updatedInscricao.getDebitos().size());
    }

}
