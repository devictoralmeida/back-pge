package br.gov.ce.pge.gestao.repository;


import br.gov.ce.pge.gestao.entity.VaraOrigem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaraOrigemRepository extends JpaRepository<VaraOrigem, UUID> {
    @Override
    List<VaraOrigem> findAll();

    Optional<VaraOrigem> findByNomeIgnoreCase(String nome);
}
