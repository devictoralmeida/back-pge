package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dao.RegistroLivroDao;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDtoTest;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.LivroEletronicoTest;
import br.gov.ce.pge.gestao.entity.RegistroLivro;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroLivroServiceImplTest {
  @Mock
  private RegistroLivroRepository repository;

  @Mock
  private LivroEletronicoService livroEletronicoService;

  @InjectMocks
  private RegistroLivroServiceImpl registroLivroService;

  @Mock
  private RegistroLivroDao dao;

  @Test
  void teste_registrar_ok() {

    when(livroEletronicoService.findByLivroAberto()).thenReturn(LivroEletronicoTest.getLivro());

    Inscricao inscricao = new Inscricao();

    registroLivroService.registrar(inscricao);

    verify(repository, times(1)).save(any(RegistroLivro.class));
  }

  @Test
  void teste_registrar_sem_registros_ok() {

    when(livroEletronicoService.findByLivroAberto()).thenReturn(LivroEletronicoTest.getLivroSemRegistro());

    Inscricao inscricao = new Inscricao();

    registroLivroService.registrar(inscricao);

    verify(repository, times(1)).save(any(RegistroLivro.class));
  }

  @Test
  void find_by_filter_pagina() {
    when(dao.countfindByFilter(any())).thenReturn(1);
    when(dao.findByFilterRegistroInscricao(any())).thenReturn(Arrays.asList(RegistroInscricaoResponseDtoTest.getRegistroInscricaoPagina()));

    var filter = registroLivroService.findByFilterRegistroInscricao(RegistroLivroFilterRequestDtoTest.getRequestFilterPagina(), 1, 1L, "pagina");

    asserts(filter);
  }

  @Test
  void find_by_filter_linha() {
    when(dao.countfindByFilter(any())).thenReturn(1);
    when(dao.findByFilterRegistroInscricao(any())).thenReturn(Arrays.asList(RegistroInscricaoResponseDtoTest.getRegistroInscricaoLinha()));

    var filter = registroLivroService.findByFilterRegistroInscricao(RegistroLivroFilterRequestDtoTest.getRequestFilterLinha(), 1, 1L, "linha");

    asserts(filter);
  }

  private void asserts(PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> filter) {
    assertNotNull(filter);
    assertNotNull(filter.getList());
  }

}
