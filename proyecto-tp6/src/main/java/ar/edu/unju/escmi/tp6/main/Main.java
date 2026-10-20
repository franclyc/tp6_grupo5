package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
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
import ar.edu.unju.escmi.tp6.exceptions.TarjetaVencidaException;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);
	private static final LocalDate FIN_PROGRAMA = LocalDate.of(2026, 12, 22);

	public static void main(String[] args) {
		// Los clientes se precargan antes que las tarjetas porque cada tarjeta busca a su cliente
		CollectionCliente.precargarClientes();
		CollectionTarjetaCredito.precargarTarjetas();
		CollectionProducto.precargarProductos();
		CollectionStock.precargarStocks();

		int opcion = 0;
		try {
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
						mostrarProductosAhora20();
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
				} catch (ClienteNoEncontradoException e) {
					System.out.println("\n" + e.getMessage());
				} catch (StockInsuficienteException e) {
					System.out.println("\nNo se pudo completar la venta: " + e.getMessage());
				} catch (MontoExcedidoException e) {
					System.out.println("\nNo se pudo completar la venta: " + e.getMessage());
				} catch (LimiteTarjetaInsuficienteException e) {
					System.out.println("\nNo se pudo completar la venta: " + e.getMessage());
				} catch (TarjetaVencidaException e) {
					System.out.println("\nNo se pudo completar la venta: " + e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println("\nDatos invalidos: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("\nOcurrio un error inesperado: " + e.getMessage());
				} finally {
					if (opcion != 6) {
						System.out.println("\n--- Fin de la operacion ---");
					}
				}
			} while (opcion != 6);
		} finally {
			// try-finally: el Scanner se cierra siempre, ocurra o no una excepcion
			scanner.close();
		}
	}

	private static void mostrarMenu() {
		System.out.println("\n====== Menu Principal - Programa Ahora 20 =====");
		System.out.println("1- Realizar una venta con el programa Ahora 20");
		System.out.println("2- Ver compras realizadas por el cliente (debe ingresar el DNI del cliente)");
		System.out.println("3- Lista de electrodomesticos que se pueden comprar con Ahora 20");
		System.out.println("4- Consultar stock de los electrodomesticos incluidos en el programa");
		System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
		System.out.println("6- Salir");
	}

	private static int leerOpcion() {
		return leerEntero("Ingrese su opcion: ");
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

	private static void realizarVenta() throws ClienteNoEncontradoException {
		if (LocalDate.now().isAfter(FIN_PROGRAMA)) {
			System.out.println("El programa Ahora 20 finalizo el 22/12/2026. No se pueden realizar ventas.");
			return;
		}

		long dni = leerLong("Ingrese el DNI del cliente: ");
		Cliente cliente = CollectionCliente.buscarCliente(dni);
		List<TarjetaCredito> tarjetasCliente = CollectionTarjetaCredito.buscarTarjetasCliente(dni);
		if (tarjetasCliente.isEmpty()) {
			System.out.println("El cliente no tiene una tarjeta de credito registrada.");
			return;
		}

		mostrarProductosAhora20();
		long numeroFactura = CollectionFactura.facturas.size() + 1L;
		Factura factura = new Factura(LocalDate.now(), numeroFactura, cliente);

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
			if (!producto.esFabricacionNacional()) {
				System.out.println("El producto no es de fabricacion nacional, no esta incluido en Ahora 20.");
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
			// Se suma lo que ya se cargo del mismo producto para no vender mas de lo que hay
			int cantidadYaCargada = calcularCantidadCargada(factura, producto);
			if (!stock.validarStockDisponible(cantidadYaCargada + cantidad)) {
				System.out.println("Stock insuficiente. Disponible: " + (stock.getCantidad() - cantidadYaCargada));
				continue;
			}
			factura.agregarDetalle(cantidad, producto);
		}

		if (factura.getDetalles().isEmpty()) {
			System.out.println("No se agregaron productos; la venta fue cancelada.");
			return;
		}

		System.out.println("Tarjetas de " + cliente.getNombre() + ":");
		for (TarjetaCredito tarjeta : tarjetasCliente) {
			System.out.println("Numero: " + tarjeta.getNumero() + " | Limite disponible: " + tarjeta.getLimiteCompra()
					+ " | Vence: " + tarjeta.getFechaCaducacion() + (tarjeta.estaVigente() ? "" : " (VENCIDA)"));
		}
		long numeroTarjeta = leerLong("Ingrese el numero de la tarjeta: ");
		TarjetaCredito tarjetaSeleccionada = null;
		for (TarjetaCredito tarjeta : tarjetasCliente) {
			if (tarjeta.getNumero() == numeroTarjeta) {
				tarjetaSeleccionada = tarjeta;
			}
		}
		if (tarjetaSeleccionada == null) {
			System.out.println("La tarjeta ingresada no pertenece al cliente. La venta fue cancelada.");
			return;
		}

		// El constructor valida tarjeta vigente, montos de Ahora 20 y limite de la tarjeta
		Credito credito = new Credito(tarjetaSeleccionada, factura);

		// Recien cuando el credito es valido se descuenta el stock y se registra la venta
		for (Detalle detalle : factura.getDetalles()) {
			CollectionStock.reducirStock(CollectionStock.buscarStock(detalle.getProducto()), detalle.getCantidad());
		}
		CollectionFactura.agregarFactura(factura);
		CollectionCredito.agregarCredito(credito);
		tarjetaSeleccionada.setLimiteCompra(tarjetaSeleccionada.getLimiteCompra() - credito.getMontoCredito());

		System.out.println("Venta realizada correctamente. Compra financiada en " + Credito.CANTIDAD_CUOTAS
				+ " cuotas.");
		System.out.println(factura);
		System.out.printf("Total: $%.2f%n", factura.calcularTotal());
	}

	private static int calcularCantidadCargada(Factura factura, Producto producto) {
		int cantidad = 0;
		for (Detalle detalle : factura.getDetalles()) {
			if (detalle.getProducto().getCodigo() == producto.getCodigo()) {
				cantidad += detalle.getCantidad();
			}
		}
		return cantidad;
	}

	private static void consultarCompras() throws ClienteNoEncontradoException {
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

	private static void mostrarProductosAhora20() {
		System.out.println("\n--- Electrodomesticos incluidos en Ahora 20 ---");
		for (Producto producto : CollectionProducto.obtenerProductosAhora20()) {
			System.out.println(producto);
		}
	}

	private static void consultarStock() {
		System.out.println("\n--- Stock de electrodomesticos incluidos en Ahora 20 ---");
		for (Stock stock : CollectionStock.stocks) {
			if (stock.getProducto().esFabricacionNacional()) {
				System.out.println(stock.getProducto().getCodigo() + " - " + stock.getProducto().getDescripcion()
						+ " | Cantidad: " + stock.getCantidad());
			}
		}
	}

	private static void consultarCreditos() throws ClienteNoEncontradoException {
		long dni = leerLong("Ingrese el DNI del cliente: ");
		Cliente cliente = CollectionCliente.buscarCliente(dni);
		List<Credito> creditosCliente = CollectionCredito.buscarCreditos(cliente.getDni());
		if (creditosCliente.isEmpty()) {
			System.out.println("El cliente no tiene creditos registrados.");
			return;
		}
		for (Credito credito : creditosCliente) {
			credito.mostrarCredito();
		}
	}
}
