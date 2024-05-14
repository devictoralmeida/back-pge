package br.gov.ce.pge.gestao.services;

import br.gov.ce.pge.gestao.dao.LivroEletronicoDao;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import br.gov.ce.pge.gestao.repository.LivroEletronicoRepository;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.service.impl.LivroEletronicoServiceImpl;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroEletronicoServiceImplTest {

  @Mock
  private LivroEletronicoRepository livroEletronicoRepository;

  @InjectMocks
  private LivroEletronicoServiceImpl livroEletronicoService;

  @Mock
  private RegistroLivroRepository registroLivroRepository;

  @Mock
  private LivroEletronicoDao livroEletronicoDao;

  @Mock
  private OrigemDebitoService origemDebitoService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.livroEletronicoService = new LivroEletronicoServiceImpl(this.livroEletronicoRepository, this.registroLivroRepository, this.livroEletronicoDao, this.origemDebitoService);
  }

  @Test
  void abertura_livro_ok() {

    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(null);

    this.livroEletronicoService.abertura();

    verify(this.livroEletronicoRepository).save(any());
  }

  @Test
  void abertura_livro_excecao() {

    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(new LivroEletronico());

    assertThrows(NegocioException.class, () -> this.livroEletronicoService.abertura());
  }

  @Test
  void fechamento_livro_ok() {

    LivroEletronico livroAberto = new LivroEletronico();
    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(livroAberto);

    this.livroEletronicoService.fechamento();

    verify(this.livroEletronicoRepository).save(livroAberto);
  }

  @Test
  void livro_aberto_existe() {
    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(new LivroEletronico());

    boolean result = this.livroEletronicoService.livroAbertoExiste();

    assertTrue(result);
  }

  @Test
  void livro_aberto_nao_existe() {

    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(null);

    boolean result = this.livroEletronicoService.livroAbertoExiste();

    assertFalse(result);
  }

  @Test
  void criacao_livro() {

    LivroEletronico livro = this.livroEletronicoService.createLivro();

    assertNotNull(livro);
    assertEquals("Assistente Virtual", livro.getUsuarioResponsavel());
    assertEquals(SituacaoLivro.ABERTO, livro.getSituacao());
    assertNotNull(livro.getDataAbertura());
  }

  @Test
  void find_by_livro_aberto_livro_existe() {

    LivroEletronico livroAberto = new LivroEletronico();
    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(livroAberto);

    LivroEletronico result = this.livroEletronicoService.findByLivroAberto();

    assertNotNull(result);
    assertEquals(livroAberto, result);
  }

  @Test
  void find_by_livro_aberto_livro_nao_existe() {

    when(this.livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO)).thenReturn(null);

    assertThrows(NegocioException.class, () -> this.livroEletronicoService.findByLivroAberto());
  }

  @Test
  void should_calculate_pages_for_given_filters() {

    LivroEletronicoFilterRequestDto request = new LivroEletronicoFilterRequestDto();
    LivroEletronicoFilterResponseDto responseDto1 = new LivroEletronicoFilterResponseDto();
    responseDto1.setId(UUID.randomUUID().toString());
    LivroEletronicoFilterResponseDto responseDto2 = new LivroEletronicoFilterResponseDto();
    responseDto2.setId(UUID.randomUUID().toString());
    when(this.livroEletronicoDao.findByFilter(request)).thenReturn(List.of(responseDto1, responseDto2));
    when(this.registroLivroRepository.countByLivroEletronicoId(UUID.fromString(responseDto1.getId()))).thenReturn(100);
    when(this.registroLivroRepository.countByLivroEletronicoId(UUID.fromString(responseDto2.getId()))).thenReturn(200);

    List<LivroEletronicoFilterResponseDto> result = this.livroEletronicoService.findByFilter(request);

    assertEquals(2, result.get(0).getPaginas());
    assertEquals(4, result.get(1).getPaginas());
  }

  @Test
  void should_handle_no_records_for_given_filters() {

    LivroEletronicoFilterRequestDto request = new LivroEletronicoFilterRequestDto();
    LivroEletronicoFilterResponseDto responseDto = new LivroEletronicoFilterResponseDto();
    responseDto.setId(UUID.randomUUID().toString());
    when(this.livroEletronicoDao.findByFilter(request)).thenReturn(List.of(responseDto));
    when(this.registroLivroRepository.countByLivroEletronicoId(UUID.fromString(responseDto.getId()))).thenReturn(0);

    List<LivroEletronicoFilterResponseDto> result = this.livroEletronicoService.findByFilter(request);

    assertEquals(0, result.get(0).getPaginas());
  }

}
