package br.gov.ce.pge.gestao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.SistemaRequestDto;
import br.gov.ce.pge.gestao.dto.response.SistemaDto;
import br.gov.ce.pge.gestao.entity.Sistema;

@Service
public interface SistemaService extends ServiceCommons<SistemaRequestDto, Sistema> {

	List<Sistema> findAllPermissoesBySistema();
	
	List<SistemaDto> findAllOrdenados();

}
