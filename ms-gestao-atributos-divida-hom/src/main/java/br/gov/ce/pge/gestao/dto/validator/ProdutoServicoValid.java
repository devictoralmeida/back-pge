package br.gov.ce.pge.gestao.dto.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ProdutoServicoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProdutoServicoValid {

	String message() default "Documento inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
