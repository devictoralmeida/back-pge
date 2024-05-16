package br.gov.ce.pge.gestao.job;

import br.gov.ce.pge.gestao.client.PortalColaboradorFeingClient;
import br.gov.ce.pge.gestao.dto.response.ColaboradorResponseDtoTest;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.jobs.SincronizarUsuarioInternoWorker;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({ MockitoExtension.class })
public class SincronizarUsuarioInternoWorkerTest {

    @InjectMocks
    private SincronizarUsuarioInternoWorker worker;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PortalColaboradorFeingClient client;

    @Test
    public void test_execute_worker() {
        when(usuarioRepository.findUsuarioAtivo()).thenReturn(Arrays.asList(UsuarioTest.getColaboradorInterno()));
        when(client.findByListCpf(any()))
                .thenReturn(new ResponseEntity<>(Arrays.asList(ColaboradorResponseDtoTest.getColaboradorResponseDto()), HttpStatus.OK));
        worker.executeWorker();

        verify(usuarioRepository, times(1)).findUsuarioAtivo();
        verify(client, times(1)).findByListCpf(any());
    }

    @Test
    public void test_execute_worker_sem_usuario() {
        when(usuarioRepository.findUsuarioAtivo()).thenReturn(Arrays.asList(UsuarioTest.getUsuario()));
        when(client.findByListCpf(any()))
                .thenReturn(new ResponseEntity<>(Arrays.asList(ColaboradorResponseDtoTest.getColaboradorResponseDto()), HttpStatus.OK));
        worker.executeWorker();

        verify(usuarioRepository, times(1)).findUsuarioAtivo();
        verify(client, times(1)).findByListCpf(any());
    }

    @Test
    public void test_execute_worker_error() {
        when(usuarioRepository.findUsuarioAtivo()).thenReturn(Arrays.asList(UsuarioTest.getColaboradorInterno()));
        when(client.findByListCpf(any()))
                .thenThrow(FeignException.class);
        worker.executeWorker();

        verify(usuarioRepository, times(1)).findUsuarioAtivo();
        verify(client, times(1)).findByListCpf(any());
    }

}
