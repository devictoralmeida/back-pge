package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDto;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDto;
import br.gov.ce.pge.gestao.entity.Pessoa;
import org.springframework.stereotype.Service;

@Service
public interface ConsultaPessoaService {
  PessoaResponseDto findByDocumento(DocumentoRequestDto request);

  Pessoa findByDocumentoEntity(String documento);
}
