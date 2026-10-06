package ar.edu.unju.escmi.tp6.exceptions;

public class MontoExcedidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MontoExcedidoException(String errorMessage) {
		super(errorMessage);
	}

}
