package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.ProdutoServicoDao;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.ProdutoServico;
import br.gov.ce.pge.gestao.entity.ProdutoServicoTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.repository.ProdutoServicoRepository;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.exception.ProdutoServicoNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ProdutoServicoServiceImplTest {

	@Autowired
	@InjectMocks
	private ProdutoServicoServiceImpl service;

	@Mock
	private ProdutoServicoRepository repository;

	@Mock
	private TipoReceitaService tipoReceitaService;

	@Mock
	private ProdutoServicoDao dao;

	@Mock
	private AuditoriaDao auditoriaDao;

	@Test
	void save_ok() {
		var model = ProdutoServicoTest.getProdutoServico();

		when(repository.save(any())).thenReturn(model);

		var dto = service.save(ProdutoServicoRequestDtoTest.getRequest(), anyString());

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getDescricao(), dto.getDescricao());
        assertEquals(model.getTipoReceitas().size(), dto.getIdsTipoReceitas().size());

        verify(repository, times(1)).save(any(ProdutoServico.class));
	}

	@Test
	void update_ok() throws JsonProcessingException {
		var model = ProdutoServicoTest.getProdutoServico();
		var modelAlterado = ProdutoServicoTest.getProdutoServicoUpdate();

		when(repository.findById(any())).thenReturn(Optional.of(model));
		when(repository.save(any())).thenReturn(modelAlterado);

		var dto = service.update(UUID.randomUUID(), ProdutoServicoUpdateRequestDtoTest.getRequest(), "Paulo");

		assertEquals(model.getId(), dto.getId());
		assertNotEquals(model.getDescricao(), dto.getDescricao());
        assertEquals(modelAlterado.getDescricao(), dto.getDescricao());
        assertEquals(model.getTipoReceitas().size(), dto.getIdsTipoReceitas().size());

		verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any(ProdutoServico.class));
	}

	@Test
	public void update_error() {

		when(repository.findById(any())).thenReturn(Optional.of(ProdutoServicoTest.getProdutoServico()));

		doThrow(new NegocioException("O registro já foi cadastrado anteriormente!")).when(repository).save(any());

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.update(UUID.randomUUID(), ProdutoServicoUpdateRequestDtoTest.getRequestUpdate(), "Paulo");
		});

		verify(repository, times(1)).findById(any());

		verify(repository, times(1)).save(any(ProdutoServico.class));

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());
	}

	@Test
	public void update_sucesso_com_descricao_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(ProdutoServicoTest.getProdutoServico()));
		when(this.repository.save(any())).thenReturn(ProdutoServicoTest.getProdutoServicoOutraDescricao());

		var model = service.update(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), ProdutoServicoRequestDtoTest.getRequestUpdateOutraDescricao(), anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "produto servico 2");
		assertEquals(model.getSituacao(), Situacao.ATIVA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(ProdutoServico.class));
	}

	@Test
	public void update_sucesso_com_situacao_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(ProdutoServicoTest.getProdutoServico()));
		when(this.repository.save(any())).thenReturn(ProdutoServicoTest.getProdutoServicoOutraSituacao());

		var model = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"),ProdutoServicoRequestDtoTest.getRequestUpdateOutraSituacao(), anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "produto servico 1");
		assertEquals(model.getSituacao(), Situacao.INATIVA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(ProdutoServico.class));
	}

	@Test
	public void update_sucesso_com_tipo_receita_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(ProdutoServicoTest.getProdutoServico()));
		when(this.repository.save(any())).thenReturn(ProdutoServicoTest.getProdutoServicoOutroTipoReceita());

		var model = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"),ProdutoServicoRequestDtoTest.getRequestUpdateOutroTipoReceita(), anyString());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getDescricao(), "produto servico 1");
		assertEquals(model.getSituacao(), Situacao.ATIVA);
		assertEquals(model.getIdsTipoReceitas(), Arrays.asList(UUID.fromString("bb5b7b6f-cb6e-4ec3-8ddf-ad4489d011b4")));

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(ProdutoServico.class));
	}

	@Test
	void find_by_id_ok() {
		var model = ProdutoServicoTest.getProdutoServico();

		when(repository.findById(any())).thenReturn(Optional.of(model));

		var dto = service.findById(any());

		assertEquals(model.getId(), dto.getId());
        assertEquals(model.getDescricao(), dto.getDescricao());
        assertEquals(1, dto.getIdsTipoReceitas().size());

		verify(repository, times(1)).findById(any());
	}

	@Test
	void delete_ok() {
		var model = ProdutoServicoTest.getProdutoServico();

		when(repository.findById(any())).thenReturn(Optional.of(model));

		service.delete(UUID.randomUUID(), "Paulo");

		verify(repository, times(1)).findById(any());
        verify(repository, times(1)).delete(any());
	}

	@Test
	void delete_error() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.empty());

		Exception error = Assertions.assertThrows(ProdutoServicoNotFoundException.class, () -> {
			service.delete(idToDelete, anyString());
		});

		verify(repository, times(1)).findById(idToDelete);

		Assertions.assertEquals("Produto/Serviço não encontrado.", error.getMessage());

		verify(repository, never()).delete(any(ProdutoServico.class));
	}

	@Test
	void find_by_filter_ok() {

		when(dao.findTipoReceitasByFilter(any())).thenReturn(List.of(ProdutoServicoConsultaFilterResponseDtoTest.getProdutoServico()));

		var dto = service.findProdutoServicosByFilter(ProdutoServicoFilterRequestDtoTest.getFilterCamposNull());

		assertEquals(1, dto.size());
        assertEquals("00001", dto.get(0).getCodigo());
        assertEquals("produto servico 1", dto.get(0).getDescricao());
        assertEquals(1, dto.get(0).getTipoReceitas().size());

		verify(dao, times(1)).findTipoReceitasByFilter(any());
	}

	@Test
	void find_by_filter_error() {
		when(dao.findTipoReceitasByFilter(any())).thenReturn(Collections.emptyList())
				.thenThrow(new RuntimeException("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado."));

		Assertions.assertDoesNotThrow(() -> service.findProdutoServicosByFilter(any()));

		Exception error = Assertions.assertThrows(RuntimeException.class, () -> {
			service.findProdutoServicosByFilter(any());
		});

		Assertions.assertEquals("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.", error.getMessage());

		verify(dao, times(2)).findTipoReceitasByFilter(any());
	}

	@Test
	void find_by_id_nao_encontrado() {
		when(repository.findById(any())).thenReturn(Optional.empty());

		Exception error = Assertions.assertThrows(ProdutoServicoNotFoundException.class, () -> {
			service.findById(UUID.fromString("8a5b9d71-22e6-496d-a4b4-fab372b0c1fe"));
		});

		Assertions.assertEquals("Produto/Serviço não encontrado.", error.getMessage());

		verify(repository, times(1)).findById(any());
	}

	@Test
	void save_erro_produto_servico_ja_cadastrado() {
		var model = ProdutoServicoTest.getProdutoServico();

		when(repository.findByCodigo(any())).thenReturn(model);

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.save(ProdutoServicoRequestDtoTest.getRequest(), anyString());
		});

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());

		verify(repository, times(1)).findByCodigo(any());
		verify(repository, times(0)).save(any());
	}

	@Test
	void find_view_history_produto_servico_by_id_ok() throws JsonProcessingException {

		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("ProdutoServico"))).thenReturn(Arrays.asList(AuditoriaTest.getAuditoriaProdutoServico()));

		PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> dto = service.findHistorys(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), 1, 10);

		List<HistoricoAtualizacaoResponseDto> historicos = dto.getResultado();

		List<Object> dadosAlterados = historicos.get(0).getDadosAlterados();
		@SuppressWarnings("unchecked")
		List<Document> documents = (List<Document>) dadosAlterados.get(0);

		assertEquals("anonimo", historicos.get(0).getResponsavel());
		assertEquals("Descrição do Produto/Serviço", documents.get(0).getString("campoAtualizado"));
		assertEquals("produto servico 1", documents.get(0).getString("valorAntigo"));
		assertEquals("produto servico 1 update", documents.get(0).getString("valorNovo"));

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(UUID.class), eq(0L), eq(10), eq("ProdutoServico"));
	}

	@Test
	void find_view_history_produto_servico_by_id_nao_encontrado() {
		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("ProdutoServico"))).thenReturn(null);

		Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			service.findHistorys(any(), 1, 10);
		});

		Assertions.assertEquals("Produto/Serviço selecionado não possui histórico de edições.", error.getMessage());

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(), eq(0L), eq(10), eq("ProdutoServico"));
	}

	@Test
	void find_view_history_produto_servico_by_id_vazia_nao_encontrado() {
		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10), eq("ProdutoServico"))).thenReturn(List.of());

		Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			service.findHistorys(any(), 1, 10);
		});

		Assertions.assertEquals("Produto/Serviço selecionado não possui histórico de edições.", error.getMessage());

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(), eq(0L), eq(10), eq("ProdutoServico"));
	}

	@Test
	void find_all_ok() {

		List<ProdutoServico> lista = List.of(ProdutoServicoTest.getProdutoServico());

		when(repository.findAllProdutosServico()).thenReturn(lista);

		List<ProdutoServicoResponseDto> dto = service.findAll();

		assertEquals(1, dto.size());
		assertEquals("00001", dto.get(0).getCodigo());
		assertEquals("produto servico 1", dto.get(0).getDescricao());
		assertEquals(1, dto.get(0).getIdsTipoReceitas().size());
		verify(repository, times(1)).findAllProdutosServico();
	}

}
