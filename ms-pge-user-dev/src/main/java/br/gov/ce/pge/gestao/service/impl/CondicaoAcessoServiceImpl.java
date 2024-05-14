package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.CondicaoAcessoRequestDto;
import br.gov.ce.pge.gestao.entity.CondicaoAcesso;
import br.gov.ce.pge.gestao.repository.CondicaoAcessoRepository;
import br.gov.ce.pge.gestao.service.CondicaoAcessoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidarHoraMinutoUtil;

@Service
@Qualifier("condicaoAcessoServiceImpl")
public class CondicaoAcessoServiceImpl implements CondicaoAcessoService {

    private final CondicaoAcessoRepository repository;

    public CondicaoAcessoServiceImpl(CondicaoAcessoRepository repository) {
    	this.repository = repository;
    }

    @Override
    public CondicaoAcesso save(@Valid CondicaoAcessoRequestDto request) {
        ValidarHoraMinutoUtil.validarHorario(request.getEncerramentoSessao());

        CondicaoAcesso condicaoAcessoModel;

        List<CondicaoAcesso> listaCondicaoAcesso = this.repository.findAll();

        if (!listaCondicaoAcesso.isEmpty()) {
            condicaoAcessoModel = listaCondicaoAcesso.get(0);
            BeanUtils.copyProperties(request, condicaoAcessoModel, "id");
        } else {
            condicaoAcessoModel = new CondicaoAcesso();
            BeanUtils.copyProperties(request, condicaoAcessoModel);
        }

        propriedadesParaInt(request, condicaoAcessoModel);

        condicaoAcessoModel.setIntervaloBloqueio(MessageCommonsContanst.INTERVALO_BLOQUEIO);

        condicaoAcessoModel = this.repository.save(condicaoAcessoModel);

        return condicaoAcessoModel;

    }

    public CondicaoAcesso findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsContanst.CONDICAO_ACESSO_NAO_ENCONTRADO));
    }

    @Override
    public List<CondicaoAcesso> findAll() {
        return this.repository.findAll();
    }

    public CondicaoAcesso update(UUID id, @Valid CondicaoAcessoRequestDto request) {
        ValidarHoraMinutoUtil.validarHorario(request.getEncerramentoSessao());

        CondicaoAcesso condicaoAcessoModel = findById(id);
        BeanUtils.copyProperties(request, condicaoAcessoModel, "id");

        propriedadesParaInt(request, condicaoAcessoModel);

        condicaoAcessoModel.setIntervaloBloqueio(MessageCommonsContanst.INTERVALO_BLOQUEIO);

        return this.repository.save(condicaoAcessoModel);
    }

    public void delete(UUID id) {
        var condicaoAcessoModel = this.findById(id);

        this.repository.delete(condicaoAcessoModel);
    }

    public static void propriedadesParaInt(CondicaoAcessoRequestDto request, CondicaoAcesso condicaoAcessoModel) {
        condicaoAcessoModel.setBloqueioAutomatico(Integer.parseInt(request.getBloqueioAutomatico()));
        condicaoAcessoModel.setAlteracaoSenha(Integer.parseInt(request.getAlteracaoSenha()));
        condicaoAcessoModel.setTentativasInvalidas(Integer.parseInt(request.getTentativasInvalidas()));
        condicaoAcessoModel.setSenhasCadastradas(Integer.parseInt(request.getSenhasCadastradas()));
    }

}
