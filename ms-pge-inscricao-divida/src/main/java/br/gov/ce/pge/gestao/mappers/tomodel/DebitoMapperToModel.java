package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.ServicosConfig;
import br.gov.ce.pge.gestao.dto.request.DebitoRequestDto;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Divida;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class DebitoMapperToModel {
  private DebitoMapperToModel() {
  }

  public static List<Debito> converterList(List<DebitoRequestDto> requestList, Divida divida, ServicosConfig servicosConfig) {
    return requestList.stream().map(dto -> DebitoMapperToModel.converter(dto, divida, servicosConfig)).toList();
  }


  public static Debito converter(DebitoRequestDto request, Divida divida, ServicosConfig servicosConfig) {
    request.validarReferencias();
    request.validarValores();
    
    if(request.getId() != null) {
    	Debito debito = divida.getDebitos().stream().filter(deb -> deb.getId().equals(request.getId())).findFirst().get();
    	BeanUtils.copyProperties(request, debito, "dataCriacao", "nomeUsuarioCadastro", "id");
        debito.setDivida(divida);
        debito.setStatus(servicosConfig.getStatusDebitoService().findById(request.getIdStatusDebito()));
        return debito;
    }

    Debito debito = new Debito();
    BeanUtils.copyProperties(request, debito, "dataCriacao", "nomeUsuarioCadastro", "id");
    debito.setDivida(divida);
    debito.setStatus(servicosConfig.getStatusDebitoService().findById(request.getIdStatusDebito()));
    return debito;
  }
}
