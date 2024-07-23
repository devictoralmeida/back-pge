package br.gov.ce.pge.mspgeoauth.entity;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sistema {

	private UUID id;

	private String nome;

	private List<Modulo> modulos;

}
