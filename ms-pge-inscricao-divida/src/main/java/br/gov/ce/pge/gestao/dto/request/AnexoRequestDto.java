package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AnexoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 415021909937948108L;

  private String nome;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_DOCUMENTO_ANEXO)
  private String tipoDocumento;

  private transient MultipartFile arquivo;

}
