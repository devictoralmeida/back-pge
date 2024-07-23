package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.AcaoJudicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AcaoJudicialRepository extends JpaRepository<AcaoJudicial, UUID> {
    AcaoJudicial findByNumeroOrdemJudicial(String numeroOrdemJudicial);
}
