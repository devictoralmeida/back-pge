package br.gov.ce.pge.gestao.shared.auditoria;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
public class CustomRevisionListener implements RevisionListener {

    @PersistenceContext
    private EntityManager entityManager;

    private static String dadosAntigos;

    @Override
    public void newRevision(final Object entity) {
        Auditoria revision = (Auditoria) entity;
        revision.setDataMovimento(LocalDateTime.now());
        revision.setNomeUsuario("anônimo");
        revision.setDadosAntigos(dadosAntigos);

        if (entityManager != null) {
            entityManager.flush();
        }
        
        setDadosAntigos(null);
    }
    
    public static synchronized String getDadosAntigos() {
        return dadosAntigos;
    }

    public static synchronized void setDadosAntigos(String dadosAntigos) {
        CustomRevisionListener.dadosAntigos = dadosAntigos;
    }
}
