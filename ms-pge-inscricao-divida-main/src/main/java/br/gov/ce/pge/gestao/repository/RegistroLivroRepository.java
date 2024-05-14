package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.RegistroLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistroLivroRepository extends JpaRepository<RegistroLivro, UUID> {
  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM tb_registro_livro r WHERE r.cd_livro_eletronico = :id")
  Integer countByLivroEletronicoId(UUID id);
}
