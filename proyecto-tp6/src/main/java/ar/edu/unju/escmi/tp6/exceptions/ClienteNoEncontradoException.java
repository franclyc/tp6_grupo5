package ar.edu.unju.escmi.tp6.exceptions;

public class ClienteNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteNoEncontradoException(String errorMessage) {
		super(errorMessage);
	}

}
