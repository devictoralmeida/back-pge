package br.gov.ce.pge.mspgeoauth.shared.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 7791469278817166611L;

    public NegocioException(String message) {
        super(message);
    }
}
