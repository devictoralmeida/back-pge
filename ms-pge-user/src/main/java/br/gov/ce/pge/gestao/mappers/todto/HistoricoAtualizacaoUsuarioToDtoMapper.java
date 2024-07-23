package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.response.AuditoriaUsuarioDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class HistoricoAtualizacaoUsuarioToDtoMapper extends HistoricoAtualizacaoMapperCommons implements AuditoriaToDtoMapper{
    @Override
    public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object model) {

        var modelAtual = (AuditoriaUsuarioDto) model;

        String dadosAntigos = modelAtual.getDadosAntigos();

        var modelAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new Usuario();

        List<Document> documents = new ArrayList<>();
        adicionarCampoAlterado(documents, "Nome do Usuário", modelAntigo.getNome() , modelAtual.getNome());
        adicionarCampoAlterado(documents, "Situação Usuário", modelAntigo.getSituacao(), modelAtual.getSituacaoUsuario());
        adicionarCampoAlterado(documents, "Tipo Usuário", modelAntigo.getTipoUsuario(), modelAtual.getTipoUsuario());
        adicionarCampoAlterado(documents, "Rede Usuário", modelAntigo.getUsuarioRede(), modelAtual.getUsuarioRede());
        adicionarCampoAlterado(documents, "Órgão Usuário", modelAntigo.getOrgao(), modelAtual.getOrgao());
        adicionarCampoAlterado(documents, "Área Usuário", modelAntigo.getArea(), modelAtual.getArea());
        adicionarCampoAlterado(documents, "Cpf Usuário", modelAntigo.getCpf(), modelAtual.getCpf());
        adicionarCampoAlterado(documents, "E-mail Usuário", modelAntigo.getEmail(), modelAtual.getEmail());

        Map<String, Object> listas = getListasIds(modelAtual);

        adicionarCampoAlteradoSistemas(documents, modelAntigo.getSistemas(), listas);
        adicionarCampoAlteradoPerfis(documents, modelAntigo.getPerfisAcessos(), listas);

        return preencherHistorico(modelAtual, documents);


    }

    private static void adicionarCampoAlteradoSistemas(List<Document> documents, List<Sistema> listaSistemaAntigos, Map<String, Object> listas) {
        String idsAdicionados = (String) listas.get("idsSistemaAdicionados");
        String idsRemovidos = (String) listas.get("idsSistemaRemovidos");

        Document document = new Document();

        Set<String> idsAntigos = listaSistemaAntigos != null ? listaSistemaAntigos.stream()
                .map(model -> model.getId().toString())
                .collect(Collectors.toSet()) : new HashSet<>();

        Set<String> idsAux = new HashSet<>(idsAntigos);

        if (idsRemovidos != null && !idsRemovidos.isEmpty()) {
            idsAux.removeAll(Arrays.asList(idsRemovidos.split(",")));
        }

        if (idsAdicionados != null && !idsAdicionados.isEmpty()) {
            idsAux.addAll(Arrays.asList(idsAdicionados.split(",")));
        }

        if (!idsAux.equals(idsAntigos)) {
            apendarCampoAlterado(document, "Sistemas", idsAntigos, idsAux);
            documents.add(document);
        }
    }

    private static void adicionarCampoAlteradoPerfis(List<Document> documents, List<PerfilAcesso> listaPerfisAntigos, Map<String, Object> listas) {
        String idsAdicionados = (String) listas.get("idsPerfisAdicionados");
        String idsRemovidos = (String) listas.get("idsPerfisRemovidos");

        Document document = new Document();

        Set<String> idsAntigos = listaPerfisAntigos != null ? listaPerfisAntigos.stream()
                .map(model -> model.getId().toString())
                .collect(Collectors.toSet()) : new HashSet<>();

        Set<String> idsAux = new HashSet<>(idsAntigos);

        if (idsRemovidos != null && !idsRemovidos.isEmpty()) {
            idsAux.removeAll(Arrays.asList(idsRemovidos.split(",")));
        }

        if (idsAdicionados != null && !idsAdicionados.isEmpty()) {
            idsAux.addAll(Arrays.asList(idsAdicionados.split(",")));
        }

        if (!idsAux.equals(idsAntigos)) {
            apendarCampoAlterado(document, "Perfis Acessos", idsAntigos, idsAux);
            documents.add(document);
        }
    }

    private static Map<String, Object> getListasIds(AuditoriaUsuarioDto model) {
        Map<String, Object> listas = new HashMap<>();
        listas.put("idsPerfisAdicionados", model.getIdsPerfisAdicionados());
        listas.put("idsPerfisRemovidos", model.getIdsPerfisRemovidos());
        listas.put("idsSistemaAdicionados", model.getIdsSistemaAdicionados());
        listas.put("idsSistemaRemovidos", model.getIdsSistemaRemovidos());
        return listas;
    }

    private static Usuario createObjeto(String stringUsuarioDadosAntigos) {

        var model = new Usuario();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var usuario = objectMapper.readValue(stringUsuarioDadosAntigos, Usuario.class);
            model.setId(usuario.getId());
            model.setNome(usuario.getNome());
            model.setSituacao(usuario.getSituacao());
            model.setEmail(usuario.getEmail());
            model.setTipoUsuario(usuario.getTipoUsuario());
            model.setOrgao(usuario.getOrgao());
            model.setArea(usuario.getArea());
            model.setCpf(usuario.getCpf());
            model.setPerfisAcessos(usuario.getPerfisAcessos());
            model.setSistemas(usuario.getSistemas());
            model.setUsuarioRede(usuario.getUsuarioRede());
        } catch (JsonMappingException e) {
            throw new NegocioException(MessageCommonsContanst.ERRO_MAPEAR_JSON);
        } catch (JsonProcessingException e) {
            throw new NegocioException(MessageCommonsContanst.ERRO_PROCESSAR_JSON);
        }

        return model;

    }
}
