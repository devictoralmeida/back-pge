package br.gov.ce.pge.gestao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;

@Repository
public interface LivroEletronicoRepository extends JpaRepository<LivroEletronico, UUID> {
	
	LivroEletronico findBySituacao(SituacaoLivro situacao);

}
