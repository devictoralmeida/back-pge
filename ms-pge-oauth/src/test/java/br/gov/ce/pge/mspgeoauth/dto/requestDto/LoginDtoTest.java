package br.gov.ce.pge.mspgeoauth.dto.requestDto;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginDtoTest {

    @Test
    void test_classe() {
        var loginDto = new LoginDto();

        assertNotNull(loginDto);
    }
}
