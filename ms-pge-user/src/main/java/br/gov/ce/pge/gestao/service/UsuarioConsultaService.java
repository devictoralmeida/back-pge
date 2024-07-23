package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.UsuarioFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.UsuarioResponseDto;
import br.gov.ce.pge.gestao.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UsuarioConsultaService {

	UsuarioResponseDto findById(UUID id);

	Usuario findByIdModel(UUID id);

	Usuario findByCpf(String cpf);

	List<Usuario> findAll();

	PaginacaoResponseDto<List<UsuarioFilterResponseDto>> findByFilter(UsuarioFilterRequestDto request, Integer page, String orderBy, Integer size);

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer size);

	Usuario findByEmail(String email);

    Usuario findByIdentificador(String identificador);
}
