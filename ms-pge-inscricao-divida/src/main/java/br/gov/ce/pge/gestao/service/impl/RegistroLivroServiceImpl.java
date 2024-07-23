package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dao.RegistroLivroDao;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.entity.RegistroLivro;
import br.gov.ce.pge.gestao.repository.RegistroLivroRepository;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import br.gov.ce.pge.gestao.shared.util.TotalPaginasLinhasUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroLivroServiceImpl implements RegistroLivroService {

	private final RegistroLivroRepository repository;

	private final LivroEletronicoService livroEletronicoService;

	private final RegistroLivroDao dao;


	public RegistroLivroServiceImpl(RegistroLivroRepository repository,
			LivroEletronicoService livroEletronicoService, RegistroLivroDao dao) {
		this.repository = repository;
		this.livroEletronicoService = livroEletronicoService;
		this.dao = dao;
	}

	@Override
	public void registrar(Divida divida) {
		LivroEletronico livro = livroEletronicoService.findByLivroAberto();

		int totalRegistros = livro.getRegistros().size();

		int totalPaginas = TotalPaginasLinhasUtil.getTotalPaginas(totalRegistros + SharedConstant.INCREMENTO);

		RegistroLivro registro = new RegistroLivro();
		registro.setDivida(divida);
		registro.setLivro(livro);
		registro.setLinha(totalRegistros + SharedConstant.INCREMENTO);
		registro.setPagina(totalPaginas);

		repository.save(registro);

	}

	@Override
	public PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> findByFilterRegistroInscricao(RegistroLivroFilterRequestDto request, Integer page, Long limit, String orderBy) {

		Integer totalRegistros = dao.countfindByFilter(request);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalRegistros, limit);

		Long offset = PaginacaoUtil.getOffset(page, limit);

		request.setOffset(offset);
		request.setLimit(limit);
		request.setOrderBy(orderBy);

		List<RegistroInscricaoResponseDto> registros = dao.findByFilterRegistroInscricao(request);

		return PaginacaoResponseDto.fromResultado(registros, totalRegistros, totalPaginas, page);
	}

}
