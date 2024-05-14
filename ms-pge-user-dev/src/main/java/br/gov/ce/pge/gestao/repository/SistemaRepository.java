package br.gov.ce.pge.gestao.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.Sistema;

@Repository
public interface SistemaRepository extends JpaRepository<Sistema, UUID> {

	@Query(value = "select * from tb_sistema s where s.nm_sistema in ('Portal da Dívida Ativa','Portal das Origens');", nativeQuery = true)
	List<Sistema> findAllPermissoesBySistema();
	
	List<Sistema> findByNome(String nome);
	
	@Query(value = "select * from tb_sistema s order by s.nm_sistema asc", nativeQuery = true)
	List<Sistema> findAll();

}
