package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.gov.ce.pge.gestao.dao.SistemaDao;
import br.gov.ce.pge.gestao.dto.request.SistemaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.entity.SistemaTest;
import br.gov.ce.pge.gestao.repository.SistemaRepository;
import br.gov.ce.pge.gestao.service.ModuloService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
class SistemaServiceImplTest {

	@Mock
	private SistemaRepository repository;

	@Mock
	private SistemaDao sistemaDao;
	
	@Mock
	private ModuloService moduloService;
	
	@Autowired
	@InjectMocks
	private SistemaServiceImpl service;
	
	@Test
	public void save_sucesso() {
		when(this.repository.save(any())).thenReturn(SistemaTest.getSistema());
		
		var model = service.save(SistemaRequestDtoTest.getRequest());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "Portal da Dívida Ativa");
		
		verify(repository, times(1)).save(any(Sistema.class));
	}
	
	@Test
	public void save_sucesso_system_without_module() {
		when(this.repository.save(any())).thenReturn(SistemaTest.getSistemaSemModulo());
		
		var model = service.save(SistemaRequestDtoTest.getRequestSemModulo());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "Portal da Dívida Ativa");
		
		verify(repository, times(1)).save(any(Sistema.class));
	}
	
	@Test
	public void update_sucesso() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(SistemaTest.getSistema()));
		when(this.repository.save(any())).thenReturn(SistemaTest.getSistemaUpdate());
		
		var model = service.update(any(), SistemaRequestDtoTest.getRequest());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "portal divida ativa update");
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(Sistema.class));
	}
	
	@Test
	public void findById_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(SistemaTest.getSistema()));
		
		var model = service.findById(any());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "Portal da Dívida Ativa");
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	public void findByAll_sucesso() {
		when(this.repository.findAll()).thenReturn(SistemaTest.getListSistema());
		
		var lista = service.findAll();
		
		assertNotNull(lista);
		assertEquals(lista.size(), 1);
		assertEquals(lista.get(0).getNome(), "Portal da Dívida Ativa");
		
		verify(repository, times(1)).findAll();
	}
	
	@Test
	public void findAll_ordenados_sucesso() {
		when(sistemaDao.findAllOrdenados()).thenReturn(List.of());
		
		var lista = service.findAllOrdenados();
		
		assertNotNull(lista);
		assertEquals(lista.size(), 0);
		
		verify(sistemaDao, times(1)).findAllOrdenados();
	}
	
	@Test
	public void delete_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(SistemaTest.getSistema()));
		
		service.delete(any());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).delete(any());
	}
	
	@Test
	public void findById_error() {
		when(this.repository.findById(any())).thenReturn(Optional.empty());
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.findById(UUID.fromString("b798aa0e-1a16-59de-adcb-a8a2b742aedd"));
		});

		Assertions.assertEquals("Sistema não encontrado", error.getMessage());
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	public void save_error() {
		when(this.repository.findByNome(any())).thenReturn(List.of(SistemaTest.getSistema()));
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.save(SistemaRequestDtoTest.getRequest());
		});

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());
		
		verify(repository, times(1)).findByNome(any());
		
		verify(repository, times(0)).save(any(Sistema.class));
	}
	
	
	@Test
	public void find_historys_sucesso() throws JsonProcessingException {
		when(this.sistemaDao.countHistorysUpdates(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"))).thenReturn(1);
		when((List<AuditoriaDto>) this.sistemaDao.findHistorysUpdates(any(), eq(0L), eq(10L))).thenReturn(AuditoriaTest.getListAuditoriaSistema());
		
		var filter = service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1);
		
		@SuppressWarnings("unchecked")
		List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);
		
		Document info = documentos.get(0); 
		
		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 1);
		assertEquals(info.get("campoAtualizado"), "Nome do Sistema");
		assertEquals(info.get("valorAntigo"), "Portal da Dívida Ativa");
		assertEquals(info.get("valorNovo"), "portal divida ativa update");

	}
	
	@Test
	public void findAll_permissoes_por_sistema_sucesso() {
		when(this.repository.findAllPermissoesBySistema()).thenReturn(SistemaTest.getListSistema());
		
		var lista = service.findAllPermissoesBySistema();
		
		assertNotNull(lista);
		assertEquals(lista.size(), 1);
		assertEquals(lista.get(0).getNome(), "Portal da Dívida Ativa");
		
		verify(repository, times(1)).findAllPermissoesBySistema();
	}
}
