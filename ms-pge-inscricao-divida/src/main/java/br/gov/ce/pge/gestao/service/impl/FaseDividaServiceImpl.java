package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.FaseDividaFeingClient;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.service.FaseDividaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FaseDividaServiceImpl implements FaseDividaService {

    private final FaseDividaFeingClient faseDividaFeingClient;

    public FaseDividaServiceImpl(FaseDividaFeingClient faseDividaFeingClient) {
        this.faseDividaFeingClient = faseDividaFeingClient;
    }

    @Override
    public FaseDividaResponseDto findById(UUID id) {
        try {
            ResponseEntity<ResponseClientDto<FaseDividaResponseDto>> response = faseDividaFeingClient.findById(id);
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (FeignException.FeignClientException e) {
            if (e.status() == SharedConstant.STATUS_NOT_FOUND || e.status() == SharedConstant.STATUS_BAD_REQUEST) {
                throw new NegocioException(getMessageErro(e.getMessage()));
            }

            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<FaseDividaResponseDto> findAll() {
        try {
            ResponseEntity<ResponseClientDto<List<FaseDividaResponseDto>>> response = faseDividaFeingClient.findAll();
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (FeignException.FeignClientException e) {
            if (e.status() == SharedConstant.STATUS_NOT_FOUND || e.status() == SharedConstant.STATUS_BAD_REQUEST) {
                throw new NegocioException(getMessageErro(e.getMessage()));
            }

            throw new NegocioException(e.getMessage());
        }
    }

    String getMessageErro(String errorMessage) {
        int startIndexOfMessage = errorMessage.indexOf("mensagem\":\"") + "mensagem\":\"".length();
        int endIndexOfMessage = errorMessage.indexOf("\"", startIndexOfMessage);
        if (endIndexOfMessage == SharedConstant.END_INDEX_MESSAGE) {
            return errorMessage;
        }
        return errorMessage.substring(startIndexOfMessage, endIndexOfMessage);
    }

}
