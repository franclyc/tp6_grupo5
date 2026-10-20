package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Factura;

public class CollectionFactura {

	public static List<Factura> facturas = new ArrayList<Factura>();

	

	public static void agregarFactura(Factura factura) {
		if (factura == null) {
			throw new IllegalArgumentException("No se puede guardar una factura null");
		}
		if (buscarFactura(factura.getNroFactura()) != null) {
			throw new IllegalArgumentException("Ya existe una factura con el numero " + factura.getNroFactura());
		}
		facturas.add(factura);
	}

	public static Factura buscarFactura(long nroFactura) {
		for (Factura fac : facturas) {
			if (fac.getNroFactura() == nroFactura) {
				return fac;
			}
		}
		return null;
	}
}
