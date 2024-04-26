package br.gov.ce.pge.gestao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



class   MsGestaoAtributosDividaApplicationTests {

    @Autowired
    private MsGestaoAtributosDividaApplication application;

    @Test
    void contextLoads() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> MsGestaoAtributosDividaApplication.main(null));
    }

}
