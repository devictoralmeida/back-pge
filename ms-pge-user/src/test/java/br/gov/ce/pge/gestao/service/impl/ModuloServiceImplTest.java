package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dao.ModuloDao;
import br.gov.ce.pge.gestao.dto.request.ModuloRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.Modulo;
import br.gov.ce.pge.gestao.entity.ModuloTest;
import br.gov.ce.pge.gestao.repository.ModuloRepository;
import br.gov.ce.pge.gestao.service.PermissaoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({ MockitoExtension.class })
class ModuloServiceImplTest {

	@Mock
	private ModuloRepository repository;

	@Mock
	private ModuloDao moduloDao;

	@Mock
	private PermissaoService permissaoService;

	@Autowired
	@InjectMocks
	private ModuloServiceImpl service;

	@Test
	public void save_sucesso() {
		when(this.repository.save(any())).thenReturn(ModuloTest.getModulo());

		var model = service.save(ModuloRequestDtoTest.getRequest());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "origem debito");

		verify(repository, times(1)).save(any(Modulo.class));
	}

	@Test
	public void update_sucesso() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(ModuloTest.getModulo()));
		when(this.repository.save(any())).thenReturn(ModuloTest.getModuloUpdate());

		var model = service.update(any(), ModuloRequestDtoTest.getRequest());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "origem debito update");

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(Modulo.class));
	}

	@Test
	public void findById_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(ModuloTest.getModulo()));

		var model = service.findById(any());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "origem debito");

		verify(repository, times(1)).findById(any());
	}

	@Test
	public void findByAll_sucesso() {
		when(this.repository.findAll()).thenReturn(ModuloTest.getListModulo());

		var lista = service.findAll();

		assertNotNull(lista);
		assertEquals(lista.size(), 1);
		assertEquals(lista.get(0).getNome(), "origem debito");

		verify(repository, times(1)).findAll();
	}

	@Test
	public void delete_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(ModuloTest.getModulo()));

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

		Assertions.assertEquals("Modulo não existe", error.getMessage());

		verify(repository, times(1)).findById(any());
	}


	@Test
	public void find_historys_sucesso() throws JsonProcessingException{
		when(this.moduloDao.countHistorysUpdates(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"))).thenReturn(1);
		when((List<AuditoriaDto>) this.moduloDao.findHistorysUpdates(any(), eq(0L), eq(10))).thenReturn(AuditoriaTest.getListAuditoriaModulo());

		var filter = service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10);

		@SuppressWarnings("unchecked")
		List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);

		Document info = documentos.get(0);

		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 1);
		assertEquals(info.get("campoAtualizado"), "Nome do Módulo");
		assertEquals(info.get("valorAntigo"), "origem debito");
		assertEquals(info.get("valorNovo"), "origem debito update");

	}

}
