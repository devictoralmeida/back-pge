package br.gov.ce.pge.gestao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.pge.gestao.entity.CondicaoAcesso;

public interface CondicaoAcessoRepository extends JpaRepository<CondicaoAcesso, UUID> {

}
