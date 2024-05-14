package br.gov.ce.pge.mspgeoauth.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permissao {

	private UUID id;

	private String nome;

	private String codigoIdentificador;
}
