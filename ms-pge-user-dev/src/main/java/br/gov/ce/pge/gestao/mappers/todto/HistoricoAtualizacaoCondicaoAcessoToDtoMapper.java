package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.response.AuditoriaCondicaoAcessoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.CondicaoAcesso;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class HistoricoAtualizacaoCondicaoAcessoToDtoMapper  extends HistoricoAtualizacaoMapperCommons implements AuditoriaToDtoMapper {

    @Override
    public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object  model) {
        var modelAtual = (AuditoriaCondicaoAcessoDto) model;

        String dadosAntigos = modelAtual.getDadosAntigos();

        if (dadosAntigos != null) {
            var modelAntigo =  createObjeto(dadosAntigos);

            List<Document> documents = new ArrayList<>();
            adicionarCampoAlterado(documents, "Alteração senha", modelAntigo.getAlteracaoSenha() , modelAtual.getAlteracaoSenha());
            adicionarCampoAlterado(documents, "Bloqueio automático", modelAntigo.getBloqueioAutomatico() , modelAtual.getBloqueioAutomatico());
            adicionarCampoAlterado(documents, "Encerramento sessão", modelAntigo.getEncerramentoSessao() , modelAtual.getEncerramentoSessao());
            adicionarCampoAlterado(documents, "Senhas cadastradas", modelAntigo.getSenhasCadastradas() , modelAtual.getSenhasCadastradas());
            adicionarCampoAlterado(documents, "Tentativas inválidas", modelAntigo.getTentativasInvalidas() , modelAtual.getTentativasInvalidas());

            return preencherHistorico(modelAtual, documents);
        }

        return List.of();
    }

    private static CondicaoAcesso createObjeto(String stringCondicaoAcessoDadosAntigos) {

        var model = new CondicaoAcesso();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var condicaoAcesso = objectMapper.readValue(stringCondicaoAcessoDadosAntigos, CondicaoAcesso.class);
            model.setId(condicaoAcesso.getId());
            model.setTentativasInvalidas(condicaoAcesso.getTentativasInvalidas());
            model.setEncerramentoSessao(condicaoAcesso.getEncerramentoSessao());
            model.setSenhasCadastradas(condicaoAcesso.getSenhasCadastradas());
            model.setAlteracaoSenha(condicaoAcesso.getAlteracaoSenha());
            model.setBloqueioAutomatico(condicaoAcesso.getBloqueioAutomatico());
        } catch (JsonMappingException e) {
            throw new NegocioException(MessageCommonsContanst.ERRO_MAPEAR_JSON);
        } catch (JsonProcessingException e) {
            throw new NegocioException(MessageCommonsContanst.ERRO_PROCESSAR_JSON);
        }

        return model;
    }


}
