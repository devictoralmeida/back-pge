package br.gov.ce.pge.gestao.security;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class Password {
	
	private Password() {}
	
	public static String encripty(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
	}
	
	public static void equalsPassword(String password, String confirmPassword) {
		if(!password.equals(confirmPassword)) {
			throw new NegocioException(MessageCommonsContanst.SENHAS_NAO_CONFEREM);
		}
	}

}
