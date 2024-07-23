package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.Contato;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ContatoResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -6152443198511374966L;

  private UUID id;

  private String valorContato;

  private UUID idTipoContato;

  private String numeroDdiContato;

  public ContatoResponseDto(Contato contato) {
    id = contato.getId();
    valorContato = contato.getValorContato();
    idTipoContato = contato.getTipoContato().getId();
    numeroDdiContato = contato.getNumeroDdiContato();
  }
}
