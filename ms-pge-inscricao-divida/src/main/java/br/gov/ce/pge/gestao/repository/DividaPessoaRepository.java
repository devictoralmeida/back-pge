package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.DividaPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DividaPessoaRepository extends JpaRepository<DividaPessoa, UUID> {

    @Query(value = "SELECT * FROM tb_divida_pessoa dp " +
            "INNER JOIN tb_papel_pessoa_divida ppd ON dp.cd_papel_pessoa_divida = ppd.ci_papel_pessoa_divida " +
            "INNER JOIN tb_tipo_papel_pessoa_divida tppd ON tppd.ci_tipo_papel_pessoa_divida = ppd.cd_tipo_papel_pessoa_divida " +
            "WHERE cd_divida = :id AND tppd.tp_papel_pessoa_divida = 'Sucessor'", nativeQuery = true)
    DividaPessoa getSucessor(@Param("id") UUID id);

}
