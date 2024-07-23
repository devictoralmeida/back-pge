package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.dto.request.ContatoRequestDto;
import br.gov.ce.pge.gestao.entity.Contato;
import br.gov.ce.pge.gestao.entity.Pessoa;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContatoMapperToModel {
  private ContatoMapperToModel() {
  }

  public static List<Contato> converterList(List<ContatoRequestDto> request, Pessoa pessoa, PessoaContatoConfig pessoaContatoConfig) {
    return request.stream().map(contato -> converter(contato, pessoa, pessoaContatoConfig)).collect(Collectors.toList());
  }

  public static Contato converter(ContatoRequestDto request, Pessoa pessoa, PessoaContatoConfig pessoaContatoConfig) {
    verificaTipoContato(request);

    Contato contato = new Contato();
    contato.setId(request.getId());
    contato.setTipoContato(pessoaContatoConfig.getTipoContatoService().findById(request.getIdTipoContato()));
    contato.setValorContato(request.getValorContato());
    contato.setNumeroDdiContato(Objects.isNull(request.getNumeroDdiContato()) ? null : request.getNumeroDdiContato());
    contato.setPessoa(pessoa);
    return contato;
  }

  private static void verificaTipoContato(ContatoRequestDto request) {
    if (IdsConstants.ID_TIPO_CONTATO_EMAIL.equals(request.getIdTipoContato())) {
      request.verificaEmail();
    } else {
      request.verificaContatoNumericoAndDdi();
    }
  }
}
