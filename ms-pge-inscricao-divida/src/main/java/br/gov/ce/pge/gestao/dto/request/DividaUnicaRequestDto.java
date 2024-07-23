package br.gov.ce.pge.gestao.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DividaUnicaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -1586641693592780999L;

  private String idTipoPapelPessoaDevedora;
  private List<String> documentosDevedores;
  private String origemDebito;
  private String tipoReceita;
  private List<String> referenciasIniciaisDebitos;
  private List<String> referenciasFinaisDebitos;
  private List<BigDecimal> valoresPrincipaisDebitos;
  private List<BigDecimal> valoresMultasDebitos;

  public Map<String, Object> filters() {
    Map<String, Object> filter = new HashMap<>();
    filter.put("idTipoPapelPessoaDevedora", idTipoPapelPessoaDevedora);
    filter.put("documentosDevedores", documentosDevedores);
    filter.put("origemDebito", origemDebito);
    filter.put("tipoReceita", tipoReceita);
    filter.put("referenciasIniciaisDebitos", referenciasIniciaisDebitos);
    filter.put("referenciasFinaisDebitos", referenciasFinaisDebitos == null || referenciasFinaisDebitos.isEmpty() ? null : referenciasFinaisDebitos);
    filter.put("valoresPrincipaisDebitos", valoresPrincipaisDebitos == null || valoresPrincipaisDebitos.isEmpty() ? null : valoresPrincipaisDebitos);
    filter.put("valoresMultasDebitos", valoresMultasDebitos == null || valoresMultasDebitos.isEmpty() ? null : valoresMultasDebitos);
    return filter;
  }

}
