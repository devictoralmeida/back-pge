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

import br.gov.ce.pge.gestao.dao.PermissaoDao;
import br.gov.ce.pge.gestao.dto.request.PermissaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.Permissao;
import br.gov.ce.pge.gestao.entity.PermissaoTest;
import br.gov.ce.pge.gestao.repository.PermissaoRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
class PermissaoServiceImplTest {

	@Mock
	private PermissaoRepository repository;

	@Mock
	private PermissaoDao permissaoDao;
	
	@Autowired
	@InjectMocks
	private PermissaoServiceImpl service;
	
	@Test
	public void save_sucesso() {
		when(this.repository.save(any())).thenReturn(PermissaoTest.getPermissao());
		
		Permissao permissao = service.save(PermissaoRequestDtoTest.getRequest());
		
		assertNotNull(permissao);
		assertNotNull(permissao.getId());
		assertEquals(permissao.getNome(), "cadastrar");
		
		verify(repository, times(1)).save(any(Permissao.class));
	}
	
	@Test
	public void update_sucesso() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(PermissaoTest.getPermissao()));
		when(this.repository.save(any())).thenReturn(PermissaoTest.getPermissaoUpdate());
		
		var permissao = service.update(any(), PermissaoRequestDtoTest.getRequest());
		
		assertNotNull(permissao);
		assertNotNull(permissao.getId());
		assertEquals(permissao.getNome(), "cadastrar update");
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(Permissao.class));
	}
	
	@Test
	public void findById_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(PermissaoTest.getPermissao()));
		
		var permissao = service.findById(any());
		
		assertNotNull(permissao);
		assertNotNull(permissao.getId());
		assertEquals(permissao.getNome(), "cadastrar");
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	public void findByAll_sucesso() {
		when(this.repository.findAll()).thenReturn(PermissaoTest.getListPermissao());
		
		var permissoes = service.findAll();
		
		assertNotNull(permissoes);
		assertEquals(permissoes.size(), 1);
		assertEquals(permissoes.get(0).getNome(), "cadastrar");
		
		verify(repository, times(1)).findAll();
	}
	
	@Test
	public void delete_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(PermissaoTest.getPermissao()));
		
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

		Assertions.assertEquals("Permissão não encontrada", error.getMessage());
		
		verify(repository, times(1)).findById(any());
	}
	
	
	@Test
	public void find_historys_sucesso() throws JsonProcessingException {
		when(this.permissaoDao.countHistorysUpdates(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"))).thenReturn(1);
		when((List<AuditoriaDto>) this.permissaoDao.findHistorysUpdates(any(), eq(0L), eq(10L))).thenReturn(AuditoriaTest.getListAuditoriaPermissao());
		
		var filter = service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1);
		
		@SuppressWarnings("unchecked")
		List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);
		
		Document info = documentos.get(0); 
		
		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 1);
		assertEquals(info.get("campoAtualizado"), "Nome da Permissão");
		assertEquals(info.get("valorAntigo"), "cadastrar");
		assertEquals(info.get("valorNovo"), "cadastrar update");

	}

}
