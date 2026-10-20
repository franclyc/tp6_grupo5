package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.exceptions.ClienteNoEncontradoException;

public class CollectionCliente {

	public static List<Cliente> clientes = new ArrayList<Cliente>();

	public static void precargarClientes() {
		if (clientes.isEmpty()) {
			clientes.add(new Cliente(45111222, "Mario Barca", "Alvear 120", "65454686"));
			clientes.add(new Cliente(36888666, "Juan Perez", "Av. Belgrano 300", "35185695"));
			clientes.add(new Cliente(25777555, "Ana Juarez", "Islas Malvinas 731", "38845224"));
		}
	}

	public static void agregarCliente(Cliente cliente) {
		if (cliente == null) {
			throw new IllegalArgumentException("No se puede guardar un cliente null");
		}
		for (Cliente cli : clientes) {
			if (cli.getDni() == cliente.getDni()) {
				throw new IllegalArgumentException("Ya existe un cliente con el DNI " + cliente.getDni());
			}
		}
		clientes.add(cliente);
	}

	public static Cliente buscarCliente(long dni) throws ClienteNoEncontradoException {
		for (Cliente cli : clientes) {
			if (cli.getDni() == dni) {
				return cli;
			}
		}
		throw new ClienteNoEncontradoException("No existe un cliente con el DNI " + dni);
	}

}
