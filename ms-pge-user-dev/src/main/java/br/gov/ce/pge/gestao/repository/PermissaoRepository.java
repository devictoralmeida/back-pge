package br.gov.ce.pge.gestao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, UUID> , RevisionRepository<Permissao, UUID, Long>{
	

}
