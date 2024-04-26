package br.gov.ce.pge.gestao.shared.exception;

public class ProdutoServicoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6653673579572997418L;
	
	public ProdutoServicoNotFoundException(String message) {super(message);}
}
