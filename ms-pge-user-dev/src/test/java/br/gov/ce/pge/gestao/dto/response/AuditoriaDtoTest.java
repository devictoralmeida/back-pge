package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.entity.CondicaoAcessoTest;
import br.gov.ce.pge.gestao.entity.PerfilAcessoTest;
import br.gov.ce.pge.gestao.entity.SistemaTest;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.enums.Situacao;

public class AuditoriaDtoTest {

    private AuditoriaModuloDto auditoriaDto;
    
    
    public static AuditoriaModuloDto getAuditoriaModuloDto_dadosAntigoNull() {
    	var auditoria = new AuditoriaModuloDto();
    	auditoria.setDadosAntigos(null);
    	return auditoria;
    }
    
    public static AuditoriaPerfilAcessoDto getAuditoriaPerfilDto_dadosAntigoNull() {
    	var auditoria = new AuditoriaPerfilAcessoDto();
    	auditoria.setDadosAntigos(null);
    	return auditoria;
    }
    
    public static AuditoriaPermissaoDto getAuditoriaPermissaoDto_dadosAntigoNull() {
    	var auditoria = new AuditoriaPermissaoDto();
    	auditoria.setDadosAntigos(null);
    	return auditoria;
    }
    
    public static AuditoriaSistemaDto getAuditoriaSistemaDto_dadosAntigoNull() {
    	var auditoria = new AuditoriaSistemaDto();
    	auditoria.setDadosAntigos(null);
    	return auditoria;
    }
    
    public static AuditoriaUsuarioDto getAuditoriaUsuarioDto_dadosAntigoNull() {
    	var auditoria = new AuditoriaUsuarioDto();
    	auditoria.setDadosAntigos(null);
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	return auditoria;
    }

	public static AuditoriaCondicaoAcessoDto getAuditoriaCondicaoAcessoDto_dadosAntigoNull() {
		var auditoria = new AuditoriaCondicaoAcessoDto();
		auditoria.setDadosAntigos(null);
		return auditoria;
	}
    
