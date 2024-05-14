package br.gov.ce.pge.gestao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroInscricaoResponseDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private UUID numeroInscricao;
  private String documento;
  private String cgf;
  private String nomeRazaoSocial;
  private String origemDebito;
  private BigDecimal valorPrincipal;
  private String nomeUsuario;
  private LocalDateTime dataRegistro;
}
