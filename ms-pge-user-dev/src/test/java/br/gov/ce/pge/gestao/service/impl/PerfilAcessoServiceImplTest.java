package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dao.PerfilAcessoDao;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDtoTest;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import br.gov.ce.pge.gestao.entity.PerfilAcessoTest;
import br.gov.ce.pge.gestao.entity.PermissaoTest;
import br.gov.ce.pge.gestao.entity.SistemaTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.repository.PerfilAcessoRepository;
import br.gov.ce.pge.gestao.service.PermissaoService;
import br.gov.ce.pge.gestao.service.SistemaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
class PerfilAcessoServiceImplTest {

	@Mock
	private PerfilAcessoRepository repository;
	
	@Mock
	private SistemaService sistemaService;
	
	@Mock
	private PermissaoService permissaoService;
	
	@Mock
	private PerfilAcessoDao perfilAcessoDao;
	
	@InjectMocks
	@Autowired
	private PerfilAcessoServiceImpl service;

	@Test
	public void save_sucesso() {
		when(this.repository.findByNome(any())).thenReturn(List.of());
		when(this.sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
		when(this.permissaoService.findById(any())).thenReturn(PermissaoTest.getPermissao());
		when(this.repository.save(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());
		
		var model = service.save(PerfilAcessoRequestDtoTest.getRequest());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "ADMIN");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getSistemas().size(), 1);
		assertEquals(model.getPermissoes().size(), 1);
		
		verify(repository, times(1)).findByNome(any());
		verify(repository, times(1)).save(any(PerfilAcesso.class));
	}
	
