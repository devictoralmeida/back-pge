package br.gov.ce.pge.gestao.dto.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;

public class TipoReceitaValidator implements ConstraintValidator<TipoReceitaValid, TipoReceitaRequestDto> {

	@Override
	public void initialize(TipoReceitaValid constraintAnnotation) {
		// Método sobrescrito utilizado para inicializar a classe, não é necessário implementação
	}

	@Override
	public boolean isValid(TipoReceitaRequestDto request, ConstraintValidatorContext context) {
		
		if(Objects.nonNull(request.getCodigo()) && !request.getCodigo().isEmpty() && !ValidateDadosUtil.contemApenasNumeros(request.getCodigo())) {
			throw new NegocioException("O código só pode contér números");
		}
		
		if(Objects.nonNull(request.getOrigemDebitos()) && request.getOrigemDebitos().isEmpty()) {
			throw new NegocioException("Favor informar a origem");
		}
		
		return true;
	}
}
