package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.RequisicaoRecuperarSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequisicaoRecuperarSenhaRepository extends JpaRepository<RequisicaoRecuperarSenha, UUID> {

    @Query(value = "SELECT * FROM tb_recuperacao_senha where ds_email = :email", nativeQuery = true)
    List<RequisicaoRecuperarSenha> findByEmail(@Param("email") String email);

}
