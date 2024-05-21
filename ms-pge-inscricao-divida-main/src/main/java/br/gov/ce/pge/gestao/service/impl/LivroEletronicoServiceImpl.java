package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dao.LivroEletronicoDao;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import br.gov.ce.pge.gestao.repository.LivroEletronicoRepository;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.TotalPaginasLinhasUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LivroEletronicoServiceImpl implements LivroEletronicoService {

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

  @Scheduled(cron = "0 0 0 1 1 ?")
  public void abertura() {

    if (livroAbertoExiste()) {
      throw new NegocioException(MessageCommonsContanst.MSG_ERRO_LIVRO_ABERTO);
    }

    repository.save(createLivro());

  }

  @Scheduled(cron = "0 59 23 31 12 ?")
  public void fechamento() {

    LivroEletronico livro = findByLivroAberto();

    livro.setDataFechamento(LocalDateTime.now());
    livro.setSituacao(SituacaoLivro.FECHADO);

    repository.save(livro);
  }

  public boolean livroAbertoExiste() {
    return repository.findBySituacao(SituacaoLivro.ABERTO) != null;
  }

  public LivroEletronico createLivro() {

    var livro = new LivroEletronico();

    livro.setNome(String.valueOf(LocalDate.now().getYear()));

    livro.setSituacao(SituacaoLivro.ABERTO);

    livro.setUsuarioResponsavel(MessageCommonsContanst.ASSISTENTE_VIRTUAL);

    livro.setDataAbertura(LocalDateTime.now());
    
    return livro;
  }

  @Override

  public LivroEletronico findByLivroAberto() {
    LivroEletronico livro = repository.findBySituacao(SituacaoLivro.ABERTO);

    if (livro == null) {
      throw new NegocioException(MessageCommonsContanst.MSG_ERRO_LIVRO_INEXISTENTE);
    }

    return livro;
  }

  @Override
  public List<LivroEletronicoFilterResponseDto> findByFilter(LivroEletronicoFilterRequestDto request) {
    List<LivroEletronicoFilterResponseDto> filters = dao.findByFilter(request);

    for (LivroEletronicoFilterResponseDto livro : filters) {

      UUID idLivro = UUID.fromString(livro.getId());

      Integer totalRegistrosLivro = registroLivroRepository.countByLivroEletronicoId(idLivro);

      var totalPaginas = TotalPaginasLinhasUtil.getTotalPaginas(totalRegistrosLivro);

      livro.setPaginas(totalPaginas);
    }

    return filters;
  }

  @Override
  public LivroEletronicoResponseDto findById(UUID id) {

    Integer totalRegistrosLivro = registroLivroRepository.countByLivroEletronicoId(id);

    var livroResponseDto = new LivroEletronicoResponseDto();

    var livro = findByIdModel(id);

    BeanUtils.copyProperties(livro, livroResponseDto);

    livroResponseDto.setPaginas(TotalPaginasLinhasUtil.getTotalPaginas(totalRegistrosLivro));

    livroResponseDto.setTotalLinhasUltimaPagina(TotalPaginasLinhasUtil.getRestoDivisao(totalRegistrosLivro));

    return livroResponseDto;
  }

  @Override
  public LivroEletronico findByIdModel(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsContanst.MSG_ERRO_LIVRO_INEXISTENTE));
  }


}
