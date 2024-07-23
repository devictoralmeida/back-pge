package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.dto.request.DividaUnicaRequestDto;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Divida;

import java.util.List;
import java.util.Objects;

public class DividaUnicaMapperToDto {
  private DividaUnicaMapperToDto() {
  }

  public static DividaUnicaRequestDto converter(Divida divida, List<String> documentosDevedores) {
    DividaUnicaRequestDto request = new DividaUnicaRequestDto();
    request.setIdTipoPapelPessoaDevedora(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR.toString());
    request.setDocumentosDevedores(documentosDevedores);
    request.setOrigemDebito(divida.getIdOrigemDebito().toString());
    request.setTipoReceita(divida.getIdTipoReceita().toString());
    request.setReferenciasIniciaisDebitos(divida.getDebitos().stream().map(Debito::getReferenciaInicial).toList());
    request.setReferenciasFinaisDebitos(divida.getDebitos().stream().map(Debito::getReferenciaFinal).filter(Objects::nonNull).toList());
    request.setValoresPrincipaisDebitos(divida.getDebitos().stream().map(Debito::getValorPrincipal).filter(Objects::nonNull).toList());
    request.setValoresMultasDebitos(divida.getDebitos().stream().map(Debito::getValorMulta).filter(Objects::nonNull).toList());
    return request;
  }
}
