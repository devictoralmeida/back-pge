package br.gov.ce.pge.mspgeoauth.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.gov.ce.pge.mspgeoauth.entity.Usuario;

@Service
public class TokenService {
	
	private static final String NOME_USUARIO = "nomeUsuario";
	private static final String PERMISSOES = "permissoes";
	private static final int HORAS = 2;
	private static final String ZONE_OFFSET = "-03:00";
	private static final String MS_PGE_OAUTH = "ms-pge-oauth";
	private static final String ERROR_AO_GERAR_TOKEN = "Error ao gerar token: ";
	
	@Value("${api.secutiry.token.secret}")
	private String secret;
	
	public String generateToken(Usuario user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer(MS_PGE_OAUTH)
					.withSubject(user.getId().toString())
					.withPayload(getPermissoes(user).toJson())
					.withPayload(Map.of(NOME_USUARIO, user.getNome()))
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
		} catch(JWTCreationException ex) {
			throw new JWTCreationException(ERROR_AO_GERAR_TOKEN, ex);
		}
	}
	
	public String validadeToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer(MS_PGE_OAUTH)
					.build()
					.verify(token)
					.getSubject();
		} catch(JWTVerificationException ex) {
			return "";
		}
	}
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(HORAS).toInstant(ZoneOffset.of(ZONE_OFFSET));
	}

	private Document getPermissoes(Usuario usuario) {
		Set<String> set = new HashSet<>();
		usuario.getPerfisAcessos().stream().forEach(perfilAcesso -> 
		   perfilAcesso.getPermissoes().stream().forEach(permissao -> 
			   set.add(permissao.getCodigoIdentificador())
		   )
		);
		return new Document(PERMISSOES, set);
   	}
}
