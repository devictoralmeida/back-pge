package br.gov.ce.pge.gestao.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.gov.ce.pge.gestao.client.PortalColaboradorFeingClient;
import br.gov.ce.pge.gestao.dto.response.ColaboradorResponseDto;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import feign.FeignException;

@Component
public class SincronizarUsuarioInternoWorker {

    private final PortalColaboradorFeingClient portalColaboradorClient;

    private final UsuarioRepository repository;

    private static final Logger LOGGER = LogManager.getLogger(SincronizarUsuarioInternoWorker.class);
    
    public SincronizarUsuarioInternoWorker(PortalColaboradorFeingClient portalColaboradorClient, UsuarioRepository repository) {
    	this.portalColaboradorClient = portalColaboradorClient;
    	this.repository = repository;
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void executeWorker() {

        try {
            var usuariosAtivos = repository.findUsuarioAtivo();

            var cpfs = usuariosAtivos.stream().map(Usuario::getCpf).toList();

            var usuarios = portalColaboradorClient.findByListCpf(cpfs);

            usuariosAtivos.stream().forEach(usuarioModel -> {
                var usuarioFiltrado = usuarios.getBody().stream().filter(usuario -> usuario.getCpf().equals(usuarioModel.getCpf())).toList();

                LOGGER.info("TOTAL USUARIOS PARA PROCESSAR >>>>>> " + usuarioFiltrado.size());

                if(!usuarioFiltrado.isEmpty()) {
                    var usuarioResponse = usuarioFiltrado.get(0);
                    salvarUsuario(usuarioModel, usuarioResponse);
                }else {
                    LOGGER.info("ERROR SEM USUARIO >>>>>> " + usuarioFiltrado.size());
                }
            });
        } catch (FeignException feignException) {
            LOGGER.info("ERROR AO PEGAR DADOS DO PORTAL DO COLABORADOR >>>>>> " + feignException.getMessage());
        }
    }

    private void salvarUsuario(Usuario usuario, ColaboradorResponseDto usuarioResponse) {
        var flSalvar = false;

        if(!usuario.getNome().equals(usuarioResponse.getNome())) {
            usuario.setNome(usuarioResponse.getNome());
            flSalvar = true;
        }
        if(!usuario.getArea().equals(usuarioResponse.getArea())) {
            usuario.setArea(usuarioResponse.getArea());
            flSalvar = true;
        }
        if(usuarioResponse.getDataDesligamento() != null) {
            usuario.setSituacao(SituacaoUsuario.INATIVA);
            flSalvar = true;
        }

        if(flSalvar) {
            LOGGER.info("SALVANDO DADOS DE USUARIO >>>>>> " + usuario.getNome());
            this.repository.save(usuario);
        }
    }

}
