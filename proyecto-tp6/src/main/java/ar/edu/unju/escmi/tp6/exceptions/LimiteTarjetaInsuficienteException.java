package ar.edu.unju.escmi.tp6.exceptions;

public class LimiteTarjetaInsuficienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LimiteTarjetaInsuficienteException(String errorMessage) {
		super(errorMessage);
	}

}
