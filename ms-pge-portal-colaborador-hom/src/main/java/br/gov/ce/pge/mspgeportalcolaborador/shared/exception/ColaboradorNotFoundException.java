package br.gov.ce.pge.mspgeportalcolaborador.shared.exception;

public class ColaboradorNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -1295219997056892874L;

    public ColaboradorNotFoundException(String message) {
        super(message);
    }
}
