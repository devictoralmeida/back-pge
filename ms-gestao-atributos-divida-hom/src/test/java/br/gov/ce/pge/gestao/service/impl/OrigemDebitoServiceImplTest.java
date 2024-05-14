package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.OrigemDebitoDao;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.OrigemDebito;
import br.gov.ce.pge.gestao.entity.OrigemDebitoTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.repository.OrigemDebitoRepository;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.exception.OrigemDebitoNotFoundException;

@ExtendWith({MockitoExtension.class})
class OrigemDebitoServiceImplTest {
	
	@Autowired
	@InjectMocks
	private OrigemDebitoServiceImpl service;
	
	@Mock
	private OrigemDebitoRepository repository;

	@Mock
	private AuditoriaDao auditoriaDao;

	@Mock
	private OrigemDebitoDao origemDebitoDao;
	
	@Test
	void save_ok() {
		when(repository.save(any())).thenReturn(OrigemDebitoTest.getOrigem());
	   
		OrigemDebitoResponseDto dto = service.save(OrigemDebitoRequestDtoTest.getRequest());
        assertEquals(OrigemDebitoTest.getOrigem().getId(), dto.getId());
        assertEquals(OrigemDebitoTest.getOrigem().getNome(), dto.getNome());
        assertEquals(OrigemDebitoTest.getOrigem().getSituacao(), dto.getSituacao());
        
        verify(repository, times(1)).save(any(OrigemDebito.class));
	}
	
