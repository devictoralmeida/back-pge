package br.gov.ce.pge.gestao.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.OrigemDebito;

@Repository
public interface OrigemDebitoRepository extends JpaRepository<OrigemDebito, UUID>, RevisionRepository<OrigemDebito, UUID, Long> {
	
	@Query(value = "SELECT * FROM tb_origem_debito o order by o.nm_origem_debito asc", nativeQuery = true)
	List<OrigemDebito> findAllOrigens();
	
	@Query(value = "SELECT * FROM tb_origem_debito o WHERE upper(o.nm_origem_debito) = :nome", nativeQuery = true)
	OrigemDebito findByNome(String nome);

}
