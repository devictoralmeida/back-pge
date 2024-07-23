package br.gov.ce.pge.mspgeapigateway.entity;

import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IpUsuarioTest {

    static IpUsuario getIpUsuario() {
        return new IpUsuario("test", "192.168.1.0", "token");
    }

    @Test
    void test_ip_usuario() {
        IpUsuario ipUsuario = getIpUsuario();
        asserts(ipUsuario);
    }

    static void asserts(IpUsuario ipUsuario) {

        assertNotNull(ipUsuario);

        assertEquals("test", ipUsuario.getNomeUsuario());
        assertEquals("192.168.1.0", ipUsuario.getIp());
        assertEquals("token", ipUsuario.getToken());

        assertNotNull(ipUsuario.toString());
    }

    @Test
    void test_getter_setter() {
        IpUsuario ipUsuario = new IpUsuario();

        ipUsuario.setNomeUsuario("test");
        ipUsuario.setIp("192.168.2.1");
        ipUsuario.setToken("token");

        assertsGetterSetter(ipUsuario);
    }

    void assertsGetterSetter(IpUsuario ipUsuario) {

        assertEquals("test", ipUsuario.getNomeUsuario());
        assertEquals("192.168.2.1", ipUsuario.getIp());
        assertEquals("token", ipUsuario.getToken());

    }

}
