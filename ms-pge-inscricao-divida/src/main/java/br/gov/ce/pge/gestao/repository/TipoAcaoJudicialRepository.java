package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.TipoAcaoJudicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TipoAcaoJudicialRepository extends JpaRepository<TipoAcaoJudicial, UUID> {
}
