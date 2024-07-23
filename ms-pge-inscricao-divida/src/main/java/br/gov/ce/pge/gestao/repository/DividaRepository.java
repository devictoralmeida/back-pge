package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.Divida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DividaRepository extends JpaRepository<Divida, UUID> {
  @Query(value = "select * from tb_divida order by dt_criacao desc limit 1", nativeQuery = true)
  Divida findLast();

  @Query(value = "SELECT * FROM tb_divida WHERE ci_divida IN (:dividas)", nativeQuery = true)
  List<Divida> findByIds(@Param("dividas") List<UUID> dividas);

  @Query(value = "SELECT * FROM tb_divida WHERE nr_inscricao = :numeroInscricao", nativeQuery = true)
  Divida findByNumeroInscricao(@Param("numeroInscricao") String numeroInscricao);
}
