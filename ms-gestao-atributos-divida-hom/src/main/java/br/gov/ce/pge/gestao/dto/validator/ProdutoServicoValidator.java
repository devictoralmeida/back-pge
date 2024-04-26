package br.gov.ce.pge.gestao.dto.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;

public class ProdutoServicoValidator implements ConstraintValidator<ProdutoServicoValid, ProdutoServicoRequestDto> {

	@Override
	public void initialize(ProdutoServicoValid constraintAnnotation) {
		// Método sobrescrito utilizado para inicializar a classe, não é necessário implementação
	}

	@Override
	public boolean isValid(ProdutoServicoRequestDto request, ConstraintValidatorContext context) {
		
		if(Objects.nonNull(request.getCodigo()) && !request.getCodigo().isEmpty() && !ValidateDadosUtil.contemApenasNumeros(request.getCodigo())) {
			throw new NegocioException("O código só pode contér números");
		}
		
		if(Objects.nonNull(request.getIdsTipoReceitas()) && request.getIdsTipoReceitas().isEmpty()) {
			throw new NegocioException("Favor informar o tipo receita");
		}
		
		return true;
	}
}