	@Test
	public void update_sucesso() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		when(this.sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
		when(this.permissaoService.findById(any())).thenReturn(PermissaoTest.getPermissao());
		when(this.repository.save(any())).thenReturn(PerfilAcessoTest.getPerfilAcessoUpdate());
		
		var model = service.update(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),PerfilAcessoRequestDtoTest.getRequestUpdate());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "ADMIN");
		assertEquals(model.getSituacao(), Situacao.INATIVA);
		assertEquals(model.getSistemas().size(), 1);
		assertEquals(model.getPermissoes().size(), 1);
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(PerfilAcesso.class));
	}
	
	@Test
	public void update_sucesso_objetos_iguais() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		
		var model = service.update(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),PerfilAcessoRequestDtoTest.getRequest());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "ADMIN");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getSistemas().size(), 1);
		assertEquals(model.getPermissoes().size(), 1);
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(0)).save(any(PerfilAcesso.class));
	}
	
	@Test
	public void update_sucesso_com_nome_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		when(this.sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
		when(this.permissaoService.findById(any())).thenReturn(PermissaoTest.getPermissao());
		when(this.repository.save(any())).thenReturn(PerfilAcessoTest.getPerfilAcessoUpdate_com_outro_nome());
		
		var model = service.update(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),PerfilAcessoRequestDtoTest.getRequestUpdate_com_outro_nome());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "ADMIN update");
		assertEquals(model.getSituacao(), Situacao.INATIVA);
		assertEquals(model.getSistemas().size(), 1);
		assertEquals(model.getPermissoes().size(), 1);
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(PerfilAcesso.class));
	}
	
	
	@Test
	public void findAll_sucesso() {
		when(this.repository.findAll()).thenReturn(PerfilAcessoTest.getListaPerfilAcesso());
		
		var model = service.findAll();
		
		assertNotNull(model);
		assertNotNull(model.get(0).getId());
		assertEquals(model.get(0).getNome(), "ADMIN");
		assertEquals(model.get(0).getSituacao(), Situacao.ATIVA);
		assertEquals(model.get(0).getSistemas().size(), 1);
		assertEquals(model.get(0).getPermissoes().size(), 1);
		
		verify(repository, times(1)).findAll();
	}
	
	
	@Test
	public void findBy_idModel_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		
		var model = service.findByIdModel(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "ADMIN");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getSistemas().size(), 1);
		assertEquals(model.getPermissoes().size(), 1);
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	public void findById_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		
		var model = service.findById(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "ADMIN");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	public void delete_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		
		service.delete(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
		
		verify(repository, times(1)).findById(any());
	}

	@Test
	void delete_error() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.of(new PerfilAcesso()));

		doThrow(new RuntimeException("Não foi possível realizar a exclusão! Mensagem específica da exceção esperada.")).when(repository).delete(any(PerfilAcesso.class));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.delete(idToDelete);
		});

		assertEquals("Não foi possível realizar a exclusão! Mensagem específica da exceção esperada.", error.getMessage());

		verify(repository, times(1)).findById(idToDelete);

		verify(repository, times(1)).delete(any(PerfilAcesso.class));

	}

	@Test
	void delete_error_usuario() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.of(new PerfilAcesso()));

		doThrow(new RuntimeException("fk_tbusuarioperfilacesso_tbperfilacesso")).when(repository).delete(any(PerfilAcesso.class));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.delete(idToDelete);
		});

		assertEquals("Não foi possível realizar a exclusão! O Perfil de Acesso está sendo utilizado para um ou mais usuários cadastrados.", error.getMessage());

		verify(repository, times(1)).findById(idToDelete);

		verify(repository, times(1)).delete(any(PerfilAcesso.class));

	}
	
	@Test
	public void teste_filter_sucesso() {
		when(this.perfilAcessoDao.countfindByFilter(any())).thenReturn(1);
		when(this.perfilAcessoDao.findByFilter(any())).thenReturn(PerfilAcessoFilterResponseDtoTest.getListPerfilAcessoFilter());
		
		var filter = service.findByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter(), 1, "nome");
		
		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 1);
		assertEquals(filter.getList().get(0).getNome(), "ADMIN");
		assertEquals(filter.getList().get(0).getSituacao(), Situacao.ATIVA);
		assertEquals(filter.getList().get(0).getSistemas().size(), 1);
	}
	
	@Test
	public void find_historys_sucesso() throws JsonProcessingException {
		when(this.perfilAcessoDao.countHistorysUpdates(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"))).thenReturn(1);
		when((List<AuditoriaDto>) this.perfilAcessoDao.findHistorysUpdates(any(), eq(0L), eq(10L))).thenReturn(AuditoriaTest.getListAuditoriaPerfilAcesso());
		
		var filter = service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1);
		
		@SuppressWarnings("unchecked")
		List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);
		
		Document info = documentos.get(0); 
		
		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 1);
		assertEquals(info.get("campoAtualizado"), "Situação Perfil Acesso");
		assertEquals(info.get("valorAntigo"), Situacao.ATIVA.toString());
		assertEquals(info.get("valorNovo"), Situacao.INATIVA.toString());

	}
	
	@Test
	public void save_error() {
		when(this.repository.findByNome(any())).thenReturn(List.of(PerfilAcessoTest.getPerfilAcesso()));
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.save(PerfilAcessoRequestDtoTest.getRequest());
		});

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());
		
		verify(repository, times(1)).findByNome(any());
		verify(repository, times(0)).save(any(PerfilAcesso.class));
	}
	
	@Test
	public void save_error_permissao_sem_sistema() {
		when(this.repository.findByNome(any())).thenReturn(List.of());
		when(this.sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
		when(this.permissaoService.findById(any())).thenReturn(PermissaoTest.getPermissaoSemSistema());
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.save(PerfilAcessoRequestDtoTest.getRequestComPermissaoSemSistemaAssociado());
		});

		Assertions.assertEquals("cadastrar não faz parte dos sistemas selecionados", error.getMessage());
		
		verify(repository, times(1)).findByNome(any());
		verify(repository, times(0)).save(any(PerfilAcesso.class));
	}
	
	@Test
	public void update_error() {
		when(this.repository.findById(any())).thenReturn(Optional.of(PerfilAcessoTest.getPerfilAcesso()));
		when(this.repository.findByNome(any())).thenReturn(List.of(PerfilAcessoTest.getPerfilAcesso()));
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.update(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),PerfilAcessoRequestDtoTest.getRequestUpdateERROR());
		});

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).findByNome(any());
		verify(repository, times(0)).save(any(PerfilAcesso.class));
	}
	
	
	@Test
	public void findBy_idModel_error() {
		when(this.repository.findById(any())).thenReturn(Optional.empty());
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.findByIdModel(UUID.fromString("b798aa0e-1a16-59de-adcb-a8a2b742aedd"));
		});

		Assertions.assertEquals("Não existe esse Perfil de Acesso", error.getMessage());
		
		verify(repository, times(1)).findById(any());
	}
	
}
