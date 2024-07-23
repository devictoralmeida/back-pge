package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dao.CondicaoAcessoDao;
import br.gov.ce.pge.gestao.dto.request.CondicaoAcessoRequestDtoTest;
import br.gov.ce.pge.gestao.entity.CondicaoAcesso;
import br.gov.ce.pge.gestao.entity.CondicaoAcessoTest;
import br.gov.ce.pge.gestao.repository.CondicaoAcessoRepository;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
class CondicaoAcessoServiceImplTest {

	@Mock
	private CondicaoAcessoRepository repository;

	@Mock
	private CondicaoAcessoDao condicaoAcessoDao;
	
	@Autowired
	@InjectMocks
	private CondicaoAcessoServiceImpl service;

	@Mock
	private UsuarioConsultaServiceImpl usuarioService;
	
	@Test
	public void save_sucesso() {
		when(this.repository.save(any())).thenReturn(CondicaoAcessoTest.getCondicaoAcesso());

		var model = service.save(CondicaoAcessoRequestDtoTest.getRequest());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(1, model.getAlteracaoSenha());
		assertEquals(1, model.getBloqueioAutomatico());
		assertEquals("12:34", model.getEncerramentoSessao());
		assertEquals(1, model.getSenhasCadastradas());
		assertEquals(1, model.getTentativasInvalidas());
		
		verify(repository, times(1)).save(any(CondicaoAcesso.class));
	}

	@Test
	public void save_com_intervalo_bloqueio() {
		when(this.repository.save(any())).thenReturn(CondicaoAcessoTest.getCondicaoAcesso());
		var request = CondicaoAcessoRequestDtoTest.getRequest();
		request.setIntervaloBloqueio("1");
		var model = service.save(request);

		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(1, model.getAlteracaoSenha());
		assertEquals(1, model.getBloqueioAutomatico());
		assertEquals("12:34", model.getEncerramentoSessao());
		assertEquals(1, model.getSenhasCadastradas());
		assertEquals(1, model.getTentativasInvalidas());
		assertEquals(1, model.getIntervaloBloqueio());

		verify(repository, times(1)).save(any(CondicaoAcesso.class));
	}

	@Test
	public void save_sucesso_com_condicao_acesso_ja_existente() {
		List<CondicaoAcesso> listaCondicaoAcesso = CondicaoAcessoTest.getListCondicaoAcesso();
		when(this.repository.findAll()).thenReturn(listaCondicaoAcesso);
		when(this.repository.save(any())).thenReturn(CondicaoAcessoTest.getCondicaoAcesso());

		var model = service.save(CondicaoAcessoRequestDtoTest.getRequest());

		assertEquals(listaCondicaoAcesso.size(), 1);
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(1, model.getAlteracaoSenha());
		assertEquals(1, model.getBloqueioAutomatico());
		assertEquals("12:34", model.getEncerramentoSessao());
		assertEquals(1, model.getSenhasCadastradas());
		assertEquals(1, model.getTentativasInvalidas());

		verify(repository, times(1)).findAll();
		verify(repository, times(1)).save(any(CondicaoAcesso.class));
	}
	
	@Test
	public void update_sucesso() throws JsonProcessingException {
		when(this.repository.findById(any())).thenReturn(Optional.of(CondicaoAcessoTest.getCondicaoAcesso()));
		when(this.repository.save(any())).thenReturn(CondicaoAcessoTest.getCondicaoAcessoUpdate());

		var request = CondicaoAcessoRequestDtoTest.getRequest();
		request.setIntervaloBloqueio("1");
		var model = service.update(any(), request);
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(2, model.getBloqueioAutomatico());
		assertEquals(14, model.getAlteracaoSenha());
		assertEquals("43:21", model.getEncerramentoSessao());
		assertEquals(3, model.getTentativasInvalidas());
		assertEquals(15, model.getSenhasCadastradas());
		assertEquals(1, model.getIntervaloBloqueio());

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(CondicaoAcesso.class));
	}
	
	@Test
	public void findById_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(CondicaoAcessoTest.getCondicaoAcesso()));
		
		var model = service.findById(any());
		
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals(1, model.getAlteracaoSenha());
		assertEquals(1, model.getBloqueioAutomatico());
		assertEquals("12:34", model.getEncerramentoSessao());
		assertEquals(1, model.getSenhasCadastradas());
		assertEquals(1, model.getTentativasInvalidas());
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	public void findByAll_sucesso() {
		when(this.repository.findAll()).thenReturn(CondicaoAcessoTest.getListCondicaoAcesso());
		
		var lista = service.findAll();
		
		assertNotNull(lista);
		assertEquals(lista.size(), 1);

		verify(repository, times(1)).findAll();
	}
	
	@Test
	public void delete_sucesso() {
		when(this.repository.findById(any())).thenReturn(Optional.of(CondicaoAcessoTest.getCondicaoAcesso()));
		
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

		Assertions.assertEquals("Condição de acesso não existe", error.getMessage());
		
		verify(repository, times(1)).findById(any());
	}

}
