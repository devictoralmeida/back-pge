package br.gov.ce.pge.gestao.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.ProdutoServico;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID> {

	ProdutoServico findByCodigo(String codigo);
	
	@Query(value = "SELECT * FROM tb_produto_servico p ORDER BY p.cd_produto_servico", nativeQuery = true)
	List<ProdutoServico> findAllProdutosServico();

}
