package br.gov.ce.pge.gestao.service.impl;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.client.PortalColaboradorFeingClient;
import br.gov.ce.pge.gestao.dto.response.UsuarioInternoResponseDto;
import br.gov.ce.pge.gestao.service.PortalColaboradorService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException;

@Service
public class PortalColaboradorServiceImpl implements PortalColaboradorService {

    private static final String COLABORADOR_NAO_ENCONTRADO = "Colaborador não encontrado! Certifique-se que o colaborador esteja cadastrado e ativo no Portal do Colaborador da PGE.";
	
    private final PortalColaboradorFeingClient portalClient;
    
    public PortalColaboradorServiceImpl(PortalColaboradorFeingClient portalClient) {
    	this.portalClient = portalClient;
    }

    @Override
    public UsuarioInternoResponseDto getColaborador(String cpf) {

        var usuario = new UsuarioInternoResponseDto();

        try {
        	
        	var response = portalClient.findByCpf(cpf);

            if(response.getBody().getDesvinculado()) throw new NegocioException(COLABORADOR_NAO_ENCONTRADO);

            usuario.setArea(response.getBody().getArea());
            usuario.setNome(response.getBody().getNome());

        } catch (NegocioException e) {
            throw new NegocioException(e.getMessage());
        } catch (FeignException feignException) {
            throw new NegocioException(COLABORADOR_NAO_ENCONTRADO);
        }

        return usuario;

    }
}
