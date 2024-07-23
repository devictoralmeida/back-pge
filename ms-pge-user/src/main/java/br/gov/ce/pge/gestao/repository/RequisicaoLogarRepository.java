package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.RequisicaoLogar;
import br.gov.ce.pge.gestao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequisicaoLogarRepository extends JpaRepository<RequisicaoLogar, UUID> {
    List<RequisicaoLogar> findByUsuario(Usuario usuario);
}
