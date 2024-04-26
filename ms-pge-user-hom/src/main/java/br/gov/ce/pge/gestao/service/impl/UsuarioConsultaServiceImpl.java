package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dao.UsuarioDao;
import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.UsuarioFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.UsuarioResponseDto;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;

@Service
public class UsuarioConsultaServiceImpl implements UsuarioConsultaService {

	private static final String SEM_HISTORICO = "O usuário selecionado não possui histórico de edições.";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

	private static final long ITENS_POR_PAGINA = 10;

	private final UsuarioRepository repository;

	private final UsuarioDao usuarioDao;
	
	public UsuarioConsultaServiceImpl(UsuarioRepository repository, UsuarioDao usuarioDao) {
		this.repository = repository;
		this.usuarioDao = usuarioDao;
	}

	@Override
	public UsuarioResponseDto findById(UUID id) {

		var usuarioResponse = new UsuarioResponseDto();

		var usuario	= this.findByIdModel(id);

		BeanUtils.copyProperties(usuario, usuarioResponse);

		return usuarioResponse;
	}

	@Override
	public Usuario findByIdModel(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(USUARIO_NAO_ENCONTRADO));
	}

	@Override
	public List<Usuario> findAll() {
		return this.repository.findAll();
	}

	@Override
	public Usuario findByCpf(String cpf) {
		return this.repository.findByCpf(cpf);
	}

	@Override
	public Usuario findByEmail(String email) {
		return this.repository.findByEmail(email);
	}

	@Override
	public Usuario findByIdentificador(String identificador) {
		return this.repository.findByIdentificador(identificador);
	}

	@Override
	public PaginacaoResponseDto<List<UsuarioFilterResponseDto>> findByFilter(UsuarioFilterRequestDto request, Integer page, String orderBy) {

		Integer totalRegistros = this.usuarioDao.countfindByFilter(request);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalRegistros, ITENS_POR_PAGINA);
		Long offset = PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA);

		request.setOffset(offset);
		request.setLimit(ITENS_POR_PAGINA);
		request.setOrderBy(orderBy);

		var filters =  this.usuarioDao.findByFilter(request);

		return PaginacaoResponseDto.fromResultado(filters, totalRegistros, totalPaginas, page);
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page) {
		Integer totalHistorys = this.usuarioDao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, ITENS_POR_PAGINA);

		Long offset = PaginacaoUtil.getOffset(page, ITENS_POR_PAGINA);

		List<AuditoriaDto> historysUpdates =  this.usuarioDao.findHistorysUpdates(id, offset, ITENS_POR_PAGINA);

		if(historysUpdates == null || historysUpdates.isEmpty()) throw new HistoricoAtualizacaoNotFoundException(SEM_HISTORICO);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates, "historico-usuario"), totalHistorys, totalPaginas, page);
	}

}
