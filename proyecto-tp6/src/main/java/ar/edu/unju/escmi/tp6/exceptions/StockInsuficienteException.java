package ar.edu.unju.escmi.tp6.exceptions;

public class StockInsuficienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StockInsuficienteException(String errorMessage) {
		super(errorMessage);
	}

}
