package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.dto.request.ContatoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ContatoRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Contato;
import br.gov.ce.pge.gestao.entity.Pessoa;
import br.gov.ce.pge.gestao.entity.TipoContatoTest;
import br.gov.ce.pge.gestao.service.TipoContatoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContatoMapperToModelTest {
  private TipoContatoService tipoContatoService;
  private PessoaContatoConfig pessoaContatoConfig;
  private Pessoa pessoa;

  @BeforeEach
  void setUp() {
    tipoContatoService = mock(TipoContatoService.class);
    pessoaContatoConfig = mock(PessoaContatoConfig.class);
    pessoa = new Pessoa();
    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
  }

  @Test
  void teste_converter_lista() {
    List<ContatoRequestDto> request = ContatoRequestDtoTest.getContatosList();

    when(tipoContatoService.findById(request.get(0).getIdTipoContato())).thenReturn(TipoContatoTest.getTipoContatoEmail());
    when(tipoContatoService.findById(request.get(1).getIdTipoContato())).thenReturn(TipoContatoTest.getTipoContatoCelular());

    List<Contato> contatos = ContatoMapperToModel.converterList(request, pessoa, pessoaContatoConfig);
    assertEquals(2, contatos.size());
    assertEquals(request.get(0).getIdTipoContato(), contatos.get(0).getTipoContato().getId());
    assertEquals(request.get(0).getValorContato(), contatos.get(0).getValorContato());
    assertEquals(request.get(1).getIdTipoContato(), contatos.get(1).getTipoContato().getId());
    assertEquals(request.get(1).getValorContato(), contatos.get(1).getValorContato());
    assertNotNull(contatos.get(0).getPessoa());
    assertNotNull(contatos.get(1).getPessoa());
  }

  @Test
  void teste_converter() {
    ContatoRequestDto request = ContatoRequestDtoTest.getContatoEmail();
    when(tipoContatoService.findById(request.getIdTipoContato())).thenReturn(TipoContatoTest.getTipoContatoEmail());

    Contato contato = ContatoMapperToModel.converter(request, pessoa, pessoaContatoConfig);
    assertEquals(request.getIdTipoContato(), contato.getTipoContato().getId());
    assertEquals(request.getValorContato(), contato.getValorContato());
    assertNotNull(contato.getPessoa());
  }

  @Test
  void teste_exception_email_invalido() {
    List<ContatoRequestDto> contatos = ContatoRequestDtoTest.getContatosList();
    ContatoRequestDto request = contatos.get(0);
    request.setValorContato("invalid_email");
    assertThrows(NegocioException.class, () -> ContatoMapperToModel.converter(request, pessoa, pessoaContatoConfig));
  }

  @Test
  void teste_exception_email_passando_ddi() {
    List<ContatoRequestDto> contatos = ContatoRequestDtoTest.getContatosList();
    ContatoRequestDto request = contatos.get(0);
    request.setNumeroDdiContato("55");
    assertThrows(NegocioException.class, () -> ContatoMapperToModel.converter(request, pessoa, pessoaContatoConfig));
  }

  @Test
  void teste_exception_numero_invalido() {
    List<ContatoRequestDto> contatos = ContatoRequestDtoTest.getContatosList();
    ContatoRequestDto request = contatos.get(1);
    request.setValorContato("invalid_number");
    assertThrows(NegocioException.class, () -> ContatoMapperToModel.converter(request, pessoa, pessoaContatoConfig));
  }

  @Test
  void teste_exception_contato_numerico_sem_ddi() {
    List<ContatoRequestDto> contatos = ContatoRequestDtoTest.getContatosList();
    ContatoRequestDto request = contatos.get(1);
    request.setNumeroDdiContato(null);
    assertThrows(NegocioException.class, () -> ContatoMapperToModel.converter(request, pessoa, pessoaContatoConfig));
  }

}
