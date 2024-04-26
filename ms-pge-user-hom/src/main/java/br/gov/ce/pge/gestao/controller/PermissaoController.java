package br.gov.ce.pge.gestao.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.gestao.dto.request.PermissaoRequestDto;
import br.gov.ce.pge.gestao.entity.Permissao;
import br.gov.ce.pge.gestao.service.PermissaoService;

@RestController
@RequestMapping(value = "permissao")
@Qualifier(value = "permissao")
public class PermissaoController extends ControllerCommons<PermissaoRequestDto, Permissao>  {

	@Qualifier("permissaoServiceImpl") 
	private PermissaoService service;
	
	public PermissaoController(@Qualifier("permissaoServiceImpl") PermissaoService service) {
		super(service);
		this.service = service;
	}

}
