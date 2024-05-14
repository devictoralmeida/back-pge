package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.DebitoRequestDto;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Inscricao;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DebitoMapperToModel {

    private DebitoMapperToModel() {
    }

    public static List<Debito> converterList(List<DebitoRequestDto> requestList, Inscricao inscricao) {
        return requestList.stream().map(dto -> DebitoMapperToModel.converter(dto, inscricao)).collect(Collectors.toList());
    }

    public static Debito converter(DebitoRequestDto request, Inscricao inscricao) {
        var debito = new Debito();
        BeanUtils.copyProperties(request, debito, "dataCriacao", "nomeUsuarioCadastro");
        debito.setInscricao(inscricao);
        return debito;
    }

    public static List<Debito> converterListUpdate(List<DebitoRequestDto> requestList, Inscricao inscricaoEntity) {
        List<Debito> debitos = inscricaoEntity.getDebitos();
        debitos.clear();

        if (Objects.nonNull(requestList)) {
            requestList.forEach(dto -> {
                Debito debito = converter(dto, inscricaoEntity);
                debitos.add(debito);
            });
        }

        return debitos;
    }
}
