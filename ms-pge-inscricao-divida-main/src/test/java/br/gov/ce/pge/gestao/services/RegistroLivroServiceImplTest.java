package br.gov.ce.pge.gestao.services;

import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.entity.RegistroLivro;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.impl.RegistroLivroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroLivroServiceImplTest {
  @Mock
  private RegistroLivroRepository repository;

  @Mock
  private LivroEletronicoService livroEletronicoService;

  @InjectMocks
  private RegistroLivroServiceImpl registroLivroService;

  @Test
  void teste_registrar_ok() {
    LivroEletronico livro = new LivroEletronico();
    when(this.livroEletronicoService.findByLivroAberto()).thenReturn(livro);

    Inscricao inscricao = new Inscricao();

    this.registroLivroService.registrar(inscricao);

    verify(this.repository, times(1)).save(any(RegistroLivro.class));
  }
}
