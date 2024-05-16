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

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dao.UsuarioDao;
import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.UsuarioFilterResponseDtoTest;
import br.gov.ce.pge.gestao.entity.AuditoriaTest;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import br.gov.ce.pge.gestao.service.SistemaService;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

@ExtendWith({ MockitoExtension.class })
class UsuarioConsultaServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    @Autowired
    private UsuarioConsultaServiceImpl consultaService;

    @Mock
    private SistemaService sistemaService;

    @Mock
    private PerfilAcessoServiceImpl perfilAcessoService;

    @Mock
    private UsuarioDao usuarioDao;

    
    @Test
    void findBy_idModel_sucesso() {
        when(repository.findById(any())).thenReturn(Optional.of(UsuarioTest.getUsuario()));

        var model = consultaService.findById(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));

        assertNotNull(model);
        assertNotNull(model.getId());
        assertEquals("teste usuario", model.getNome());
        assertEquals(SituacaoUsuario.ATIVA, model.getSituacao());
        assertEquals(1, model.getSistemas().size());
        assertEquals(1, model.getPerfisAcessos().size());

        verify(repository, times(1)).findById(any());
    }
    
    @Test
    void findBy_idModel_error() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception error = Assertions.assertThrows(NegocioException.class, () -> {
        	consultaService.findByIdModel(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
		});
        
        assertEquals("Usuário não encontrado", error.getMessage());

        verify(repository, times(1)).findById(any());
    }
    
    @Test
    void findBy_cpf_sucesso() {
        when(repository.findByCpf(any())).thenReturn(UsuarioTest.getUsuario());

        var model = consultaService.findByCpf("05459813360");

        assertNotNull(model);
        assertNotNull(model.getId());
        assertEquals("teste usuario", model.getNome());
        assertEquals(SituacaoUsuario.ATIVA, model.getSituacao());
        assertEquals(1, model.getSistemas().size());
        assertEquals(1, model.getPerfisAcessos().size());

        verify(repository, times(1)).findByCpf(any());
    }
    
    @Test
    void findAll_sucesso() {
        when(repository.findAll()).thenReturn(List.of(UsuarioTest.getUsuario()));

        var lista = consultaService.findAll();
        
        var model = lista.get(0);

        assertNotNull(model);
        assertNotNull(model.getId());
        assertEquals("teste usuario", model.getNome());
        assertEquals(SituacaoUsuario.ATIVA, model.getSituacao());
        assertEquals(1, model.getSistemas().size());
        assertEquals(1, model.getPerfisAcessos().size());

        verify(repository, times(1)).findAll();
    }

    @Test
    void byFilter_sucesso() {
        when(usuarioDao.countfindByFilter(any())).thenReturn(1);
        when(usuarioDao.findByFilter(any())).thenReturn(UsuarioFilterResponseDtoTest.getListUsuarioFilter());

        var filter = consultaService.findByFilter(UsuarioFilterRequestDtoTest.getRequestFilter(), 1, "nome");

        assertNotNull(filter);
        assertEquals(1, filter.getTotalPaginas());
        assertEquals(1, filter.getTotalRegistros());
        assertEquals("teste usuario", filter.getList().get(0).getNome());
        assertEquals(SituacaoUsuario.ATIVA, filter.getList().get(0).getSituacao());
        assertEquals("area teste", filter.getList().get(0).getArea());
        assertEquals("orgao teste", filter.getList().get(0).getOrgao());
        assertEquals(TipoUsuario.EXTERNO, filter.getList().get(0).getTipoUsuario());
        assertEquals("01234567890", filter.getList().get(0).getCpf());

    }

    @Test
    void find_historys_sucesso() throws JsonProcessingException {
        when(this.usuarioDao.countHistorysUpdates(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"))).thenReturn(1);
        when(this.usuarioDao.findHistorysUpdates(any(), eq(0L), eq(10L))).thenReturn(AuditoriaTest.getListAuditoriaUsuario());

        var filter = consultaService.findHistorys(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"), 1);

        @SuppressWarnings("unchecked")
        List<Document> documentos = (List<Document>) filter.getList().get(0).getDadosAlterados().get(0);

        Document info = documentos.get(0);

        assertNotNull(filter);
        assertEquals(1, filter.getPaginaAtual());
        assertEquals(1, filter.getTotalPaginas());
        assertEquals(1, filter.getTotalRegistros());
        assertEquals("Situação Usuário", info.get("campoAtualizado"));
        assertEquals(SituacaoUsuario.ATIVA.toString(), info.get("valorAntigo"));
        assertEquals(SituacaoUsuario.INATIVA.toString(), info.get("valorNovo"));

    }
    
    @Test
    void find_historys_vazio_error() throws JsonProcessingException {
        when(this.usuarioDao.countHistorysUpdates(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"))).thenReturn(0);
        when(this.usuarioDao.findHistorysUpdates(any(), eq(0L), eq(10L))).thenReturn(List.of());

        Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
        	consultaService.findHistorys(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"), 1);
		});
        
        assertEquals("O usuário selecionado não possui histórico de edições.", error.getMessage());

        verify(usuarioDao, times(1)).countHistorysUpdates(any());
        verify(usuarioDao, times(1)).findHistorysUpdates(any(), any(), any());

    }
    
    @Test
    void find_historys_null_error() throws JsonProcessingException {
        when(this.usuarioDao.countHistorysUpdates(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"))).thenReturn(0);
        when(this.usuarioDao.findHistorysUpdates(any(), eq(0L), eq(10L))).thenReturn(null);

        Exception error = Assertions.assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
        	consultaService.findHistorys(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"), 1);
		});
        
        assertEquals("O usuário selecionado não possui histórico de edições.", error.getMessage());

        verify(usuarioDao, times(1)).countHistorysUpdates(any());
        verify(usuarioDao, times(1)).findHistorysUpdates(any(), any(), any());

    }

    @Test
    void findBy_email_sucesso() {
        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());

        var model = consultaService.findByEmail("teste@pge.ce.gov.br");

        assertNotNull(model);
        assertNotNull(model.getId());
        assertEquals("teste usuario", model.getNome());
        assertEquals(SituacaoUsuario.ATIVA, model.getSituacao());
        assertEquals(1, model.getSistemas().size());
        assertEquals(1, model.getPerfisAcessos().size());

        verify(repository, times(1)).findByEmail(any());
    }

    @Test
    void findBy_identificador_sucesso() {
        when(repository.findByIdentificador(any())).thenReturn(UsuarioTest.getUsuario());

        var model = consultaService.findByIdentificador("00000000000");

        assertNotNull(model);
        assertNotNull(model.getId());
        assertEquals("teste usuario", model.getNome());
        assertEquals(SituacaoUsuario.ATIVA, model.getSituacao());
        assertEquals(1, model.getSistemas().size());
        assertEquals(1, model.getPerfisAcessos().size());

        verify(repository, times(1)).findByIdentificador(any());
    }

}
