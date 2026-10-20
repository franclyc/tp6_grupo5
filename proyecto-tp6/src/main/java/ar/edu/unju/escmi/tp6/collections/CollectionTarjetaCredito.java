package ar.edu.unju.escmi.tp6.collections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import ar.edu.unju.escmi.tp6.exceptions.ClienteNoEncontradoException;

public class CollectionTarjetaCredito {

	public static List<TarjetaCredito> tarjetas = new ArrayList<TarjetaCredito>();

	
	public static void precargarTarjetas() {
		if (tarjetas.isEmpty()) {
			try {
				tarjetas.add(new TarjetaCredito(232323, LocalDate.of(2026, 10, 10),
						CollectionCliente.buscarCliente(45111222), 800000));
				tarjetas.add(new TarjetaCredito(4458444, LocalDate.of(2030, 3, 15),
						CollectionCliente.buscarCliente(36888666), 900000));
				tarjetas.add(new TarjetaCredito(8754566, LocalDate.of(2030, 4, 21),
						CollectionCliente.buscarCliente(25777555), 1000000));
			} catch (ClienteNoEncontradoException e) {
				System.out.println("No se pudieron precargar las tarjetas: " + e.getMessage());
			}
		}
	}

	public static void agregarTarjetaCredito(TarjetaCredito tarjeta) {
		if (tarjeta == null) {
			throw new IllegalArgumentException("No se puede guardar una tarjeta null");
		}
		if (buscarTarjetaCredito(tarjeta.getNumero()) != null) {
			throw new IllegalArgumentException("Ya existe una tarjeta con el numero " + tarjeta.getNumero());
		}
		tarjetas.add(tarjeta);
	}

	public static TarjetaCredito buscarTarjetaCredito(long numero) {
		for (TarjetaCredito tarjeta : tarjetas) {
			if (tarjeta.getNumero() == numero) {
				return tarjeta;
			}
		}
		return null;
	}

	public static List<TarjetaCredito> buscarTarjetasCliente(long dni) {
		List<TarjetaCredito> tarjetasCliente = new ArrayList<TarjetaCredito>();
		for (TarjetaCredito tarjeta : tarjetas) {
			if (tarjeta.getCliente().getDni() == dni) {
				tarjetasCliente.add(tarjeta);
			}
		}
		return tarjetasCliente;
	}
}
