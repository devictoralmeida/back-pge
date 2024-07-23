package br.gov.ce.pge.gestao.repository;

import br.gov.ce.pge.gestao.entity.TipoDocumentoAnexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoDocumentoAnexoRepository extends JpaRepository<TipoDocumentoAnexo, UUID> {
  @Override
  List<TipoDocumentoAnexo> findAll();

  Optional<TipoDocumentoAnexo> findByTipoDocumento(String tipoDocumento);
}
