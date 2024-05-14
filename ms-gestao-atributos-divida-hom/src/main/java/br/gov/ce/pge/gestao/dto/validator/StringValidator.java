package br.gov.ce.pge.gestao.dto.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;

public class StringValidator implements ConstraintValidator<DescricaoValid, String> {

    @Override
    public void initialize(DescricaoValid constraintAnnotation) {
        // Método sobrescrito utilizado para inicializar a classe, não é necessário implementação
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
    	if(Objects.nonNull(value) && !value.isEmpty()  && !ValidateDadosUtil.contemApenasLetrasEnumero(value)) {
			throw new NegocioException("a descrição não pode ter caractere especial");
		}
    	
        return true;
    }
}
