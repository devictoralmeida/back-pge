package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.constants.MessageUsuarioConstant;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.entity.TermoCondicao;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import br.gov.ce.pge.gestao.service.*;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ListaUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.TemplateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Value("${url.sistema.portalOrigens}")
	private String urlPortalOrigens;

	@Value("${url.sistema.portalDivida}")
	private String urlPortalDivida;

	private final UsuarioRepository repository;

	private final UsuarioConsultaService consultaService;

	private final SistemaService sistemaService;

	private final PerfilAcessoService perfilAcessoService;

	private final TermoCondicaoService termoService;

	private final EnviarEmailService enviarEmailService;

	private final LdapService ldapService;

	private final PortalColaboradorService portalService;

	public UsuarioServiceImpl(UsuarioRepository repository, UsuarioConsultaService consultaService, SistemaService sistemaService,
							  PerfilAcessoService perfilAcessoService, TermoCondicaoService termoService, EnviarEmailService enviarEmailService,
							  LdapService ldapService, PortalColaboradorService portalService) {
		this.repository = repository;
		this.consultaService = consultaService;
		this.sistemaService = sistemaService;
		this.perfilAcessoService = perfilAcessoService;
		this.termoService = termoService;
		this.enviarEmailService = enviarEmailService;
		this.ldapService = ldapService;
		this.portalService = portalService;
	}

	@Override
	public void save(@Valid UsuarioRequestDto request, String nomeUsuario) {
		verificaUsuarioCadastrado(request.getCpf());
		verificaUsuarioCadastradoEmail(request.getEmail());
		verificaUsuarioNoPortalColaborador(request);
		verificaUsuarioRedeInterna(request);


		var usuario = new Usuario();

		BeanUtils.copyProperties(request, usuario);

		var listaSistema = request.getSistemas().stream().map(this.sistemaService::findById).collect(Collectors.toList());
		var listaPerfilAcesso = request.getPerfisAcessos().stream().map(this.perfilAcessoService::findByIdModel).collect(Collectors.toList());

		verificaSePerfilAcessoSelecionadoContemNoSistemaSelecionado(listaPerfilAcesso, listaSistema);

		verificaOrgaoParaUsuario(request.getTipoUsuario(), request.getOrgao());

		usuario.setSistemas(listaSistema);
		usuario.setPerfisAcessos(listaPerfilAcesso);

		usuario.setTermos(getTermos(request.getSistemas()));
		usuario.setTokenAcessoUnico(UUID.randomUUID());

		usuario.setNomeUsuario(nomeUsuario);

		this.repository.save(usuario);

		this.tratarEmail(usuario);
	}

	private void verificaUsuarioNoPortalColaborador(UsuarioRequestDto request) {
		if(TipoUsuario.INTERNO.equals(request.getTipoUsuario())) {
			portalService.getColaborador(request.getCpf());
		}
	}

	private void tratarEmail(Usuario usuario) {
		usuario.getSistemas().stream().forEach(sistema -> {
			var template = MessageUsuarioConstant.TEMPLATE_CADASTRO_SENHA_EXTERNO_FTLH;
			var url = "cadastrar-senha/" + usuario.getId() + "?token=" + usuario.getTokenAcessoUnico();
			var assunto = "Confirmação de Cadastro " + sistema.getNome();

			if(TipoUsuario.INTERNO.equals(usuario.getTipoUsuario())) {
				template = MessageUsuarioConstant.TEMPLATE_CADASTRO_SENHA_INTERNO_FTLH;
				url = "";
			}

			Map<String, Object> params = getParams(usuario, sistema, url);

			try {
				enviarEmailService.enviarEmailTemplate(assunto, usuario.getEmail(), template, params);
			} catch (IOException | MessagingException | TemplateException e) {
				throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_ENVIAR_EMAIL);
			}
		});
	}

	private Map<String, Object> getParams(Usuario usuario, Sistema sistema, String url) {
		Map<String, Object> params = new HashMap<>();
		params.put("nomeUsuario", usuario.getNome());
		params.put("nomeSistema", sistema.getNome());

		var urlAmbiente = "";

		if(MessageUsuarioConstant.PORTAL_DIVIDA_ATIVA.equals(sistema.getNome())) {
			urlAmbiente = this.urlPortalDivida;
		}else if(MessageUsuarioConstant.PORTAL_DAS_ORIGENS.equals(sistema.getNome())){
			urlAmbiente = this.urlPortalOrigens;
		}else {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_SISTEMA_INEXISTENTE);
		}

		params.put("urlCadastro", urlAmbiente + "autenticacao/" + url);
		params.put("enderecoSistema", urlAmbiente + "autenticacao");
		return params;
	}

	private List<TermoCondicao> getTermos(List<UUID> idsSistemas) {
		var termos = this.termoService.findByNomeSistema();
		return termos.stream().filter(termo -> idsSistemas.contains(termo.getSistema().getId())).collect(Collectors.toList());
	}

	private void verificaOrgaoParaUsuario(TipoUsuario tipoUsuario, String orgao) {
		if (tipoUsuario.equals(TipoUsuario.INTERNO) && !orgao.equals(MessageUsuarioConstant.PGE)) {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_ORGAO_USUARIO_INTERNO);
		}

		if (tipoUsuario.equals(TipoUsuario.EXTERNO) && orgao.equals(MessageUsuarioConstant.PGE)) {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_NAO_PODE_SER_PGE);
		}
	}

	private void verificaUsuarioCadastrado(String cpf) {
		var usuario = this.consultaService.findByCpf(cpf);

		if(Objects.nonNull(usuario))
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_CPF_EXISTENTE);
	}

	private void verificaUsuarioCadastradoEmail(String email) {
		var usuario = this.consultaService.findByEmail(email);

		if(Objects.nonNull(usuario))
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_EMAIL_EXISTENTE);
	}

	private void verificaSePerfilAcessoSelecionadoContemNoSistemaSelecionado(List<PerfilAcesso> listaPerfilAcesso, List<Sistema> listaSistema) {
		for (Sistema sistema : listaSistema) {
			boolean sistemaEncontrado = listaPerfilAcesso.stream()
					.anyMatch(perfil -> perfil.getSistemas().contains(sistema));

			if (!sistemaEncontrado)
				throw new NegocioException(sistema.getNome() + MessageUsuarioConstant.MSG_SISTEMA_NAO_RELACIONADO_PERFIL);
		}
	}

	private void verificaUsuarioRedeInterna(UsuarioRequestDto request) {
		if(request.getUsuarioRede() != null && TipoUsuario.INTERNO.equals(request.getTipoUsuario())) {
			var usuario = this.repository.findByUsuarioRede(request.getUsuarioRede());

			if(usuario != null) {
				throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIOREDE_EXISTENTE);
			}

			if(!ldapService.userExists(request.getUsuarioRede())) {
				throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIOREDE_NAO_ENCONTRADO);
			}
		}
	}

	@Override
	public void update(UUID id, @Valid UsuarioRequestDto request, String nomeUsuario) throws JsonProcessingException {

		var usuarioModel = this.consultaService.findByIdModel(id);

		if (verificaObjetosIguais(request, usuarioModel)) {
			return;
		}

		verificaUsuarioNoPortalColaborador(request);

		if(!usuarioModel.getCpf().equals(request.getCpf())) {
			verificaUsuarioCadastrado(request.getCpf());
		}

		if(!usuarioModel.getEmail().equals(request.getEmail())) {
			verificaUsuarioCadastradoEmail(request.getEmail());
		}

		if(usuarioModel.getUsuarioRede() != null
				&& !usuarioModel.getUsuarioRede().equals(request.getUsuarioRede())
				|| request.getUsuarioRede() != null && usuarioModel.getUsuarioRede() == null) {
			verificaUsuarioRedeInterna(request);
		}

		var listaSistema = request.getSistemas().stream().map(this.sistemaService::findById).collect(Collectors.toList());
		var listaPerfilAcesso = request.getPerfisAcessos().stream().map(this.perfilAcessoService::findByIdModel).collect(Collectors.toList());

		verificaSePerfilAcessoSelecionadoContemNoSistemaSelecionado(listaPerfilAcesso, listaSistema);

		verificaOrgaoParaUsuario(request.getTipoUsuario(), request.getOrgao());

		CustomRevisionListener.setDadosAntigos(usuarioModel.toStringMapper());
		CustomRevisionListener.setNomeUsuario(nomeUsuario);
		BeanUtils.copyProperties(request, usuarioModel);

		usuarioModel.setSistemas(listaSistema);
		usuarioModel.setPerfisAcessos(listaPerfilAcesso);

		usuarioModel.setTermos(getTermos(request.getSistemas()));

		usuarioModel.setNomeUsuario(nomeUsuario);

		this.repository.save(usuarioModel);
	}

	@Override
	public void delete(UUID id) {
		var usuario = this.consultaService.findByIdModel(id);

		if(usuario.getDataUltimoAcesso() != null && !usuario.getSituacao().equals(SituacaoUsuario.INATIVA)) {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_COM_ACESSO);
		}

		this.repository.delete(usuario);
	}

	@Override
	public void ultimoAcesso(UUID id) {
		var usuario = consultaService.findByIdModel(id);
		usuario.setDataUltimoAcesso(LocalDateTime.now());
		this.repository.save(usuario);
	}

	@Override
	public void bloquearUsuario(UUID id) {
		var usuario = consultaService.findByIdModel(id);
		usuario.setSituacao(SituacaoUsuario.BLOQUEADA);
		usuario.setHoraBloqueio(LocalDateTime.now());
		this.repository.save(usuario);
	}

	@Override
	public void aceitarTermo(UUID id, String nomeSistema) {
		var usuario = consultaService.findByIdModel(id);

		if(nomeSistema.equals(MessageUsuarioConstant.PORTAL_DIVIDA_ATIVA)) {
			usuario.setDataAceiteTermoPortalDivida(LocalDateTime.now());
		}else if(nomeSistema.equals(MessageUsuarioConstant.PORTAL_DAS_ORIGENS)){
			usuario.setDataAceiteTermoPortalOrigens(LocalDateTime.now());
		}else {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_SISTEMA_INEXISTENTE);
		}
		this.repository.save(usuario);
	}

	private boolean verificaObjetosIguais(UsuarioRequestDto request, Usuario usuarioModel) {
		return Objects.equals(request.getNome(), usuarioModel.getNome()) &&
				Objects.equals(request.getCpf(), usuarioModel.getCpf()) &&
				Objects.equals(request.getEmail(), usuarioModel.getEmail()) &&
				Objects.equals(request.getTipoUsuario(), usuarioModel.getTipoUsuario()) &&
				Objects.equals(request.getUsuarioRede(), usuarioModel.getUsuarioRede()) &&
				Objects.equals(request.getArea(), usuarioModel.getArea()) &&
				Objects.equals(request.getOrgao(), usuarioModel.getOrgao()) &&
				ListaUtil.isEquals(request.getSistemas(), usuarioModel.getSistemas().stream().map(s -> s.getId()).collect(Collectors.toList())) &&
				ListaUtil.isEquals(request.getPerfisAcessos(), usuarioModel.getPerfisAcessos().stream().map(pa -> pa.getId()).collect(Collectors.toList())) &&
				Objects.equals(request.getSituacao(), usuarioModel.getSituacao());
	}

}
