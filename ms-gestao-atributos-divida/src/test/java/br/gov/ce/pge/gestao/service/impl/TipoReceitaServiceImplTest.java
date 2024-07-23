package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.contantes.MensagemTipoReceita;
import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.TipoReceitaDao;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.TipoReceita;
import br.gov.ce.pge.gestao.entity.TipoReceitaTest;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.repository.TipoReceitaRepository;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.exception.TipoReceitaNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class TipoReceitaServiceImplTest {

	@Autowired
	@InjectMocks
	private TipoReceitaServiceImpl service;

	@Mock
	private TipoReceitaRepository repository;

	@Mock
	private OrigemDebitoService origemDebitoService;

	@Mock
	private TipoReceitaDao dao;

	@Mock
	private AuditoriaDao auditoriaDao;

	@Test
	void save_ok() {
		var model = TipoReceitaTest.getTipoReceita();

		when(repository.save(any())).thenReturn(model);

		var dto = service.save(TipoReceitaRequestDtoTest.getRequest(), anyString());

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getDescricao(), dto.getDescricao());
        assertEquals(model.getOrigemDebitos().size(), dto.getOrigemDebitos().size());

        verify(repository, times(1)).save(any(TipoReceita.class));
	}

	@Test
	void update_ok() throws JsonProcessingException {
		var model = TipoReceitaTest.getTipoReceita();
		var modelUpdate = TipoReceitaTest.getTipoReceitaUpdate();

		when(repository.findById(any())).thenReturn(Optional.of(model));
		when(repository.save(any())).thenReturn(modelUpdate);

		var dto = service.update(UUID.randomUUID(), TipoReceitaUpdateRequestDtoTest.getRequest(), "Paulo");

		assertEquals(model.getId(), dto.getId());
        assertNotEquals(model.getDescricao(), dto.getDescricao());
        assertEquals("Receita 01 update", dto.getDescricao());
        assertEquals(model.getOrigemDebitos().size(), dto.getOrigemDebitos().size());

		verify(repository, times(1)).save(any(TipoReceita.class));
	}

	@Test
	public void update_error() {

		when(repository.findById(any())).thenReturn(Optional.of(TipoReceitaTest.getTipoReceita()));

		doThrow(new NegocioException("O registro já foi cadastrado anteriormente, com o nome: junit")).when(repository).save(any());

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.update(UUID.randomUUID(), TipoReceitaUpdateRequestDtoTest.getRequestUpdate(), "Paulos");
		});

		verify(repository, times(1)).findById(any());

		verify(repository, times(1)).save(any(TipoReceita.class));

		Assertions.assertEquals("O registro já foi cadastrado anteriormente, com o nome: junit", error.getMessage());
	}

	@Test
	public void update_sucesso_com_descricao_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(TipoReceitaTest.getTipoReceita()));
		when(this.repository.save(any())).thenReturn(TipoReceitaTest.getTipoReceitaComOutraDescricao());

		var model = service.update(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), TipoReceitaRequestDtoTest.getRequestUpdateOutraDescricao(),anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "Receita 02");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getNatureza(), Natureza.TRIBUTARIA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TipoReceita.class));
	}

	@Test
	public void update_sucesso_com_situacao_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(TipoReceitaTest.getTipoReceita()));
		when(this.repository.save(any())).thenReturn(TipoReceitaTest.getTipoReceitaComOutraSituacao());

		var model = service.update(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), TipoReceitaRequestDtoTest.getRequestUpdateOutraSituacao(),anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "Receita 01");
		assertEquals(model.getSituacao(), Situacao.INATIVA);
		assertEquals(model.getNatureza(), Natureza.TRIBUTARIA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TipoReceita.class));
	}

	@Test
	public void update_sucesso_com_natureza_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(TipoReceitaTest.getTipoReceita()));
		when(this.repository.save(any())).thenReturn(TipoReceitaTest.getTipoReceitaComOutraNatureza());

		var model = service.update(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), TipoReceitaRequestDtoTest.getRequestUpdateOutraNatureza(),anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "Receita 01");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getNatureza(), Natureza.NAO_TRIBUTARIA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TipoReceita.class));
	}

	@Test
	public void update_sucesso_com_origem_debito_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(TipoReceitaTest.getTipoReceita()));
		when(this.repository.save(any())).thenReturn(TipoReceitaTest.getTipoReceitaComOutraOrigem());

		var model = service.update(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), TipoReceitaRequestDtoTest.getRequestUpdateOutraOrigem(), anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "Receita 01");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getNatureza(), Natureza.TRIBUTARIA);
		assertEquals(model.getOrigemDebitos(), Arrays.asList(UUID.fromString("b88bc5e8-6126-4af9-9c61-cea471800974")));

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(TipoReceita.class));
	}

	@Test
	void find_by_id_ok() {
		var model = TipoReceitaTest.getTipoReceita();

		when(repository.findById(any())).thenReturn(Optional.of(model));

		var dto = service.findById(any());

		assertEquals(model.getId(), dto.getId());
        assertEquals(model.getDescricao(), dto.getDescricao());
        assertEquals(model.getOrigemDebitos().size(), dto.getOrigemDebitos().size());

		verify(repository, times(1)).findById(any());
	}

	@Test
	void find_by_filter_ok() {

		when(dao.findTipoReceitasByFilter(any())).thenReturn(List.of(TipoReceitaConsultaFilterResponseDtoTest.getResponseConsulta()));

		var dto = service.findTipoReceitasByFilter(TipoReceitaFilterRequestDtoTest.getFilterCamposNull());

		assertEquals(1, dto.size());
        assertEquals("0001", dto.get(0).getCodigo());
        assertEquals("Receita 01", dto.get(0).getDescricao());
        assertEquals(1, dto.get(0).getOrigemDebitos().size());

		verify(dao, times(1)).findTipoReceitasByFilter(any());
	}

	@Test
	void find_all_ok() {

		List<TipoReceita> lista = List.of(TipoReceitaTest.getTipoReceita());

		when(repository.findAllTipos()).thenReturn(lista);

		List<TipoReceitaResponseDto> dto = service.findAll();

		assertEquals(1, dto.size());
		assertEquals("0001", dto.get(0).getCodigo());
		assertEquals("Receita 01", dto.get(0).getDescricao());
		assertEquals(1, dto.get(0).getOrigemDebitos().size());
		verify(repository, times(1)).findAllTipos();
	}

	@Test
	void find_by_filter_error() {
		when(dao.findTipoReceitasByFilter(any())).thenReturn(Collections.emptyList())
				.thenThrow(new RuntimeException("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado."));

		Assertions.assertDoesNotThrow(() -> service.findTipoReceitasByFilter(any()));

		Exception error = Assertions.assertThrows(RuntimeException.class, () -> {
			service.findTipoReceitasByFilter(any());
		});

		Assertions.assertEquals("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.", error.getMessage());

		verify(dao, times(2)).findTipoReceitasByFilter(any());
	}

	@Test
	void delete_ok() {
		var model = TipoReceitaTest.getTipoReceita();

		when(repository.findById(any())).thenReturn(Optional.of(model));

		service.delete(UUID.randomUUID(), "Paulo");

		verify(repository, times(1)).findById(any());
        verify(repository, times(1)).delete(any());
	}

	@Test
	void delete_error_produto_servico() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.of(new TipoReceita()));

		doThrow(new RuntimeException("fk_tbprodutoservicoreceita_tbtiporeceita")).when(repository).delete(any(TipoReceita.class));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.delete(idToDelete, anyString());
		});

		verify(repository, times(1)).findById(idToDelete);

		verify(repository, times(1)).delete(any(TipoReceita.class));

		Assertions.assertEquals("Não foi possível realizar a exclusão! O Tipo receita está sendo utilizado para um ou mais Produto/Serviço cadastrado.", error.getMessage());
	}

	@Test
	void delete_error() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.of(new TipoReceita()));

		doThrow(new RuntimeException("Não foi possível realizar a exclusão! Mensagem específica da exceção esperada.")).when(repository).delete(any(TipoReceita.class));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.delete(idToDelete, anyString());
		});

		assertEquals("Não foi possível realizar a exclusão! Mensagem específica da exceção esperada.", error.getMessage());

		verify(repository, times(1)).findById(idToDelete);

		verify(repository, times(1)).delete(any(TipoReceita.class));

	}

	@Test
	void find_by_id_nao_encontrado() {
		when(repository.findById(any())).thenReturn(Optional.empty());

		Exception error = Assertions.assertThrows(TipoReceitaNotFoundException.class, () -> {
			service.findById(UUID.fromString("8a5b9d71-22e6-496d-a4b4-fab372b0c1fe"));
		});

		Assertions.assertEquals(MensagemTipoReceita.MENSAGEM_TIPORECEITA_NAO_ENCONTRADA, error.getMessage());

		verify(repository, times(1)).findById(any());
	}

	@Test
	void save_erro_tipo_receita_ja_cadastrada() {
		var model = TipoReceitaTest.getTipoReceita();

		when(repository.findByCodigo(any())).thenReturn(model);

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.save(TipoReceitaRequestDtoTest.getRequest(), anyString());
		});

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());

		verify(repository, times(0)).save(any());
	}

	@Test
	void find_view_history_tipo_receita_by_id_ok() throws JsonProcessingException {

		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("TipoReceita"))).thenReturn(Arrays.asList(AuditoriaTest.getAuditoriaTipoReceita()));

		PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> dto = service.findHistorys(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"), 1, 10);

		List<HistoricoAtualizacaoResponseDto> historicos = dto.getResultado();

		List<Object> dadosAlterados = historicos.get(0).getDadosAlterados();
		@SuppressWarnings("unchecked")
		List<Document> documents = (List<Document>) dadosAlterados.get(0);

		assertEquals("anonimo", historicos.get(0).getResponsavel());
		assertEquals("Descrição do Tipo Receita", documents.get(0).getString("campoAtualizado"));
		assertEquals("Receita 01", documents.get(0).getString("valorAntigo"));
		assertEquals("Receita 01 update", documents.get(0).getString("valorNovo"));

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(UUID.class), eq(0L), eq(10), eq("TipoReceita"));
	}

	@Test
	void find_view_history_tipo_receita_by_id_nao_encontrado() {
		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("TipoReceita"))).thenReturn(null);

		Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			service.findHistorys(any(), 1, 10);
		});

		Assertions.assertEquals("Tipo Receita selecionado não possui histórico de edições.", error.getMessage());

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(), eq(0L), eq(10), eq("TipoReceita"));
	}

	@Test
	void find_view_history_tipo_receita_by_id_vazia_nao_encontrado() {
		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("TipoReceita"))).thenReturn(List.of());

		Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			service.findHistorys(any(), 1, 10);
		});

		Assertions.assertEquals("Tipo Receita selecionado não possui histórico de edições.", error.getMessage());

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(), eq(0L), eq(10), eq("TipoReceita"));
	}

}
