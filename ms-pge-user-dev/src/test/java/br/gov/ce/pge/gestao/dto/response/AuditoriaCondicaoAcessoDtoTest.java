package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.CondicaoAcessoTest;
import br.gov.ce.pge.gestao.entity.ModuloTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuditoriaCondicaoAcessoDtoTest {
	
	public static AuditoriaDto getAuditoria() throws JsonProcessingException {
		var auditoria = new AuditoriaCondicaoAcessoDto();
		auditoria.setDadosAntigos(ModuloTest.getModulo().toStringMapper());
		auditoria.setDataMovimento(LocalDateTime.now().toString());
		auditoria.setDataMovimento(LocalDateTime.now().toString());
		auditoria.setNomeUsuario("anônimo");
		auditoria.setSituacao(Situacao.ATIVA);
		return auditoria;
	}

	public static String json() throws JsonProcessingException {
		return CondicaoAcessoTest.getCondicaoAcesso().toStringMapper();
	}

	@Test
	public void all_fiedls() throws JsonProcessingException {
		var dto = (AuditoriaCondicaoAcessoDto) getAuditoria();
		dto.setId("b6f0142e-4d04-413b-a521-b59177360a8e");

		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertNotNull(dto.equals(getAuditoria()));
		assertNotNull(dto.hashCode());
		assertEquals("b6f0142e-4d04-413b-a521-b59177360a8e", dto.getId());
	}
}
