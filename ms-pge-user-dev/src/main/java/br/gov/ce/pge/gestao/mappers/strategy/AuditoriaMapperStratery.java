package br.gov.ce.pge.gestao.mappers.strategy;

import java.util.Map;

import br.gov.ce.pge.gestao.mappers.todto.*;

public class AuditoriaMapperStratery {
	
	private AuditoriaMapperStratery() {}
	
	public static final Map<String, AuditoriaToDtoMapper> mapperHistorico = Map.of(
			"historico-permissao", new HistoricoAtualizacaoPermissaoToDtoMapper(),
			"historico-modulo", new HistoricoAtualizacaoModuloToDtoMapper(),
			"historico-sistema", new HistoricoAtualizacaoSistemaToDtoMapper(),
			"historico-perfil", new HistoricoAtualizacaoPerfilAcessoToDtoMapper(),
			"historico-usuario", new HistoricoAtualizacaoUsuarioToDtoMapper(),
			"historico-condicao-acesso", new HistoricoAtualizacaoCondicaoAcessoToDtoMapper()
			);

}