	@Test
	void update_ok() throws JsonProcessingException {
		when(repository.findById(any())).thenReturn(Optional.of(OrigemDebitoTest.getOrigem()));
		when(repository.save(any())).thenReturn(OrigemDebitoTest.getOrigemUpdate());
	  
		OrigemDebitoResponseDto dto = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), OrigemDebitoRequestDtoTest.getRequestUpdate());
        assertEquals(OrigemDebitoRequestDtoTest.getRequestUpdate().getId(), dto.getId());
        assertEquals(OrigemDebitoRequestDtoTest.getRequestUpdate().getNome(), dto.getNome());
        assertEquals(OrigemDebitoRequestDtoTest.getRequestUpdate().getSituacao(), dto.getSituacao());
        
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any(OrigemDebito.class));
	}

	@Test
	void find_all_ok() {
		List<OrigemDebito> lista = Arrays.asList(OrigemDebitoTest.getOrigem());
		
		when(repository.findAllOrigens()).thenReturn(lista);

		List<OrigemDebitoResponseDto> dto = service.findAll();
       
		assertEquals(1, dto.size());
        assertEquals("teste nome origem", dto.get(0).getNome());
        assertEquals(Situacao.ATIVA, dto.get(0).getSituacao());
        verify(repository, times(1)).findAllOrigens();
	}
	
	@Test
	void find_all_vazia_ok() {
		List<OrigemDebito> lista = Arrays.asList();
		
		when(repository.findAllOrigens()).thenReturn(lista);

		List<OrigemDebitoResponseDto> dto = service.findAll();
       
		assertEquals(0, dto.size());
        verify(repository, times(1)).findAllOrigens();
	}
	
	@Test
	void find_by_id_ok() {
		when(repository.findById(any())).thenReturn(Optional.of(OrigemDebitoTest.getOrigem()));

		OrigemDebitoResponseDto dto = service.findById(any());
		assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());
        assertEquals("teste nome origem", dto.getNome());
        assertEquals(Situacao.ATIVA, dto.getSituacao());
      
        verify(repository, times(1)).findById(any());
	}
	
	@Test
	void find_by_id_nao_encontrado() {
		when(repository.findById(any())).thenReturn(Optional.empty());
		
		Exception error = Assertions.assertThrows(OrigemDebitoNotFoundException.class, () -> {
			service.findById(any());
		});

		Assertions.assertEquals("Origem débito não encontrada.", error.getMessage());
      
        verify(repository, times(1)).findById(any());
	}
	
	
	@Test
	void find_view_history_origem_debito_by_id_ok() throws JsonProcessingException {

		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10L), eq("OrigemDebito"))).thenReturn(Arrays.asList(AuditoriaTest.getAuditoriaOrigemDebito()));

		PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> dto = service.findHistorys(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), 1);

		List<HistoricoAtualizacaoResponseDto> historicos = dto.getResultado();

		List<Object> dadosAlterados = historicos.get(0).getDadosAlterados();
		
		@SuppressWarnings("unchecked")
		List<Document> documents = (List<Document>) dadosAlterados.get(0);

		assertEquals("anonimo", historicos.get(0).getResponsavel());
		assertEquals("Nome da Origem do Débito", documents.get(0).getString("campoAtualizado"));
		assertEquals("teste", documents.get(0).getString("valorAntigo"));
		assertEquals("teste up", documents.get(0).getString("valorNovo"));
		assertEquals("Descrição da Origem do Débito", documents.get(1).getString("campoAtualizado"));
		assertEquals("teste descricao", documents.get(1).getString("valorAntigo"));
		assertEquals("teste descricao up", documents.get(1).getString("valorNovo"));

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(UUID.class), eq(0L), eq(10L), eq("OrigemDebito"));
	}

	@Test
	void find_view_history_origem_debito_by_id_nao_encontrado() {
		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10L), eq("OrigemDebito"))).thenReturn(null);

		Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			service.findHistorys(any(), 1);
		});

		Assertions.assertEquals("Origem de Débito selecionada não possui histórico de edições.", error.getMessage());

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(), eq(0L), eq(10L), eq("OrigemDebito"));
	}
	
	@Test
	void find_view_history_origem_debito_by_id_vazia_nao_encontrado() {
		when((List<AuditoriaDto>) this.auditoriaDao.findHistorysUpdates(any(), eq(0L), eq(10L), eq("OrigemDebito"))).thenReturn(List.of());

		Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			service.findHistorys(any(), 1);
		});

		Assertions.assertEquals("Origem de Débito selecionada não possui histórico de edições.", error.getMessage());

		verify(this.auditoriaDao, times(1)).findHistorysUpdates(any(), eq(0L), eq(10L), eq("OrigemDebito"));
	}
	
	@Test
	void save_error_origem_cadastrada() {
		when(repository.findByNome(any())).thenReturn(OrigemDebitoTest.getOrigem());

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.save(OrigemDebitoRequestDtoTest.getRequest());
		});

		Assertions.assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());
		verify(repository, times(1)).findByNome(any(String.class));
		verify(repository, times(0)).save(any(OrigemDebito.class));
	}

	@Test
	void delete_ok() {
		var model = OrigemDebitoTest.getOrigem();

		when(repository.findById(any())).thenReturn(Optional.of(model));

		service.delete(any());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).delete(any());
	}

	@Test
	void delete_error() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.of(new OrigemDebito()));

		doThrow(new RuntimeException("Não foi possível realizar a exclusão! Mensagem específica da exceção esperada.")).when(repository).delete(any(OrigemDebito.class));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.delete(idToDelete);
		});

		assertEquals("Não foi possível realizar a exclusão! Mensagem específica da exceção esperada.", error.getMessage());

		verify(repository, times(1)).findById(idToDelete);

		verify(repository, times(1)).delete(any(OrigemDebito.class));

	}
	
	@Test
	void find_by_filter_ok() {

		when(origemDebitoDao.findOrigemDebitosByFilter(any())).thenReturn(List.of(OrigemDebitoConsultaResponseDtoTest.getOrigemDebito()));

		var dto = service.findOrigemDebitoByFilter(any());

		assertEquals(1, dto.size());
		assertEquals("teste nome origem", dto.get(0).getNome());
		assertEquals(Situacao.ATIVA.toString(), dto.get(0).getSituacao());

		verify(origemDebitoDao, times(1)).findOrigemDebitosByFilter(any());
	}
	
	@Test
	public void update_error() {

		when(repository.findById(any())).thenReturn(Optional.of(OrigemDebitoTest.getOrigem()));
		when(repository.findByNome(any())).thenReturn(OrigemDebitoTest.getOrigem());

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.update(any(), OrigemDebitoRequestDtoTest.getRequestUpdateOutroNome());
		});

		assertEquals("O registro já foi cadastrado anteriormente!", error.getMessage());
		
		verify(repository, times(1)).findById(any());

		verify(repository, times(0)).save(any(OrigemDebito.class));

	}
	
	@Test
	public void update_sucesso_com_nome_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(OrigemDebitoTest.getOrigem()));
		when(this.repository.save(any())).thenReturn(OrigemDebitoTest.getOrigemUpdateOutroNome());

		var model = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"),OrigemDebitoRequestDtoTest.getRequestUpdateOutroNome());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "teste nome origem update");
		assertEquals(model.getSituacao(), Situacao.ATIVA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(OrigemDebito.class));
	}

	@Test
	public void update_sucesso_com_descricao_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(OrigemDebitoTest.getOrigem()));
		when(this.repository.save(any())).thenReturn(OrigemDebitoTest.getOrigemUpdateOutraDescricao());

		var model = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"),OrigemDebitoRequestDtoTest.getRequestUpdateOutraDescricao());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "teste nome origem");
		assertEquals(model.getDescricao(), "teste descricao diferente");
		assertEquals(model.getSituacao(), Situacao.ATIVA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(OrigemDebito.class));
	}

	@Test
	public void update_sucesso_com_situacao_difirente() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(OrigemDebitoTest.getOrigem()));
		when(this.repository.save(any())).thenReturn(OrigemDebitoTest.getOrigemUpdateOutraSituacao());

		var model = service.update(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"),OrigemDebitoRequestDtoTest.getRequestUpdateOutraSituacao());

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(model.getNome(), "teste nome origem");
		assertEquals(model.getDescricao(), "teste");
		assertEquals(model.getSituacao(), Situacao.INATIVA);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(OrigemDebito.class));
	}
	
	@Test
	void delete_error_tipo_receita() {
		UUID idToDelete = UUID.randomUUID();

		when(repository.findById(idToDelete)).thenReturn(Optional.of(new OrigemDebito()));

		doThrow(new RuntimeException("fk_tbtiporeceitaorigem_tborigemdebito")).when(repository).delete(any(OrigemDebito.class));

		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			service.delete(idToDelete);
		});

		assertEquals("Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.", error.getMessage());

		verify(repository, times(1)).findById(idToDelete);

		verify(repository, times(1)).delete(any(OrigemDebito.class));

	}

}
