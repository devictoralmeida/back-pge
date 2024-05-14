package br.gov.ce.pge.gestao.repository;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.entity.OrigemDebito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.TipoReceita;

@Repository
public interface TipoReceitaRepository extends JpaRepository<TipoReceita, UUID> {
	
	TipoReceita findByCodigo(String codigo);

	@Query(value = "SELECT * FROM tb_tipo_receita t ORDER BY t.cd_tipo_receita", nativeQuery = true)
	List<TipoReceita> findAllTipos();

	List<TipoReceita> findByOrigemDebitos(OrigemDebito origemDebito);
}
