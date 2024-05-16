package br.gov.ce.pge.gestao.dto.validation;

import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SenhaCadastroValidator implements ConstraintValidator<SenhaCadastroValid, UsuarioCadastroSenhaRequestDto> {
    @Override
    public boolean isValid(UsuarioCadastroSenhaRequestDto request, ConstraintValidatorContext context) {
        if(!ValidateDadosUtil.senhaValid(request.getSenha())) {
            throw new NegocioException("Para criar uma senha mais segura, use números, letras maiúsculas, letras minúsculas e caracteres especiais.");
        }

        return true;
    }
}
