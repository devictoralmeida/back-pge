package br.gov.ce.pge.gestao.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import br.gov.ce.pge.gestao.repository.LivroEletronicoRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@Service
public class LivroEletronicoServiceImpl implements LivroEletronicoService {

	private static final String ASSISTENTE_VIRTUAL = "Assistente Virtual";
	private static final String MSG_ERRO_LIVRO_INEXISTENTE = "Não existe livro aberto";
	private static final String MSG_ERRO_LIVRO_ABERTO = "Já existe um livro aberto";
	private final LivroEletronicoRepository repository;

	public LivroEletronicoServiceImpl(LivroEletronicoRepository repository) {
		this.repository = repository;
	}

	@Scheduled(cron = "0 58 14 22 4 ?")
	private void abertura() {

		if (livroAbertoExiste()) {
			throw new NegocioException(MSG_ERRO_LIVRO_ABERTO);
		}

		this.repository.save(createLivro());

	}

	@Scheduled(cron = "0 59 14 22 4 ?")
	private void fechamento() {

		LivroEletronico livro = findByLivroAberto();

		livro.setDataFechamento(LocalDateTime.now());
		livro.setSituacao(SituacaoLivro.FECHADO);

		this.repository.save(livro);
	}

	private boolean livroAbertoExiste() {
		return repository.findBySituacao(SituacaoLivro.ABERTO) != null;
	}

	private LivroEletronico createLivro() {
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

}
