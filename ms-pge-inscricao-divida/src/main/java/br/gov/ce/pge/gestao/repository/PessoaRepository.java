package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
  @Query(value = """
          SELECT p.*, e.*, tp.*, c.*, tc.*, dp.* FROM tb_pessoa p 
          INNER JOIN tb_endereco e ON e.cd_pessoa = p.ci_pessoa 
          INNER JOIN tb_tipo_pessoa tp ON p.cd_tipo_pessoa = tp.ci_tipo_pessoa 
          INNER JOIN tb_divida_pessoa dp ON dp.cd_pessoa = p.ci_pessoa
          INNER JOIN tb_papel_pessoa_divida ppd ON ppd.ci_papel_pessoa_divida = dp.cd_papel_pessoa_divida
          INNER JOIN tb_tipo_papel_pessoa_divida tppd ON tppd.ci_tipo_papel_pessoa_divida = ppd.cd_tipo_papel_pessoa_divida
          LEFT JOIN tb_contato c ON c.cd_pessoa = p.ci_pessoa 
          LEFT JOIN tb_tipo_contato tc ON tc.ci_tipo_contato = c.cd_tipo_contato 
          WHERE p.nr_documento_pessoa = :documento 
          AND e.fl_principal = TRUE;
          """, nativeQuery = true)
  Optional<Pessoa> findByDocumento(String documento);
}
