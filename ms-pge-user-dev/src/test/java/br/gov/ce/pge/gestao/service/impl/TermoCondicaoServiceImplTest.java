package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import br.gov.ce.pge.gestao.dao.TermoCondicaoDao;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.TermoCondicao;
import br.gov.ce.pge.gestao.entity.TermoCondicaoTest;
import br.gov.ce.pge.gestao.repository.TermoCondicaoRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
class TermoCondicaoServiceImplTest {
	
	@InjectMocks
	@Autowired
	private TermoCondicaoServiceImpl service;
	
	@Mock
	private TermoCondicaoRepository repository;
	
	@Mock
	private TermoCondicaoDao dao;

	@Test
	void update_termo_sucesso() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicao()));
		
		service.update(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"), TermoCondicaoRequestDtoTest.getRequest());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TermoCondicao.class));
	}

	@Test
	void update_termo_sucesso_sem_versao_nova() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicao()));

		service.updateExistente(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"), TermoCondicaoRequestDtoTest.getRequest());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TermoCondicao.class));
	}

	@Test
	void update_termo_portal_origens_sucesso() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicaoPortalOrigens()));

		service.update(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"), TermoCondicaoRequestDtoTest.getRequest());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TermoCondicao.class));
	}

	@Test
	void update_termo_erro() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicao()));
		when(repository.countByIdTermoCondicao(any())).thenReturn(2);

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.update(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"), TermoCondicaoRequestDtoTest.getRequest());
		});

		assertEquals("Alteração só é permitida na versão vigente!", error.getMessage());

		verify(repository, times(1)).countByIdTermoCondicao(any());
	}
	
	@Test
	void update_termo_sucesso_version() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicaoOther()));
		
		service.update(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"), TermoCondicaoRequestDtoTest.getRequest());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TermoCondicao.class));
	}
	
	
	@Test
	void findBy_idModel_sucesso() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicao()));
		
		TermoCondicao termo = service.findByIdModel(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		
		assertEquals(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"), termo.getId());
		assertEquals("Termos e Condições .....", termo.getConteudo());
		assertEquals("0.1", termo.getVersao());
		assertEquals("Portal da Dívida Ativa", termo.getSistema().getNome());

		verify(repository, times(1)).findById(any());
	}
	
	@Test
	void findBy_idModel_erro() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.empty());
		
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.findByIdModel(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		});
		
		assertEquals("Registro não encontrado.", error.getMessage());
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	void findBy_sistema_portal_divida_sucesso() {
		when(repository.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicao());
		
		var termos = service.findBySistema();
		
		assertEquals(1, termos.size());
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termos.get(0).getId());
		assertEquals("Portal da Dívida Ativa", termos.get(0).getNomeSistema());
		assertEquals("0.1", termos.get(0).getVersao());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termos.get(0).getDataCriacao());
	
		verify(repository, times(1)).findByNomeSistema();
	}

	@Test
	void findBy_sistema_portal_origens_sucesso() {
		when(repository.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicaoPortalOrigens());

		var termos = service.findBySistema();

		assertEquals(1, termos.size());
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termos.get(0).getId());
		assertEquals("Portal das Origens", termos.get(0).getNomeSistema());
		assertEquals("0.1", termos.get(0).getVersao());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termos.get(0).getDataCriacao());

		verify(repository, times(1)).findByNomeSistema();
	}

	@Test
	void findBy_sistema_portal_divida_sucesso_pendentes() {
		when(repository.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicaoPendentes());

		var termos = service.findBySistema();

		assertEquals(1, termos.size());
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termos.get(0).getId());
		assertEquals("Portal da Dívida Ativa", termos.get(0).getNomeSistema());
		assertEquals("0.1", termos.get(0).getVersao());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termos.get(0).getDataCriacao());

		verify(repository, times(1)).findByNomeSistema();
	}

	@Test
	void findBy_sistema_portal_origens_sucesso_pendentes() {
		when(repository.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicaoPortalOrigensPendentes());

		var termos = service.findBySistema();

		assertEquals(1, termos.size());
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termos.get(0).getId());
		assertEquals("Portal das Origens", termos.get(0).getNomeSistema());
		assertEquals("0.1", termos.get(0).getVersao());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termos.get(0).getDataCriacao());

		verify(repository, times(1)).findByNomeSistema();
	}

	@Test
	void findBy_sistema_sucesso() {
		when(repository.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicao());

		var termos = service.findByNomeSistema();

		assertEquals(1, termos.size());
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termos.get(0).getId().toString());
		assertEquals("0.1", termos.get(0).getVersao());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termos.get(0).getDataCriacao());

		verify(repository, times(1)).findByNomeSistema();
	}
	
	@Test
	void findBy_id() {
		when(repository.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"))).thenReturn(Optional.of(TermoCondicaoTest.getTermoCondicao()));
		
		var termo = service.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		
		assertEquals("Portal da Dívida Ativa", termo.getNomeSistema());
		assertEquals("0.1", termo.getVersao());
		assertEquals("anônimo", termo.getNomeUsuario());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termo.getDataCriacao());
	
		verify(repository, times(1)).findById(any());
	}

	@Test
	public void find_historys_sucesso() throws JsonProcessingException {
		when(this.dao.countHistorysUpdates(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"))).thenReturn(1);
		when((List<AuditoriaDto>) this.dao.findHistorysUpdates(any(), eq(0L), eq(11L))).thenReturn(AuditoriaTest.getListAuditoriaTermo());

		var filter = service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1);

		List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);
		Document info = documentos.get(0);


		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 1);
		assertEquals(info.get("campoAtualizado"), "Versão do Termo");
		assertEquals(info.get("valorAntigo"), null);
		assertEquals(info.get("valorNovo"), "0.1");

	}

	@Test
	public void find_multiples_historys_sucesso() throws JsonProcessingException {
		when(this.dao.countHistorysUpdates(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"))).thenReturn(2);
		when((List<AuditoriaDto>) this.dao.findHistorysUpdates(any(), eq(0L), eq(11L))).thenReturn(AuditoriaTest.getMultipleListAuditoriaTermo());

		var filter = service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1);

		List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);
		Document info = documentos.get(0);


		assertNotNull(filter);
		assertEquals(filter.getPaginaAtual(), 1);
		assertEquals(filter.getTotalPaginas(), 1);
		assertEquals(filter.getTotalRegistros(), 2);
		assertEquals(info.get("campoAtualizado"), "Versão do Termo");
		assertEquals(info.get("valorAntigo"), "0.1");
		assertEquals(info.get("valorNovo"), "0.2");

	}
	
}
