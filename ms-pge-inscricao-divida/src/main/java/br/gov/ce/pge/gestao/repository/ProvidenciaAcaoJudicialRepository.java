package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProvidenciaAcaoJudicialRepository extends JpaRepository<ProvidenciaAcaoJudicial, UUID> {
    ProvidenciaAcaoJudicial findByNome(String nome);
}
