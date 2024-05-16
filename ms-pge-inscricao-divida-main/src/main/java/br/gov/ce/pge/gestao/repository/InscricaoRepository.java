package br.gov.ce.pge.gestao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.Inscricao;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {

    @Query(value = "select * from tb_inscricao order by dt_criacao desc limit 1", nativeQuery = true)
    Inscricao findLast();
}
