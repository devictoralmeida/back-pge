package br.gov.ce.pge.gestao.service;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;

@Service
public interface UsuarioService {

	void save(@Valid UsuarioRequestDto request, String nomeUsuario);

	void update(UUID id, @Valid UsuarioRequestDto request, String nomeUsuario) throws JsonProcessingException;

	void delete(UUID id);

	void bloquearUsuario(UUID id);

	void aceitarTermo(UUID id, String nomeSistema);

	void ultimoAcesso(UUID id);
}
