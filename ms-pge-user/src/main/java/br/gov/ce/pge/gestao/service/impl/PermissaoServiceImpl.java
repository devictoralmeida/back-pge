package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessagePerfilAcessoConstant;
import br.gov.ce.pge.gestao.dao.PermissaoDao;
import br.gov.ce.pge.gestao.dto.request.PermissaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.entity.Permissao;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.repository.PermissaoRepository;
import br.gov.ce.pge.gestao.service.PermissaoService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
@Qualifier("permissaoServiceImpl")
public class PermissaoServiceImpl implements PermissaoService {

	private final PermissaoRepository repository;

	private final PermissaoDao permissaoDao;

	public PermissaoServiceImpl(PermissaoRepository repository, PermissaoDao permissaoDao) {
		this.repository = repository;
		this.permissaoDao = permissaoDao;
	}

	@Override
	public Permissao save(@Valid PermissaoRequestDto request) {
		var permissaoModel = new Permissao();
		BeanUtils.copyProperties(request, permissaoModel);
		permissaoModel = this.repository.save(permissaoModel);
		return permissaoModel;
	}

	@Override
	public Permissao update(UUID id, @Valid PermissaoRequestDto request) throws JsonProcessingException{
		var permissaoModel = this.findById(id);
		CustomRevisionListener.setDadosAntigos(permissaoModel.toStringMapper());
		BeanUtils.copyProperties(request, permissaoModel);
		permissaoModel = this.repository.save(permissaoModel);
		return permissaoModel;
	}

	@Override
	public Permissao findById(UUID id) {
		return this.repository.findById(id).orElseThrow(() -> new NegocioException(MessagePerfilAcessoConstant.PERMISSAO_NAO_ENCONTRADO));
	}

	@Override
	public List<Permissao> findAll() {
		return this.repository.findAll();
	}

	@Override
	public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer tamanho) {
		Integer totalHistorys = this.permissaoDao.countHistorysUpdates(id);

		Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalHistorys, tamanho);

		Long offset = PaginacaoUtil.getOffset(page, tamanho);

		List<AuditoriaDto> historysUpdates = this.permissaoDao.findHistorysUpdates(id, offset, tamanho);

		return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(historysUpdates, "historico-permissao"), totalHistorys, totalPaginas, page);

	}

	@Override
	public void delete(UUID id) {
		var permissaoModel = this.findById(id);
		this.repository.delete(permissaoModel);
	}

}
