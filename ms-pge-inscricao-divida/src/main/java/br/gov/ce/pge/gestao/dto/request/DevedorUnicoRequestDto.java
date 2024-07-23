package br.gov.ce.pge.gestao.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DevedorUnicoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 9170130987469426801L;

  private List<String> documentos;
  private List<String> statusDebitos;
  private List<String> referenciasIniciaisDebitos;
  private List<String> referenciasFinaisDebitos;
  private List<BigDecimal> valoresPrincipaisDebitos;
  private List<BigDecimal> valoresMultasDebitos;
  private List<LocalDate> datasVencimentosDebitos;
  private List<LocalDate> datasConstituicoesDefinitivasDebitos;
  private List<LocalDate> datasAtualizacaoMonetariaDebitos;

  public Map<String, Object> filters() {
    Map<String, Object> filter = new HashMap<>();
    filter.put("documentos", documentos);
    filter.put("statusDebitos", statusDebitos);
    filter.put("referenciasIniciaisDebitos", referenciasIniciaisDebitos);
    filter.put("datasVencimentosDebitos", datasVencimentosDebitos);
    filter.put("datasAtualizacaoMonetariaDebitos", datasAtualizacaoMonetariaDebitos);
    filter.put("datasConstituicoesDefinitivasDebitos", datasConstituicoesDefinitivasDebitos == null || datasConstituicoesDefinitivasDebitos.isEmpty() ? null : datasConstituicoesDefinitivasDebitos);
    filter.put("referenciasFinaisDebitos", referenciasFinaisDebitos == null || referenciasFinaisDebitos.isEmpty() ? null : referenciasFinaisDebitos);
    filter.put("valoresPrincipaisDebitos", valoresPrincipaisDebitos == null || valoresPrincipaisDebitos.isEmpty() ? null : valoresPrincipaisDebitos);
    filter.put("valoresMultasDebitos", valoresMultasDebitos == null || valoresMultasDebitos.isEmpty() ? null : valoresMultasDebitos);
    return filter;
  }

}
