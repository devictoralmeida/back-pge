package br.gov.ce.pge.gestao.dto.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;

public class UsuarioValidator implements ConstraintValidator<UsuarioValid, UsuarioRequestDto> {

	@Override
	public boolean isValid(@Valid UsuarioRequestDto request, ConstraintValidatorContext context) {
		
		if(Objects.nonNull(request.getCpf()) && !ValidateDadosUtil.contemApenasNumeros(request.getCpf())) {
			throw new NegocioException("o documento do usuário só pode conter número");
		}
		
		if(Objects.nonNull(request.getEmail()) && !ValidateDadosUtil.emailDominioGov(request.getEmail())) {
			throw new NegocioException("E-mail inválido.");
		}

		return true;
	}
}
