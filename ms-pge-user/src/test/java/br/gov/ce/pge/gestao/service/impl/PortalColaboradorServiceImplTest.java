package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.client.PortalColaboradorFeingClient;
import br.gov.ce.pge.gestao.dto.response.ColaboradorResponseDtoTest;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
public class PortalColaboradorServiceImplTest {

    @InjectMocks
    @Autowired
    private PortalColaboradorServiceImpl service;

    @Mock
    PortalColaboradorFeingClient portalClient;

    @Test
    void findBy_cpf_sucesso() throws JsonProcessingException {
        when(portalClient.findByCpf(anyString()))
                .thenReturn(new ResponseEntity<>(ColaboradorResponseDtoTest.getColaboradorResponseDto(), HttpStatus.OK));

        var colaborador = service.getColaborador("05459813360");

        assertNotNull(colaborador);
        assertEquals("Jose Junior", colaborador.getNome());
        assertEquals("TI", colaborador.getArea());
    }
    
    @Test
    void findBy_cpf_desvinculado() throws JsonProcessingException {
        when(portalClient.findByCpf(anyString()))
                .thenReturn(new ResponseEntity<>(ColaboradorResponseDtoTest.getColaboradorResponseDto_desvinculado(), HttpStatus.OK));

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
        	service.getColaborador("05459813360");
		});

		Assertions.assertEquals("Colaborador não encontrado! Certifique-se que o colaborador esteja cadastrado e ativo no Portal do Colaborador da PGE.", error.getMessage());
        
    }
    
}
