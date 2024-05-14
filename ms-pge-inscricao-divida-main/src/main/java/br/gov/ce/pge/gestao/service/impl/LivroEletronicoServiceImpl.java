package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dao.LivroEletronicoDao;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import br.gov.ce.pge.gestao.repository.LivroEletronicoRepository;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LivroEletronicoServiceImpl implements LivroEletronicoService {
  private static final String ASSISTENTE_VIRTUAL = "Assistente Virtual";
  private static final String MSG_ERRO_LIVRO_INEXISTENTE = "Não existe livro aberto";
  private static final String MSG_ERRO_LIVRO_ABERTO = "Já existe um livro aberto";
  private final LivroEletronicoRepository repository;
  private final RegistroLivroRepository registroLivroRepository;
  private final LivroEletronicoDao dao;
  private final OrigemDebitoService origemDebitoService;

  public LivroEletronicoServiceImpl(LivroEletronicoRepository repository, RegistroLivroRepository registroLivroRepository, LivroEletronicoDao dao, OrigemDebitoService origemDebitoService) {
    this.repository = repository;
    this.registroLivroRepository = registroLivroRepository;
    this.dao = dao;
    this.origemDebitoService = origemDebitoService;
  }

  @Scheduled(cron = "0 39 14 30 4 ?")
  public void abertura() {

    if (this.livroAbertoExiste()) {
      throw new NegocioException(MSG_ERRO_LIVRO_ABERTO);
    }

    this.repository.save(this.createLivro());

  }

  @Scheduled(cron = "0 59 23 31 12 ?")
  public void fechamento() {

    LivroEletronico livro = this.findByLivroAberto();

    livro.setDataFechamento(LocalDateTime.now());
    livro.setSituacao(SituacaoLivro.FECHADO);

    this.repository.save(livro);
  }

  public boolean livroAbertoExiste() {
    return this.repository.findBySituacao(SituacaoLivro.ABERTO) != null;
  }

  public LivroEletronico createLivro() {
    var livro = new LivroEletronico();
    livro.setNome(String.valueOf(LocalDate.now().getYear()));
    livro.setSituacao(SituacaoLivro.ABERTO);
    livro.setUsuarioResponsavel(ASSISTENTE_VIRTUAL);
    livro.setDataAbertura(LocalDateTime.now());
    return livro;
  }

  @Override

  public LivroEletronico findByLivroAberto() {
    LivroEletronico livro = this.repository.findBySituacao(SituacaoLivro.ABERTO);

    if (livro == null) {
      throw new NegocioException(MSG_ERRO_LIVRO_INEXISTENTE);
    }

    return livro;
  }

  @Override
  public List<LivroEletronicoFilterResponseDto> findByFilter(LivroEletronicoFilterRequestDto request) {
    List<LivroEletronicoFilterResponseDto> filters = this.dao.findByFilter(request);

    for (LivroEletronicoFilterResponseDto livro : filters) {
      UUID idLivro = UUID.fromString(livro.getId());
      Integer totalRegistrosLivro = this.registroLivroRepository.countByLivroEletronicoId(idLivro);
      livro.setPaginas((int) Math.ceil((double) totalRegistrosLivro / 50));
    }

    return filters;
  }

}
