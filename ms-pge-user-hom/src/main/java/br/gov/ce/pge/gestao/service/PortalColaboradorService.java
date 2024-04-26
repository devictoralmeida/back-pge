package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.UsuarioInternoResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PortalColaboradorService {
    UsuarioInternoResponseDto getColaborador(String cpf);
}
