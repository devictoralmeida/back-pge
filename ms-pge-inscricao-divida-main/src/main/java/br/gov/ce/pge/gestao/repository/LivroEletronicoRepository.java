package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroEletronicoRepository extends JpaRepository<LivroEletronico, UUID> {
  LivroEletronico findBySituacao(SituacaoLivro situacao);

  @Query("SELECT DISTINCT l.id, l.nome, l.situacao, l.dataAbertura, l.dataFechamento, i.id, d.documento, d.cgf, d.nomeRazaoSocial, di.idOrigemDebito, de.valorPrincipal, i" +
          ".nomeUsuarioCadastro, " +
          "i.dataCriacao, de.valorMulta " +
          "FROM LivroEletronico l " +
          "JOIN l.registros r " +
          "JOIN r.inscricao i " +
          "JOIN i.devedor d " +
          "JOIN i.divida di " +
          "JOIN i.debitos de " +
          "WHERE l.id = :livroId")
  List<Object[]> findByIdWithRegistros(@Param("livroId") UUID livroId);

}
