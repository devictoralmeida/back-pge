package br.gov.ce.pge.gestao.service.impl;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.RegistroLivro;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;

@Service
public class RegistroLivroServiceImpl implements RegistroLivroService {

	private final RegistroLivroRepository repository;
	
	private final LivroEletronicoService livroEletronicoService;

	public RegistroLivroServiceImpl(RegistroLivroRepository repository,
			LivroEletronicoService livroEletronicoService) {
		this.repository = repository;
		this.livroEletronicoService = livroEletronicoService;
	}

	@Override
	public void registrar(Inscricao inscricao) {
		var livro = livroEletronicoService.findByLivroAberto();
		
		var registro = new RegistroLivro();
		registro.setInscricao(inscricao);
		registro.setLivro(livro);
		
		this.repository.save(registro);

	}

}
