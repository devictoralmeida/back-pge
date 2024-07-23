package br.gov.ce.pge.mspgeoauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class IpUsuario {

    private String nomeUsuario;

    private String ip;

    private String token;

    @Override
    public String toString() {
        return "{" +
                "\"nomeUsuario\": \"" + nomeUsuario + "\"," +
                "\"ip\": \"" + ip + "\"," +
                "\"token\": \"" + token + "\"" +
                "}";
    }
}
