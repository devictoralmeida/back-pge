package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.request.DevedorUnicoRequestDto;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Divida;

import java.util.List;
import java.util.Objects;

public class DevedorUnicoMapperToDto {
  private DevedorUnicoMapperToDto() {
  }

  public static DevedorUnicoRequestDto converter(Divida divida, List<String> documentos) {
    DevedorUnicoRequestDto request = new DevedorUnicoRequestDto();
    request.setDocumentos(documentos);
    request.setStatusDebitos(divida.getDebitos().stream().map(debito -> debito.getStatus().getId().toString()).toList());
    request.setReferenciasIniciaisDebitos(divida.getDebitos().stream().map(Debito::getReferenciaInicial).toList());
    request.setDatasVencimentosDebitos(divida.getDebitos().stream().map(Debito::getDataVencimento).toList());
    request.setDatasAtualizacaoMonetariaDebitos(divida.getDebitos().stream().map(Debito::getDataInicioAtualizacaoMonetaria).toList());
    request.setReferenciasFinaisDebitos(divida.getDebitos().stream().map(Debito::getReferenciaFinal).filter(Objects::nonNull).toList());
    request.setValoresPrincipaisDebitos(divida.getDebitos().stream().map(Debito::getValorPrincipal).filter(Objects::nonNull).toList());
    request.setValoresMultasDebitos(divida.getDebitos().stream().map(Debito::getValorMulta).filter(Objects::nonNull).toList());
    request.setDatasConstituicoesDefinitivasDebitos(divida.getDebitos().stream().map(Debito::getDataConstituicaoDefinitivaCredito).filter(Objects::nonNull).toList());

    return request;
  }
}
