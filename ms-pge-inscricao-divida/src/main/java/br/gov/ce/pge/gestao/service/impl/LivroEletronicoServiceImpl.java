package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dao.LivroEletronicoDao;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import br.gov.ce.pge.gestao.repository.LivroEletronicoRepository;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
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

    public LivroEletronicoServiceImpl(LivroEletronicoRepository repository, RegistroLivroRepository registroLivroRepository, LivroEletronicoDao dao) {
        this.repository = repository;
        this.registroLivroRepository = registroLivroRepository;
        this.dao = dao;
    }

    @Override
    @Scheduled(cron = SharedConstant.HORARIO_INICIA_PROCESSO_ABERTURA_LIVRO)
    public void abertura() {

        if (livroAbertoExiste()) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_LIVRO_ABERTO);
        }

        repository.save(createLivro());

    }

    @Override
    @Scheduled(cron = SharedConstant.HORARIO_INICIA_PROCESSO_FECHAMENTO_LIVRO)
    public void fechamento() {

        LivroEletronico livro = findByLivroAberto();

        if(SituacaoLivro.FECHADO == livro.getSituacao()) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_LIVRO_JA_FECHADO);
        }

        livro.setDataFechamento(LocalDateTime.now());
        livro.setSituacao(SituacaoLivro.FECHADO);

        repository.save(livro);
    }

    public boolean livroAbertoExiste() {
        Integer anoAtual = LocalDate.now().getYear();
        return repository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual)) != null;
    }

    public LivroEletronico createLivro() {
        LivroEletronico livro = new LivroEletronico();
        livro.setNome(String.valueOf(LocalDateTime.now().getYear()));
        livro.setSituacao(SituacaoLivro.ABERTO);
        livro.setUsuarioResponsavel(MessageCommonsConstants.ASSISTENTE_VIRTUAL);
        livro.setDataAbertura(LocalDateTime.now());
        return livro;
    }

    @Override
    public LivroEletronico findByLivroAberto() {
        Integer anoAtual = LocalDate.now().getYear();
        LivroEletronico livro = repository.findBySituacao(SituacaoLivro.ABERTO.toString(), String.valueOf(anoAtual));

        if (livro == null) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_LIVRO_INEXISTENTE);
        }

        return livro;
    }

    @Override
    public List<LivroEletronicoFilterResponseDto> findByFilter(LivroEletronicoFilterRequestDto request) {
        if (request.getCnpj() != null) {
            verificaTamanhoFiltroCnpj(request.getCnpj());
        }

        List<LivroEletronicoFilterResponseDto> filters = dao.findByFilter(request);

        for (LivroEletronicoFilterResponseDto livro : filters) {
            UUID idLivro = UUID.fromString(livro.getId());
            Integer totalRegistrosLivro = registroLivroRepository.countByLivroEletronicoId(idLivro);
            int totalPaginas = totalRegistrosLivro > SharedConstant.SOMA_INICIO ? TotalPaginasLinhasUtil.getTotalPaginas(totalRegistrosLivro) : SharedConstant.INCREMENTO;
            livro.setPaginas(totalPaginas);
        }

        return filters;
    }

    @Override
    public LivroEletronicoResponseDto findById(UUID id) {

        Integer totalRegistrosLivro = registroLivroRepository.countByLivroEletronicoId(id);

        LivroEletronicoResponseDto livroResponseDto = new LivroEletronicoResponseDto();

        LivroEletronico livro = findByIdModel(id);

        BeanUtils.copyProperties(livro, livroResponseDto);

        livroResponseDto.setPaginas(TotalPaginasLinhasUtil.getTotalPaginas(totalRegistrosLivro));

        livroResponseDto.setTotalLinhasUltimaPagina(TotalPaginasLinhasUtil.getRestoDivisao(totalRegistrosLivro));

        return livroResponseDto;
    }

    @Override
    public LivroEletronico findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_LIVRO_INEXISTENTE));
    }

    private void verificaTamanhoFiltroCnpj(String cnpj) {
        if (cnpj.length() > SharedConstant.TAMANHO_MINIMO_CNPJ_INCOMPLETO && cnpj.length() < SharedConstant.TAMANHO_MAXIMO_CNPJ) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_TAMANHO_CNPJ_FILTRO_INVALIDO);
        }
    }
}
