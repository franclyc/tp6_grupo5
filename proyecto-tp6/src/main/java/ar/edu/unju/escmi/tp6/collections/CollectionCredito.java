package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Credito;

public class CollectionCredito {

	public static List<Credito> creditos = new ArrayList<Credito>();

	public static void agregarCredito(Credito credito) {
		if (credito == null) {
			throw new IllegalArgumentException("No se puede guardar un credito null");
		}
		if (creditos.contains(credito)) {
			throw new IllegalArgumentException("El credito de la factura " + credito.getFactura().getNroFactura()
					+ " ya existe");
		}
		creditos.add(credito);
	}

	public static List<Credito> buscarCreditos(long dni) {
		List<Credito> creditosCliente = new ArrayList<Credito>();
		for (Credito credito : creditos) {
			if (credito.getFactura().getCliente().getDni() == dni) {
				creditosCliente.add(credito);
			}
		}
		return creditosCliente;
	}
}
