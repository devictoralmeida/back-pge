package br.gov.ce.pge.gestao.service.impl;


import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDto;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDto;
import br.gov.ce.pge.gestao.entity.Pessoa;
import br.gov.ce.pge.gestao.mappers.todto.PessoaMapperToDto;
import br.gov.ce.pge.gestao.repository.PessoaRepository;
import br.gov.ce.pge.gestao.service.ConsultaPessoaService;
import org.springframework.stereotype.Service;


@Service
public class ConsultaPessoaServiceImpl implements ConsultaPessoaService {
  private final PessoaRepository repository;

  public ConsultaPessoaServiceImpl(PessoaRepository repository) {
    this.repository = repository;
  }

  @Override
  public PessoaResponseDto findByDocumento(DocumentoRequestDto request) {
    return repository.findByDocumento(request.getDocumento())
            .map(PessoaMapperToDto::converter)
            .orElse(null);
  }

  @Override
  public Pessoa findByDocumentoEntity(String documento) {
    return repository.findByDocumento(documento)
            .orElse(null);
  }
}
