package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import ar.edu.unju.escmi.tp6.exceptions.ClienteNoEncontradoException;
import ar.edu.unju.escmi.tp6.exceptions.LimiteTarjetaInsuficienteException;
import ar.edu.unju.escmi.tp6.exceptions.MontoExcedidoException;
import ar.edu.unju.escmi.tp6.exceptions.StockInsuficienteException;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		CollectionCliente.precargarClientes();
		CollectionTarjetaCredito.precargarTarjetas();
		CollectionProducto.precargarProductos();
		CollectionStock.precargarStocks();

		int opcion;
		do {
			mostrarMenu();
			opcion = leerOpcion();
			try {
				switch (opcion) {
				case 1:
					realizarVenta();
					break;
				case 2:
					consultarCompras();
					break;
				case 3:
					mostrarProductos();
					break;
				case 4:
					consultarStock();
					break;
				case 5:
					consultarCreditos();
					break;
				case 6:
					System.out.println("Hasta luego.");
					break;
				default:
					System.out.println("Opcion invalida. Ingrese un numero del 1 al 6.");
				}
			} catch (ClienteNoEncontradoException | StockInsuficienteException | MontoExcedidoException
					| LimiteTarjetaInsuficienteException e) {
				System.out.println("\n" + e.getMessage());
			}
		} while (opcion != 6);
		scanner.close();
	}

	private static void mostrarMenu() {
		System.out.println("\n====== Menu Principal =====");
		System.out.println("1- Realizar una venta");
		System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
		System.out.println("3- Mostrar lista de los electrodomesticos");
		System.out.println("4- Consultar stock");
		System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
		System.out.println("6- Salir");
	}

	private static int leerOpcion() {
		while (true) {
			String entrada = leerTexto("Ingrese su opcion: ");
			try {
				return Integer.parseInt(entrada);
			} catch (NumberFormatException e) {
				System.out.println("Debe ingresar un numero valido.");
			}
		}
	}

	private static long leerLong(String mensaje) {
		while (true) {
			String entrada = leerTexto(mensaje);
			try {
				return Long.parseLong(entrada);
			} catch (NumberFormatException e) {
				System.out.println("Debe ingresar un numero valido.");
			}
		}
	}

	private static int leerEntero(String mensaje) {
		while (true) {
			String entrada = leerTexto(mensaje);
			try {
				return Integer.parseInt(entrada);
			} catch (NumberFormatException e) {
				System.out.println("Debe ingresar un numero entero valido.");
			}
		}
	}

	private static String leerTexto(String mensaje) {
		System.out.print(mensaje);
		return scanner.nextLine().trim();
	}

	private static void realizarVenta() {
		long dni = leerLong("Ingrese el DNI del cliente: ");
		Cliente cliente = CollectionCliente.buscarCliente(dni);
		List<TarjetaCredito> tarjetasCliente = new ArrayList<>();
		for (TarjetaCredito tarjeta : CollectionTarjetaCredito.tarjetas) {
			if (tarjeta.getCliente().getDni() == cliente.getDni()) {
				tarjetasCliente.add(tarjeta);
			}
		}
		if (tarjetasCliente.isEmpty()) {
			System.out.println("El cliente no tiene una tarjeta de credito registrada.");
			return;
		}

		mostrarProductos();
		List<Detalle> detalles = new ArrayList<>();
		List<Stock> stocksVendidos = new ArrayList<>();
		List<Integer> cantidadesVendidas = new ArrayList<>();
		while (true) {
			long codigo = leerLong("Ingrese el codigo del producto (0 para finalizar): ");
			if (codigo == 0) {
				break;
			}
			Producto producto = CollectionProducto.buscarProducto(codigo);
			if (producto == null) {
				System.out.println("No existe un producto con ese codigo.");
				continue;
			}

			Stock stock = CollectionStock.buscarStock(producto);
			if (stock == null) {
				System.out.println("El producto no tiene stock registrado.");
				continue;
			}
			int cantidad = leerEntero("Ingrese la cantidad: ");
			if (cantidad <= 0) {
				System.out.println("La cantidad debe ser mayor que cero.");
				continue;
			}
			if (!stock.validarStockDisponible(cantidad)) {
				System.out.println("Stock insuficiente. Disponible: " + stock.getCantidad());
				continue;
			}

			detalles.add(new Detalle(cantidad, producto, true));
			stocksVendidos.add(stock);
			cantidadesVendidas.add(cantidad);
		}

		if (detalles.isEmpty()) {
			System.out.println("No se agregaron productos; la venta fue cancelada.");
			return;
		}

		System.out.println("Tarjetas disponibles para " + cliente.getNombre() + ":");
		for (TarjetaCredito tarjeta : tarjetasCliente) {
			System.out.println("Numero: " + tarjeta.getNumero() + " | Limite disponible: "
					+ tarjeta.getLimiteCompra());
		}
		long numeroTarjeta = leerLong("Ingrese el numero de la tarjeta: ");
		TarjetaCredito tarjetaSeleccionada = null;
		for (TarjetaCredito tarjeta : tarjetasCliente) {
			if (tarjeta.getNumero() == numeroTarjeta) {
				tarjetaSeleccionada = tarjeta;
				break;
			}
		}
		if (tarjetaSeleccionada == null) {
			System.out.println("La tarjeta ingresada no pertenece al cliente. La venta fue cancelada.");
			return;
		}

		long numeroFactura = CollectionFactura.facturas.size() + 1L;
		Factura factura = new Factura(LocalDate.now(), numeroFactura, cliente, detalles);
		Credito credito = new Credito(tarjetaSeleccionada, factura, new ArrayList<>());

		for (int i = 0; i < stocksVendidos.size(); i++) {
			CollectionStock.reducirStock(stocksVendidos.get(i), cantidadesVendidas.get(i));
		}
		CollectionFactura.agregarFactura(factura);
		CollectionCredito.agregarCredito(credito);
		tarjetaSeleccionada.setLimiteCompra(tarjetaSeleccionada.getLimiteCompra() - factura.calcularTotal());

		System.out.println("Venta realizada correctamente. Compra financiada en 20 cuotas.");
		System.out.println(factura);
		System.out.printf("Total: $%.2f%n", factura.calcularTotal());
	}

	private static void consultarCompras() {
		long dni = leerLong("Ingrese el DNI del cliente: ");
		Cliente cliente = CollectionCliente.buscarCliente(dni);
		List<Factura> compras = cliente.consultarCompras();
		if (compras.isEmpty()) {
			System.out.println("El cliente no tiene compras registradas.");
			return;
		}
		for (Factura factura : compras) {
			System.out.println(factura);
			System.out.printf("Total: $%.2f%n", factura.calcularTotal());
		}
	}

	private static void mostrarProductos() {
		System.out.println("\n--- Productos disponibles ---");
		for (Producto producto : CollectionProducto.productos) {
			System.out.println(producto);
		}
	}

	private static void consultarStock() {
		System.out.println("\n--- Stock disponible ---");
		for (Stock stock : CollectionStock.stocks) {
			System.out.println(stock.getProducto().getCodigo() + " - " + stock.getProducto().getDescripcion()
					+ " | Cantidad: " + stock.getCantidad());
		}
	}

	private static void consultarCreditos() {
		long dni = leerLong("Ingrese el DNI del cliente: ");
		Cliente cliente = CollectionCliente.buscarCliente(dni);
		boolean encontroCredito = false;
		for (Credito credito : CollectionCredito.creditos) {
			if (credito.getTarjetaCredito().getCliente().getDni() == cliente.getDni()) {
				credito.mostarCredito();
				encontroCredito = true;
			}
		}
		if (!encontroCredito) {
			System.out.println("El cliente no tiene creditos registrados.");
		}
	}
}
