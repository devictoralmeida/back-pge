package br.gov.ce.pge.gestao.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.TermoCondicao;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TermoCondicaoRepository extends JpaRepository<TermoCondicao, UUID> {

	@Query(value = " (SELECT t.* FROM tb_termo_condicao t INNER JOIN tb_sistema s ON s.ci_sistema = t.cd_sistema WHERE s.nm_sistema = 'Portal da Dívida Ativa' ORDER BY t.dt_criacao DESC LIMIT 1)"
			+ "UNION ALL "
			+ "(SELECT t.* FROM tb_termo_condicao t INNER JOIN tb_sistema s ON s.ci_sistema = t.cd_sistema WHERE s.nm_sistema = 'Portal das Origens' ORDER BY t.dt_criacao DESC LIMIT 1);", nativeQuery = true)
	List<TermoCondicao> findByNomeSistema();

	@Query(value = "SELECT COUNT(*) " +
			"FROM tb_termo_condicao " +
			"WHERE ci_termo_condicao = :id" +
			"   OR (dt_criacao >= (SELECT dt_criacao FROM tb_termo_condicao WHERE ci_termo_condicao = :id) " +
			"       AND cd_sistema = (SELECT cd_sistema FROM tb_termo_condicao WHERE ci_termo_condicao = :id))",
			nativeQuery = true)
	int countByIdTermoCondicao(@Param("id") UUID id);

	@Modifying
	@Query(value = "UPDATE tb_usuario SET dt_aceite_termo_portal_divida = NULL FROM tb_usuario u INNER JOIN" +
			" tb_usuario_sistema us ON u.ci_usuario = us.cd_usuario INNER JOIN tb_usuario_termo ut ON " +
			"u.ci_usuario = ut.cd_usuario WHERE us.cd_sistema = :id", nativeQuery = true)
	void updateDataAceitePortalDivida(@Param("id") UUID id);

	@Modifying
	@Query(value = "UPDATE tb_usuario SET dt_aceite_termo_portal_origem = NULL FROM tb_usuario u INNER JOIN " +
			"tb_usuario_sistema us ON u.ci_usuario = us.cd_usuario INNER JOIN tb_usuario_termo ut ON " +
			"u.ci_usuario = ut.cd_usuario WHERE us.cd_sistema = :id", nativeQuery = true)
	void updateDataAceitePortalOrigens(@Param("id") UUID id);
}
