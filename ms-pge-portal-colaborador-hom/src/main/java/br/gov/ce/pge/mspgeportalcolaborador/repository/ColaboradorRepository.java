package br.gov.ce.pge.mspgeportalcolaborador.repository;

import br.gov.ce.pge.mspgeportalcolaborador.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    @Query(value = "SELECT c.id, c.nome, c.cpf, c.desvinculado, c.data_desligamento, a.nome AS area FROM colaboradores c LEFT JOIN areas a ON a.id = c.area_id " +
            "WHERE c.cpf = :cpf", nativeQuery = true)
    Colaborador findByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT c.id, c.nome, c.cpf, c.desvinculado, c.data_desligamento, a.nome AS area " +
            "FROM colaboradores c " +
            "LEFT JOIN areas a ON a.id = c.area_id " +
            "WHERE c.cpf IN :cpfs", nativeQuery = true)
    List<Colaborador> findByCpfs(@Param("cpfs") List<String> cpfs);
}
