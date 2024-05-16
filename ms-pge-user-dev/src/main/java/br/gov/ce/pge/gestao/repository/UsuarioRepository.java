package br.gov.ce.pge.gestao.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	Usuario findByCpf(String cpf);

	Usuario findByEmail(String email);

	@Query(value = "SELECT * FROM tb_usuario WHERE nr_cpf_usuario = :identificador OR ds_email_usuario = :identificador OR nm_usuario_rede = :identificador", nativeQuery = true)
	Usuario findByIdentificador(@Param("identificador") String identificador);

	Usuario findByUsuarioRede(String usuarioRede);

	@Query(value = "SELECT * FROM tb_usuario WHERE tp_usuario = 'INTERNO' AND ds_situacao_usuario = 'ATIVA'", nativeQuery = true)
	List<Usuario> findUsuarioAtivo();
}
