package br.gov.ce.pge.gestao.services;

import br.gov.ce.pge.gestao.client.OrigemDebitoFeingClient;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.service.impl.OrigemDebitoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
class OrigemDebitoServiceImplTest {
    @Mock
    private OrigemDebitoFeingClient feignClient;

    @InjectMocks
    private OrigemDebitoServiceImpl service;

    @Test
    void test_find_by_id_success() throws JsonProcessingException {
        var expectedDto = OrigemDebitoResponseDtoTest.getOrigem();
        ResponseClientDto<OrigemDebitoResponseDto> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Origem de débito encontrada");
        ResponseEntity<ResponseClientDto<OrigemDebitoResponseDto>> responseEntity = ResponseEntity.ok(responseDto);

        doReturn(responseEntity).when(this.feignClient).findById(expectedDto.getId());

        OrigemDebitoResponseDto actualDto = this.service.findById(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }
}
