package br.gov.ce.pge.gestao.services;

import br.gov.ce.pge.gestao.client.TipoReceitaFeingClient;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDtoTest;
import br.gov.ce.pge.gestao.service.impl.TipoReceitaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TipoReceitaServiceImplTest {
    @Mock
    private TipoReceitaFeingClient feignClient;

    @InjectMocks
    private TipoReceitaServiceImpl service;

    @Test
    void test_find_by_id_success() {
        var expectedDto = TipoReceitaResponseDtoTest.getTipoReceita();
        UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211");

        ResponseClientDto<TipoReceitaResponseDto> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Tipo de receita encontrada");

        ResponseEntity<ResponseClientDto<TipoReceitaResponseDto>> responseEntity = ResponseEntity.ok(responseDto);

        doReturn(responseEntity).when(this.feignClient).findById(expectedDto.getId());

        TipoReceitaResponseDto actualDto = this.service.findById(id);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }

}
