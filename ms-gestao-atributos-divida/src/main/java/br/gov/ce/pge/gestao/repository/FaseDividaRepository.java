package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.FaseDivida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FaseDividaRepository extends JpaRepository<FaseDivida, UUID> {

    @Query(value = "SELECT * FROM tb_fase_divida fd order by fd.cd_fase_divida asc", nativeQuery = true)
    List<FaseDivida> findAllFase();

    @Query(value = "SELECT * FROM tb_fase_divida fd where fd.ds_situacao_fase_divida = 'ATIVA' order by fd.cd_fase_divida asc", nativeQuery = true)
    List<FaseDivida> findFasesAtivas();

    @Query(value = "SELECT * FROM tb_fase_divida fd WHERE upper(fd.nm_fase_divida) = :nome", nativeQuery = true)
    Optional<FaseDivida> findByNome(String nome);

    @Query(value = "SELECT * FROM tb_fase_divida fd WHERE SIMILARITY(:nome, fd.nm_fase_divida) > 0.75", nativeQuery = true)
    List<FaseDivida> findByNomeSemelhante(String nome);

    @Query(value = "SELECT coalesce(max(fd.cd_fase_divida), '0') FROM tb_fase_divida fd", nativeQuery = true)
    String findMaxCodigo();
}
