package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LivroEletronicoRepository extends JpaRepository<LivroEletronico, UUID> {
  @Query(value = "SELECT * FROM tb_livro_eletronico WHERE ds_situacao_livro = :situacao OR nm_livro_eletronico = :anoAtual", nativeQuery = true)
  LivroEletronico findBySituacao(@Param("situacao") String situacao, @Param("anoAtual") String anoAtual);
}
