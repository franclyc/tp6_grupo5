package ar.edu.unju.escmi.tp6.exceptions;

/**
 * Excepcion no chequeada (unchecked): se lanza cuando se intenta pagar
 * con una tarjeta de credito cuya fecha de caducacion ya paso.
 */
public class TarjetaVencidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TarjetaVencidaException(String errorMessage) {
		super(errorMessage);
	}

}
