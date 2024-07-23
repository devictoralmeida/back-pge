package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dao.LivroEletronicoDao;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.entity.LivroEletronicoTest;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import br.gov.ce.pge.gestao.repository.LivroEletronicoRepository;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        livroEletronicoService = new LivroEletronicoServiceImpl(livroEletronicoRepository, registroLivroRepository, livroEletronicoDao);
    }

    @Test
    void abertura_livro_ok() {

        Integer anoAtual = LocalDate.now().getYear();

        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(null);

        livroEletronicoService.abertura();

        verify(livroEletronicoRepository).save(any());
    }

    @Test
    void abertura_livro_excecao() {

        Integer anoAtual = LocalDate.now().getYear();

        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(new LivroEletronico());

        assertThrows(NegocioException.class, () -> livroEletronicoService.abertura());
    }

    @Test
    void fechamento_livro_ok() {

        Integer anoAtual = LocalDate.now().getYear();

        LivroEletronico livroAberto = new LivroEletronico();
        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(livroAberto);

        livroEletronicoService.fechamento();

        verify(livroEletronicoRepository).save(livroAberto);
    }

    @Test
    void livro_aberto_existe() {

        Integer anoAtual = LocalDate.now().getYear();

        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(new LivroEletronico());

        boolean result = livroEletronicoService.livroAbertoExiste();

        assertTrue(result);
    }

    @Test
    void livro_aberto_nao_existe() {

        Integer anoAtual = LocalDate.now().getYear();

        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(null);

        boolean result = livroEletronicoService.livroAbertoExiste();

        assertFalse(result);
    }

    @Test
    void criacao_livro() {

        LivroEletronico livro = livroEletronicoService.createLivro();

        assertNotNull(livro);
        assertEquals("Assistente Virtual", livro.getUsuarioResponsavel());
        assertEquals(SituacaoLivro.ABERTO, livro.getSituacao());
        assertNotNull(livro.getDataAbertura());
    }

    @Test
    void find_by_livro_aberto_livro_existe() {

        Integer anoAtual = LocalDate.now().getYear();

        LivroEletronico livroAberto = new LivroEletronico();
        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(livroAberto);

        LivroEletronico result = livroEletronicoService.findByLivroAberto();

        assertNotNull(result);
        assertEquals(livroAberto, result);
    }

    @Test
    void find_by_livro_aberto_livro_nao_existe() {

        Integer anoAtual = LocalDate.now().getYear();

        when(livroEletronicoRepository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual))).thenReturn(null);

        assertThrows(NegocioException.class, () -> livroEletronicoService.findByLivroAberto());
    }

    @Test
    void should_calculate_pages_for_given_filters() {

        LivroEletronicoFilterRequestDto request = new LivroEletronicoFilterRequestDto();
        LivroEletronicoFilterResponseDto responseDto1 = new LivroEletronicoFilterResponseDto();
        responseDto1.setId(UUID.randomUUID().toString());
        LivroEletronicoFilterResponseDto responseDto2 = new LivroEletronicoFilterResponseDto();
        responseDto2.setId(UUID.randomUUID().toString());
        when(livroEletronicoDao.findByFilter(request)).thenReturn(List.of(responseDto1, responseDto2));
        when(registroLivroRepository.countByLivroEletronicoId(UUID.fromString(responseDto1.getId()))).thenReturn(100);
        when(registroLivroRepository.countByLivroEletronicoId(UUID.fromString(responseDto2.getId()))).thenReturn(200);

        List<LivroEletronicoFilterResponseDto> result = livroEletronicoService.findByFilter(request);

        assertEquals(2, result.get(0).getPaginas());
        assertEquals(4, result.get(1).getPaginas());
    }

    @Test
    void should_handle_no_records_for_given_filters() {

        LivroEletronicoFilterRequestDto request = new LivroEletronicoFilterRequestDto();
        LivroEletronicoFilterResponseDto responseDto = new LivroEletronicoFilterResponseDto();
        responseDto.setId(UUID.randomUUID().toString());
        when(livroEletronicoDao.findByFilter(request)).thenReturn(List.of(responseDto));
        when(registroLivroRepository.countByLivroEletronicoId(UUID.fromString(responseDto.getId()))).thenReturn(0);

        List<LivroEletronicoFilterResponseDto> result = livroEletronicoService.findByFilter(request);

        assertEquals(1, result.get(0).getPaginas());
    }

//    @Test
//    void find_by_id() {
//        when(registroLivroRepository.countByLivroEletronicoId(any())).thenReturn(1);
//        when(livroEletronicoRepository.findById(any())).thenReturn(Optional.of(LivroEletronicoTest.getLivro()));
//
//        LivroEletronicoResponseDto livro = livroEletronicoService.findById(any());
//
//        assertNotNull(livro);
//    }

    @Test
    void find_by_id_model_erro() {

        when(livroEletronicoRepository.findById(any())).thenReturn(Optional.empty());

        Exception erro = assertThrows(NegocioException.class, () -> {
            livroEletronicoService.findByIdModel(any());
        });

        assertEquals(MessageCommonsConstants.MSG_ERRO_LIVRO_INEXISTENTE, erro.getMessage());

        verify(livroEletronicoRepository, times(1)).findById(any());
    }

}