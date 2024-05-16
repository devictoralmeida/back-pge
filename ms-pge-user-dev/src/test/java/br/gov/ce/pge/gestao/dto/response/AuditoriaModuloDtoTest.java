package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.entity.ModuloTest;
import br.gov.ce.pge.gestao.enums.Situacao;

public class AuditoriaModuloDtoTest {

	public static AuditoriaDto getAuditoria() throws JsonProcessingException{
		var auditoria = new AuditoriaModuloDto();
		auditoria.setDadosAntigos(ModuloTest.getModulo().toStringMapper());
		auditoria.setDataMovimento(LocalDateTime.now().toString());
		auditoria.setDataMovimento(LocalDateTime.now().toString());
		auditoria.setNomeUsuario("anônimo");
		auditoria.setSituacao(Situacao.ATIVA);
		return auditoria;
	}
	
	public static String json() throws JsonProcessingException{
		return ModuloTest.getModulo().toStringMapper();
	}
	
	@Test
	public void all_fiedls() throws JsonProcessingException {
		var dto = (AuditoriaModuloDto) getAuditoria();
		dto.setId("b6f0142e-4d04-413b-a521-b59177360a8e");
		
		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertNotNull(dto.equals(getAuditoria()));
		assertNotNull(dto.hashCode());
		assertEquals("b6f0142e-4d04-413b-a521-b59177360a8e", dto.getId());
	}
}