    public static AuditoriaUsuarioDto getAuditoriaUsuarioDto_idsAdicionados_perfis() throws JsonProcessingException {
    	var auditoria = new AuditoriaUsuarioDto();
    	auditoria.setDadosAntigos(UsuarioTest.getUsuario().toStringMapper());
    	auditoria.setNome(UsuarioTest.getUsuario().getNome());
    	auditoria.setCpf(UsuarioTest.getUsuario().getCpf());
    	auditoria.setEmail(UsuarioTest.getUsuario().getEmail());
    	auditoria.setArea(UsuarioTest.getUsuario().getArea());
    	auditoria.setOrgao(UsuarioTest.getUsuario().getOrgao());
    	auditoria.setSituacaoUsuario(UsuarioTest.getUsuario().getSituacao());
    	auditoria.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());
    	auditoria.setUsuarioRede(UsuarioTest.getUsuario().getUsuarioRede());
    	auditoria.setIdsPerfisAdicionados("5027c7f7-622b-4161-ac75-97c9110553f2");
    	auditoria.setIdsSistemaAdicionados(null);
    	auditoria.setIdsSistemaRemovidos(null);
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	return auditoria;
    }
    
    public static AuditoriaUsuarioDto getAuditoriaUsuarioDto_idsRemovidos_perfis() throws JsonProcessingException {
    	var auditoria = new AuditoriaUsuarioDto();
    	auditoria.setDadosAntigos(UsuarioTest.getUsuario().toStringMapper());
    	auditoria.setNome(UsuarioTest.getUsuario().getNome());
    	auditoria.setCpf(UsuarioTest.getUsuario().getCpf());
    	auditoria.setEmail(UsuarioTest.getUsuario().getEmail());
    	auditoria.setArea(UsuarioTest.getUsuario().getArea());
    	auditoria.setOrgao(UsuarioTest.getUsuario().getOrgao());
    	auditoria.setSituacaoUsuario(UsuarioTest.getUsuario().getSituacao());
    	auditoria.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());
    	auditoria.setUsuarioRede(UsuarioTest.getUsuario().getUsuarioRede());
    	auditoria.setIdsPerfisAdicionados("5027c7f7-622b-4161-ac75-97c9110553f2");
    	auditoria.setIdsPerfisRemovidos("c4095434-f704-4209-be74-3d42d519d438");
    	auditoria.setIdsSistemaAdicionados("");
    	auditoria.setIdsSistemaRemovidos("");
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	return auditoria;
    }
    
    public static AuditoriaUsuarioDto getAuditoriaUsuarioDto_idsAdicionados_sistema() throws JsonProcessingException {
    	var auditoria = new AuditoriaUsuarioDto();
    	auditoria.setDadosAntigos(UsuarioTest.getUsuario().toStringMapper());
    	auditoria.setNome(UsuarioTest.getUsuario().getNome());
    	auditoria.setCpf(UsuarioTest.getUsuario().getCpf());
    	auditoria.setEmail(UsuarioTest.getUsuario().getEmail());
    	auditoria.setArea(UsuarioTest.getUsuario().getArea());
    	auditoria.setOrgao(UsuarioTest.getUsuario().getOrgao());
    	auditoria.setSituacaoUsuario(UsuarioTest.getUsuario().getSituacao());
    	auditoria.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());
    	auditoria.setUsuarioRede(UsuarioTest.getUsuario().getUsuarioRede());
    	auditoria.setIdsPerfisAdicionados(null);
    	auditoria.setIdsPerfisRemovidos(null);
    	auditoria.setIdsSistemaAdicionados("c4095434-f704-4209-be74-3d42d519d438");
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	return auditoria;
    }
    
    public static AuditoriaUsuarioDto getAuditoriaUsuarioDto_idsRemovidos_sistema() throws JsonProcessingException {
    	var auditoria = new AuditoriaUsuarioDto();
    	auditoria.setDadosAntigos(UsuarioTest.getUsuario().toStringMapper());
    	auditoria.setNome(UsuarioTest.getUsuario().getNome());
    	auditoria.setCpf(UsuarioTest.getUsuario().getCpf());
    	auditoria.setEmail(UsuarioTest.getUsuario().getEmail());
    	auditoria.setArea(UsuarioTest.getUsuario().getArea());
    	auditoria.setOrgao(UsuarioTest.getUsuario().getOrgao());
    	auditoria.setSituacaoUsuario(UsuarioTest.getUsuario().getSituacao());
    	auditoria.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());
    	auditoria.setUsuarioRede(UsuarioTest.getUsuario().getUsuarioRede());
    	auditoria.setIdsPerfisAdicionados("");
    	auditoria.setIdsPerfisRemovidos("");
    	auditoria.setIdsSistemaRemovidos("f08cc600-1268-4e35-8279-63ecef41454b");
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	return auditoria;
    }
    
    public static AuditoriaPerfilAcessoDto getAuditoriaPerfilDto_idsAdicionados_sistema() throws JsonProcessingException {
    	var auditoria = new AuditoriaPerfilAcessoDto();
    	auditoria.setDadosAntigos(PerfilAcessoTest.getPerfilAcesso().toStringMapper());
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	auditoria.setNome(PerfilAcessoTest.getPerfilAcesso().getNome());
    	auditoria.setSituacao(PerfilAcessoTest.getPerfilAcesso().getSituacao());
    	auditoria.setIdsSistemaAdicionados("f08cc600-1268-4e35-8279-63ecef41454b");
    	auditoria.setIdsSistemaRemovidos(null);
    	
    	auditoria.setIdsAdicionados(null);
    	auditoria.setIdsRemovidos(null);
    	return auditoria;
    }
    
    public static AuditoriaPerfilAcessoDto getAuditoriaPerfilDto_idsRemovidos_sistema() throws JsonProcessingException {
    	var auditoria = new AuditoriaPerfilAcessoDto();
    	auditoria.setDadosAntigos(PerfilAcessoTest.getPerfilAcesso().toStringMapper());
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	auditoria.setNome(PerfilAcessoTest.getPerfilAcesso().getNome());
    	auditoria.setSituacao(PerfilAcessoTest.getPerfilAcesso().getSituacao());
    	auditoria.setIdsSistemaAdicionados("");
    	auditoria.setIdsSistemaRemovidos("f08cc600-1268-4e35-8279-63ecef41454b");
    	
    	auditoria.setIdsAdicionados("");
    	auditoria.setIdsRemovidos("");
    	return auditoria;
    }
    
    
    public static AuditoriaPerfilAcessoDto getAuditoriaPerfilDto_idsAdicionados_permissao() throws JsonProcessingException {
    	var auditoria = new AuditoriaPerfilAcessoDto();
    	auditoria.setDadosAntigos(PerfilAcessoTest.getPerfilAcesso().toStringMapper());
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	auditoria.setNome(PerfilAcessoTest.getPerfilAcesso().getNome());
    	auditoria.setSituacao(PerfilAcessoTest.getPerfilAcesso().getSituacao());
    	auditoria.setIdsSistemaAdicionados(null);
    	auditoria.setIdsSistemaRemovidos(null);
    	
    	auditoria.setIdsAdicionados("f08cc600-1268-4e35-8279-63ecef41454b");
    	auditoria.setIdsRemovidos(null);
    	return auditoria;
    }
    
    public static AuditoriaPerfilAcessoDto getAuditoriaPerfilDto_idsRemovidos_permissao() throws JsonProcessingException {
    	var auditoria = new AuditoriaPerfilAcessoDto();
    	auditoria.setDadosAntigos(PerfilAcessoTest.getPerfilAcesso().toStringMapper());
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	auditoria.setNome(PerfilAcessoTest.getPerfilAcesso().getNome());
    	auditoria.setSituacao(PerfilAcessoTest.getPerfilAcesso().getSituacao());
    	auditoria.setIdsSistemaAdicionados("");
    	auditoria.setIdsSistemaRemovidos("");
    	
    	auditoria.setIdsAdicionados("");
    	auditoria.setIdsRemovidos("f08cc600-1268-4e35-8279-63ecef41454b");
    	return auditoria;
    }
    
    public static AuditoriaSistemaDto getAuditoriaSistemaDto_idsAdicionados() throws JsonProcessingException {
    	var auditoria = new AuditoriaSistemaDto();
    	auditoria.setDadosAntigos(SistemaTest.getSistema().toStringMapper());
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	auditoria.setNome(SistemaTest.getSistema().getNome());
    	auditoria.setIdsAdicionados("f08cc600-1268-4e35-8279-63ecef41454b");
    	auditoria.setIdsRemovidos(null);
    	return auditoria;
    }
    
    public static AuditoriaSistemaDto getAuditoriaSistemaDto_idsRemovidos() throws JsonProcessingException {
    	var auditoria = new AuditoriaSistemaDto();
    	auditoria.setDadosAntigos(SistemaTest.getSistema().toStringMapper());
    	auditoria.setDataMovimento(LocalDateTime.now().toString());
    	auditoria.setNome(SistemaTest.getSistema().getNome());
    	auditoria.setIdsAdicionados(null);
    	auditoria.setIdsRemovidos("f08cc600-1268-4e35-8279-63ecef41454b");
    	return auditoria;
    }

	public static AuditoriaCondicaoAcessoDto getAuditoriaCondicaoAcessoDto_dadosAntigos() throws JsonProcessingException {
		var auditoria = new AuditoriaCondicaoAcessoDto();
		auditoria.setDadosAntigos(CondicaoAcessoTest.getCondicaoAcesso().toStringMapper());
		auditoria.setDataMovimento(LocalDateTime.now().toString());
		auditoria.setEncerramentoSessao(CondicaoAcessoTest.getCondicaoAcesso().getEncerramentoSessao());
		auditoria.setSenhasCadastradas(CondicaoAcessoTest.getCondicaoAcesso().getSenhasCadastradas());
		auditoria.setTentativasInvalidas(CondicaoAcessoTest.getCondicaoAcesso().getTentativasInvalidas());
		auditoria.setBloqueioAutomatico(CondicaoAcessoTest.getCondicaoAcesso().getBloqueioAutomatico());
		auditoria.setAlteracaoSenha(CondicaoAcessoTest.getCondicaoAcesso().getAlteracaoSenha());
		return auditoria;
	}

    @BeforeEach
    public void setUp() {
        auditoriaDto = new AuditoriaModuloDto();
        auditoriaDto.setDataMovimento("2024-01-31");
        auditoriaDto.setNomeUsuario("TestUser");
        auditoriaDto.setDadosAntigos("OldData");
        auditoriaDto.setSituacao(Situacao.ATIVA);
        auditoriaDto.setDescricao("TestDescription");
    }

    @Test
    public void test_data_movimento() {
        assertEquals("2024-01-31", auditoriaDto.getDataMovimento());
    }

    @Test
    public void test_nome_usuario() {
        assertEquals("TestUser", auditoriaDto.getNomeUsuario());
    }

    @Test
    public void test_dados_antigos() {
        assertEquals("OldData", auditoriaDto.getDadosAntigos());
    }

    @Test
    public void test_situacao() {
        assertEquals("ATIVA", auditoriaDto.getSituacao().toString());
    }

    @Test
    public void test_descricao() {
        assertEquals("TestDescription", auditoriaDto.getDescricao());
    }

}
