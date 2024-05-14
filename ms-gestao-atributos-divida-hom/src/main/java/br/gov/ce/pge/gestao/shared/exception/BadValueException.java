package br.gov.ce.pge.gestao.shared.exception;

public class BadValueException extends RuntimeException {

	private static final long serialVersionUID = -2669052821269647805L;
	
	public BadValueException(String message) {super(message);}
}
