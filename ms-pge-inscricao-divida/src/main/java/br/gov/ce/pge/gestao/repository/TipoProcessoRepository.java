package br.gov.ce.pge.gestao.repository;


import br.gov.ce.pge.gestao.entity.TipoProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoProcessoRepository extends JpaRepository<TipoProcesso, UUID> {
    Optional<TipoProcesso> findByNomeIgnoreCase(String nome);
}
