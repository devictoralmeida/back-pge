package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.ContatoResponseDto;
import br.gov.ce.pge.gestao.dto.response.EnderecoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDto;
import br.gov.ce.pge.gestao.entity.Endereco;
import br.gov.ce.pge.gestao.entity.Pessoa;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class PessoaMapperToDto {
  private PessoaMapperToDto() {
  }

  public static PessoaResponseDto converter(Pessoa pessoa) {
    PessoaResponseDto dto = new PessoaResponseDto();

    Endereco enderecoPrincipal = pessoa.getEnderecos().stream()
            .filter(Endereco::getPrincipal).findFirst().orElse(null);
    BeanUtils.copyProperties(pessoa, dto);
    dto.setEndereco(enderecoPrincipal != null ? new EnderecoResponseDto(enderecoPrincipal) : null);
    dto.setContatos(pessoa.getContatos().stream().map(ContatoResponseDto::new).collect(Collectors.toList()));
    dto.setIdTipoPessoa(pessoa.getTipoPessoa().getId());
    return dto;
  }
}
