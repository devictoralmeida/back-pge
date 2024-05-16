package br.gov.ce.pge.gestao.service;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.ModuloRequestDto;
import br.gov.ce.pge.gestao.entity.Modulo;

@Service
public interface ModuloService extends ServiceCommons<ModuloRequestDto, Modulo> {


}
