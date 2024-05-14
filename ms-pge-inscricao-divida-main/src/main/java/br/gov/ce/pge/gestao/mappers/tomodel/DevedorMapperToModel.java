package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.DevedorRequestDto;
import br.gov.ce.pge.gestao.entity.Devedor;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.beans.BeanUtils;

public class DevedorMapperToModel {

    private DevedorMapperToModel() {
    }

    public static Devedor converter(DevedorRequestDto request) {
        verificaTipoPessoaDevedor(request);
        var devedor = new Devedor();
        BeanUtils.copyProperties(request, devedor, "dataCriacao", "nomeUsuarioCadastro");
        return devedor;
    }

    public static void verificaTipoPessoaDevedor(DevedorRequestDto request) {
        if (request.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA && request.getCgf() == null) {
            throw new NegocioException("Favor informar o CGF.");
        }
    }
}
