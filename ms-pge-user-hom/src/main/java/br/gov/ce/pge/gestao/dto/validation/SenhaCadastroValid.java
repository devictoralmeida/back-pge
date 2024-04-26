package br.gov.ce.pge.gestao.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhaCadastroValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SenhaCadastroValid {

    String message() default "Documento inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}