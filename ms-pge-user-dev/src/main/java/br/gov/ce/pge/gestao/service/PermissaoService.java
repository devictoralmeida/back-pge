package br.gov.ce.pge.gestao.service;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.PermissaoRequestDto;
import br.gov.ce.pge.gestao.entity.Permissao;

@Service
public interface PermissaoService extends ServiceCommons<PermissaoRequestDto, Permissao> {


}
