package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoDtoTest;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDtoTest;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class ProdutoServicoControllerTest {

	@InjectMocks
	ProdutoServicoController controller;

	@Mock
	ProdutoServicoService service;
	
	@Mock
	GlobalExceptionHandler globalExceptionHandler;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}
	
	@Test
	public void test_cadastro_produto_servico_ok() throws Exception {
		when(service.save(any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServicoResponse());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(ProdutoServicoRequestDtoTest.getRequest());

	    mockMvc.perform(MockMvcRequestBuilders.post("/produto-servico")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"3e4a0c60-22e6-496d-a4b4-fab372b0c0ed\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"ATIVA\"}}"))
				.andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"descricao\": \"produto servico 1\"}}"))
				.andExpect(jsonPath("$.data.idsTipoReceitas[0]").value("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Produto/Serviço.\"}"));

	    verify(service, times(1)).save(any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_consulta_por_id_ok() throws Exception {
		when(service.findById(any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServicoResponse());

	    mockMvc.perform(MockMvcRequestBuilders.get("/produto-servico/{id}", "3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"3e4a0c60-22e6-496d-a4b4-fab372b0c0ed\"}}"))
				.andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"ATIVA\"}}"))
				.andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"descricao\": \"produto servico 1\"}}"))
				.andExpect(jsonPath("$.data.idsTipoReceitas[0]").value("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Produto serviço encontrado\"}"));

	    verify(service, times(1)).findById(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_list_all_ok() throws Exception {
		when(service.findAll()).thenReturn(Arrays.asList(ProdutoServicoResponseDtoTest.getProdutoServicoResponse()));

	    mockMvc.perform(MockMvcRequestBuilders.get("/produto-servico/")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Produtos Serviços encontrados\"}"));

	    verify(service, times(1)).findAll();
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_update_produto_servico_ok() throws Exception {
		when(service.update(any(),any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServicoResponseUpdate());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(ProdutoServicoRequestDtoTest.getRequestUpdate());

	    mockMvc.perform(MockMvcRequestBuilders.put("/produto-servico/{id}", "3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"3e4a0c60-22e6-496d-a4b4-fab372b0c0ed\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"ATIVA\"}}"))
				.andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"descricao\": \"produto servico 1\"}}"))
				.andExpect(jsonPath("$.data.idsTipoReceitas[0]").value("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("Produto serviço alterada com sucesso")));

	    verify(service, times(1)).update(any(),any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_update_produto_servico_error() throws Exception {
		doThrow(new NegocioException("O registro já foi cadastrado anteriormente, com o nome: junit")).when(service).update(any(), any());

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.update(any(), any());
		});

		assertEquals("O registro já foi cadastrado anteriormente, com o nome: junit", exception.getMessage());

		verify(service).update(any(), any());
	}

	@Test
	public void test_cadastro_produto_servico_sem_descricao_error() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(ProdutoServicoRequestDtoTest.getRequestSemDescricao());

		mockMvc.perform(MockMvcRequestBuilders.post("/produto-servico")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		verify(service, times(0)).update(any(),any());
	}

	@Test
	public void test_consulta_historico_por_id_ok() throws Exception {
		when(service.findHistorys(any(UUID.class), eq(1))).thenReturn(HistoricoAtualizacaoDtoTest.getResponse());

		mockMvc.perform(MockMvcRequestBuilders.get("/produto-servico/historico/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data.totalRegistros").value(1))
				.andExpect(jsonPath("$.data.totalPaginas").value(1))
				.andExpect(jsonPath("$.data.paginaAtual").value(1))
				.andExpect(jsonPath("$.data.resultado[0].dadosAlterados[0].campoAtualizado").value("situacao"))
				.andExpect(jsonPath("$.data.resultado[0].dadosAlterados[0].valorAntigo").value("ATIVA"))
				.andExpect(jsonPath("$.data.resultado[0].dadosAlterados[0].valorNovo").value("INATIVA"))
				.andExpect(jsonPath("$.data.resultado[0].responsavel").value("anonimo"))
				.andExpect(jsonPath("$.data.resultado[0].dataAlterado").value("21/11/2023 10:57:31"))
				.andExpect(jsonPath("$.mensagem").value("Históricos do produto encontrados"))
				.andDo(MockMvcResultHandlers.print());

		verify(service, times(1)).findHistorys(any(UUID.class), eq(1));
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_historico_lista_vazia_ok() {
		doThrow(new HistoricoAtualizacaoNotFoundException("Produto/Serviço selecionado não possui histórico de edições.")).when(service).findHistorys(any(), eq(0));

		HistoricoAtualizacaoNotFoundException exception = assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			controller.viewHistoryById(any(), eq(0));
		});

		assertEquals("Produto/Serviço selecionado não possui histórico de edições.", exception.getMessage());

		verify(service).findHistorys(any(), eq(0));
	}

	@Test
	public void test_consulta_por_filtro_ok() throws Exception {
		when(service.findProdutoServicosByFilter(any())).thenReturn(Arrays.asList(ProdutoServicoConsultaFilterResponseDtoTest.getProdutoServico()));

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(ProdutoServicoFilterRequestDtoTest.getProdutoServico());

		mockMvc.perform(MockMvcRequestBuilders.post("/produto-servico/filtros")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data[0].id").value("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"))
				.andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Produtos serviços encontrados com sucesso\"}"));

		verify(service, times(1)).findProdutoServicosByFilter(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_filter_lista_vazia_ok() throws Exception {
		when(service.findProdutoServicosByFilter(any(ProdutoServicoFilterRequestDto.class))).thenReturn(Arrays.asList());

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(ProdutoServicoFilterRequestDtoTest.getFilterRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/produto-servico/filtros")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.")));

		verify(service, times(1)).findProdutoServicosByFilter(any(ProdutoServicoFilterRequestDto.class));

	}

	@Test
	public void test_delete_ok() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/produto-servico/{id}", "1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Produto serviço deletado com sucesso\"}"));

		verify(service, times(1)).delete(any());
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_delete_error() {

		doThrow(new NegocioException("Não foi possível realizar a exclusão!")).when(service).delete(any());

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.delete(any());
		});

		assertEquals("Não foi possível realizar a exclusão!", exception.getMessage());

		verify(service).delete(any());
	}

	@Test
	public void test_cadastro_produto_servico_error() throws Exception {
		doThrow(new NegocioException("O registro já foi cadastrado anteriormente, com o código: 0001")).when(service).save(any());

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.save(any());
		});

		assertEquals("O registro já foi cadastrado anteriormente, com o código: 0001", exception.getMessage());

		verify(service).save(any());
	}
}
