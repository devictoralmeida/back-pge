package br.gov.ce.pge.gestao.shared.auditoria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;

import org.hibernate.envers.DefaultRevisionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ MockitoExtension.class })
public class CustomRevisionListenerTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CustomRevisionListener customRevisionListener;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void teste_dados_auditoria() {
        
        DefaultRevisionEntity revisionEntity = new DefaultRevisionEntity();
        revisionEntity.setId(1);

        Auditoria auditoria = new Auditoria();
        CustomRevisionListener.setDadosAntigos("Dados Antigos");
        CustomRevisionListener.setNomeUsuario(null);
        assertEquals("Dados Antigos", CustomRevisionListener.getDadosAntigos());

        customRevisionListener.newRevision(auditoria);

        verify(entityManager).flush();
        assertEquals("anônimo", auditoria.getNomeUsuario());
        assertEquals("Dados Antigos", auditoria.getDadosAntigos());
    }
}
