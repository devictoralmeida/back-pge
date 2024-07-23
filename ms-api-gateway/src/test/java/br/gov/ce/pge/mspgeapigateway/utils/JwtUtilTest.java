package br.gov.ce.pge.mspgeapigateway.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

	@InjectMocks
	private JwtUtil jwtUtil;

	@Mock
	private Algorithm algorithmMock;
	
	@Mock
	private JWT jwt;
	
	@Mock
	private Claim claim;

	@Mock
	private JWTVerifier jwtVerifierMock;
	
	@Mock
	private DecodedJWT decodedJWT;

	@Test
	public void test_validate_token_valid_token() {

		ReflectionTestUtils.setField(jwtUtil, "secretKey", "ms-pge-portal");

		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtcy1wZ2Utb2F1dGgiLCJzdWIiOiIyZGE2MGZmNi0yODUzLTRmYmQtOGQ5Yi01MTVmNjEwNGM5YmMiLCJwZXJtaXNzb2VzIjpbIk9SSUdFTV9ERUJJVE9fRVhDTFVJUiIsIlRJUE9fUkVDRUlUQV9FRElUQVIiLCJQUk9EVVRPX1NFUlZJQ09fVklTVUFMSVpBUiIsIlRJUE9fUkVDRUlUQV9FWENMVUlSIiwiVElQT19SRUNFSVRBX1ZJU1VBTElaQVIiLCJQUk9EVVRPX1NFUlZJQ09fQ0FEQVNUUkFSIiwiVVNVQVJJT19ISVNUT1JJQ09fQUxURVJBQ0FPIiwiVEVSTU9TX0NPTkRJQ09FU19WSVNVQUxJWkFSIiwiT1JJR0VNX0RFQklUT19DQURBU1RSQVIiLCJVU1VBUklPX0VESVRBUiIsIk9SSUdFTV9ERUJJVE9fRURJVEFSIiwiRkFTRV9ESVZJREFfSElTVE9SSUNPX0FMVEVSQUNBTyIsIlBFUkZJTF9BQ0VTU09fSElTVE9SSUNPX0FMVEVSQUNBTyIsIkZBU0VfRElWSURBX0VYQ0xVSVIiLCJURVJNT1NfQ09ORElDT0VTX0hJU1RPUklDT19BTFRFUkFDQU8iLCJQUk9EVVRPX1NFUlZJQ09fSElTVE9SSUNPX0FMVEVSQUNBTyIsIlRFUk1PU19DT05ESUNPRVNfRURJVEFSIiwiUEVSRklMX0FDRVNTT19FRElUQVIiLCJQRVJGSUxfQUNFU1NPX0VYQ0xVSVIiLCJDT05ESUNPRVNfQUNFU1NPX0NBREFTVFJBUiIsIlBST0RVVE9fU0VSVklDT19FWENMVUlSIiwiRkFTRV9ESVZJREFfRkxVWE9fRkFTRSIsIlBFUkZJTF9BQ0VTU09fVklTVUFMSVpBUiIsIlVTVUFSSU9fQ0FEQVNUUkFSIiwiRkVSUkFNRU5UQVNfIiwiVElQT19SRUNFSVRBX0NBREFTVFJBUiIsIlVTVUFSSU9fVklTVUFMSVpBUiIsIk9SSUdFTV9ERUJJVE9fSElTVE9SSUNPX0FMVEVSQUNBTyIsIlBST0RVVE9fU0VSVklDT19FRElUQVIiLCJPUklHRU1fREVCSVRPX1ZJU1VBTElaQVIiLCJGQVNFX0RJVklEQV9FRElUQVIiLCJQRVJGSUxfQUNFU1NPX0NBREFTVFJBUiIsIlVTVUFSSU9fRVhDTFVJUiIsIkZBU0VfRElWSURBX0NBREFTVFJBUiIsIkZBU0VfRElWSURBX1ZJU1VBTElaQVIiLCJUSVBPX1JFQ0VJVEFfSElTVE9SSUNPX0FMVEVSQUNBTyJdLCJleHAiOjE3MTIwMDYzODl9.N6BWMNPy_IsIFdrYsc7uQlEBVKq_xH7MbdlNflgYFFo";

		Object result = jwtUtil.validateToken(token);

		assertNotNull(result);
	}
	
	@Test
	public void test_nome_usuario() {

		ReflectionTestUtils.setField(jwtUtil, "secretKey", "ms-pge-portal");
		
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtcy1wZ2Utb2F1dGgiLCJzdWIiOiI2MDQ1Zjg5OS0xMzI2LTRjMmUtOTFjYy0zYjRhMDk3YWQwMDYiLCJwZXJtaXNzb2VzIjpbIk9SSUdFTV9ERUJJVE9fRVhDTFVJUiIsIlRJUE9fUkVDRUlUQV9FRElUQVIiLCJQUk9EVVRPX1NFUlZJQ09fVklTVUFMSVpBUiIsIlRJUE9fUkVDRUlUQV9WSVNVQUxJWkFSIiwiVElQT19SRUNFSVRBX0VYQ0xVSVIiLCJQUk9EVVRPX1NFUlZJQ09fQ0FEQVNUUkFSIiwiVVNVQVJJT19ISVNUT1JJQ09fQUxURVJBQ0FPIiwiVEVSTU9TX0NPTkRJQ09FU19WSVNVQUxJWkFSIiwiT1JJR0VNX0RFQklUT19DQURBU1RSQVIiLCJPUklHRU1fREVCSVRPX0VESVRBUiIsIlVTVUFSSU9fRURJVEFSIiwiRkFTRV9ESVZJREFfSElTVE9SSUNPX0FMVEVSQUNBTyIsIlBFUkZJTF9BQ0VTU09fSElTVE9SSUNPX0FMVEVSQUNBTyIsIkZBU0VfRElWSURBX0VYQ0xVSVIiLCJURVJNT1NfQ09ORElDT0VTX0hJU1RPUklDT19BTFRFUkFDQU8iLCJQUk9EVVRPX1NFUlZJQ09fSElTVE9SSUNPX0FMVEVSQUNBTyIsIlRFUk1PU19DT05ESUNPRVNfRURJVEFSIiwiUEVSRklMX0FDRVNTT19FRElUQVIiLCJQRVJGSUxfQUNFU1NPX0VYQ0xVSVIiLCJDT05ESUNPRVNfQUNFU1NPX0NBREFTVFJBUiIsIlBST0RVVE9fU0VSVklDT19FWENMVUlSIiwiRkFTRV9ESVZJREFfRkxVWE9fRkFTRSIsIlBFUkZJTF9BQ0VTU09fVklTVUFMSVpBUiIsIlVTVUFSSU9fQ0FEQVNUUkFSIiwiRkVSUkFNRU5UQVNfIiwiVElQT19SRUNFSVRBX0NBREFTVFJBUiIsIlVTVUFSSU9fVklTVUFMSVpBUiIsIk9SSUdFTV9ERUJJVE9fSElTVE9SSUNPX0FMVEVSQUNBTyIsIlBST0RVVE9fU0VSVklDT19FRElUQVIiLCJPUklHRU1fREVCSVRPX1ZJU1VBTElaQVIiLCJGQVNFX0RJVklEQV9FRElUQVIiLCJQRVJGSUxfQUNFU1NPX0NBREFTVFJBUiIsIkZBU0VfRElWSURBX1ZJU1VBTElaQVIiLCJGQVNFX0RJVklEQV9DQURBU1RSQVIiLCJUSVBPX1JFQ0VJVEFfSElTVE9SSUNPX0FMVEVSQUNBTyIsIlVTVUFSSU9fRVhDTFVJUiJdLCJub21lVXN1YXJpbyI6IkFCIFRFU1RFIDUiLCJleHAiOjE3MTUwMDgxNTN9.WvvReLBlyHtlnHLkLO6KtlPXCZHqBDQdYk2-Jqgjc08";
		Mockito.lenient().when(jwt.decodeJwt(token)).thenReturn(decodedJWT);
		
		Mockito.lenient().when(decodedJWT.getClaim("nomeUsuario")).thenReturn(claim);

		Object result = jwtUtil.getNomeUsuarioByToken(token);

		assertNotNull(result);
		assertEquals("AB TESTE 5", result);
	}
	
	@Test
	public void test_nome_usuario_erro() {

		ReflectionTestUtils.setField(jwtUtil, "secretKey", "ms-pge-portal");
		
		String token = "invalidToken";
		Mockito.lenient().when(jwt.decodeJwt(token)).thenThrow(new RuntimeException("erro"));
		
		assertNull(jwtUtil.getNomeUsuarioByToken(token));
	}


}