package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dao.RegistroLivroDao;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import br.gov.ce.pge.gestao.shared.util.TotalPaginasLinhasUtil;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.RegistroLivro;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;

import java.util.List;

@Service
public class RegistroLivroServiceImpl implements RegistroLivroService {

	private final RegistroLivroRepository repository;
	
	private final LivroEletronicoService livroEletronicoService;

	private final RegistroLivroDao dao;

	public RegistroLivroServiceImpl(RegistroLivroRepository repository,
			LivroEletronicoService livroEletronicoService,
			RegistroLivroDao dao) {
		this.repository = repository;
		this.livroEletronicoService = livroEletronicoService;
		this.dao = dao;
	}

	@Override
	public void registrar(Inscricao inscricao) {
		var livro = livroEletronicoService.findByLivroAberto();

		var totalRegistros = livro.getRegistros().size();

		var totalPaginas = TotalPaginasLinhasUtil.getTotalPaginas(totalRegistros);

		int pagina = totalPaginas == 0 ? 1 : totalPaginas;

		var registro = new RegistroLivro();
		registro.setInscricao(inscricao);
		registro.setLivro(livro);
		registro.setLinha(totalRegistros + SharedConstant.INCREMENTO);
		registro.setPagina(pagina);
		
		this.repository.save(registro);

	}

	@Override
	public PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> findByFilterRegistroInscricao(RegistroLivroFilterRequestDto request, Integer page, Long limit, String orderBy) {

		Integer totalRegistros = this.dao.countfindByFilter(request);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalRegistros, limit);

		Long offset = PaginacaoUtil.getOffset(page, limit);

		request.setOffset(offset);
		request.setLimit(limit);
		request.setOrderBy(orderBy);

		List<RegistroInscricaoResponseDto> registros = this.dao.findByFilterRegistroInscricao(request);

		return PaginacaoResponseDto.fromResultado(registros, totalRegistros, totalPaginas, page);
	}

}
