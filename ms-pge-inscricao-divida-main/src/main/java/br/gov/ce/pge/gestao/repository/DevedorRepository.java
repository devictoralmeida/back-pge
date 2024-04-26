package br.gov.ce.pge.gestao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.Devedor;

@Repository
public interface DevedorRepository extends JpaRepository<Devedor, UUID> {

}
