package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.CorresponsavelRequestDto;
import br.gov.ce.pge.gestao.entity.Corresponsavel;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CorresponsavelMapperToModel {

    private CorresponsavelMapperToModel() {
    }

    public static List<Corresponsavel> converterList(List<CorresponsavelRequestDto> requestList, Inscricao inscricao) {
        return requestList.stream().map(dto -> CorresponsavelMapperToModel.converter(dto, inscricao)).collect(Collectors.toList());
    }


    public static List<Corresponsavel> converterListUpdate(List<CorresponsavelRequestDto> requestList, Inscricao inscricao) {
        List<Corresponsavel> corresponsaveis = inscricao.getCorresponsaveis();
        corresponsaveis.clear();

        if (Objects.nonNull(requestList)) {
            requestList.forEach(dto -> {
                Corresponsavel corresponsavel = converter(dto, inscricao);
                corresponsaveis.add(corresponsavel);
            });
        }

        return corresponsaveis;
    }

    public static Corresponsavel converter(CorresponsavelRequestDto request, Inscricao inscricao) {
        verificaTipoPessoa(request);
        var corresponsavel = new Corresponsavel();
        BeanUtils.copyProperties(request, corresponsavel, "dataCriacao", "nomeUsuarioCadastro");
        corresponsavel.setInscricao(inscricao);
        return corresponsavel;
    }

    private static void verificaTipoPessoa(CorresponsavelRequestDto request) {
        if (request.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA && request.getCgf() == null) {
            throw new NegocioException("Favor informar o CGF.");
        }
    }
}
