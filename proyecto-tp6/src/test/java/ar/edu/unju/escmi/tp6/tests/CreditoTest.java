package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {

	public static final double MONTO_MAXIMO = 2500000;
	public static final double PRECIO_TELEVISOR = 165000;
	public static final double PRECIO_HELADERA = 130000;
	public static final double PRECIO_CELULAR = 150000;
	public static final double LIMITE_TARJETA = 1000000;

	private TarjetaCredito tarjeta;
	private Factura factura;
	private Credito credito;

	@BeforeEach
	void setUp() {
		Cliente cliente = new Cliente(25777555L, "Ana Juarez", "Islas Malvinas 731", "38845224");
		tarjeta = new TarjetaCredito(8754566L, LocalDate.now().plusYears(2), cliente, LIMITE_TARJETA);

		factura = new Factura(LocalDate.now(), 1, cliente);
		factura.agregarDetalle(2, new Producto(5111, "Televisor 50\" - ULTRA HD Smart LG", PRECIO_TELEVISOR,
				"Argentina"));
		factura.agregarDetalle(1, new Producto(3111, "Heladera con Freezer 317 lts Columbia", PRECIO_HELADERA,
				"Argentina"));
		factura.agregarDetalle(1, new Producto(2111, "Celular Galaxi A33 Samsung", PRECIO_CELULAR, "Argentina",
				true));

		credito = new Credito(tarjeta, factura);
	}

	@Test
	void testMontoCreditoNoSuperaMontoPermitido() {
		assertTrue(credito.getMontoCredito() <= MONTO_MAXIMO,
				"El monto total del credito no deberia superar los $2.500.000");
	}

	@Test
	void testSumaImportesDetallesIgualTotalFactura() {
		double totalEsperado = 2 * PRECIO_TELEVISOR + 1 * PRECIO_HELADERA + 1 * PRECIO_CELULAR;
		assertEquals(totalEsperado, factura.calcularTotal(), 0.001,
				"La suma de los importes de los detalles debe ser igual al total de la factura");
	}

	@Test
	void testMontoCompraNoSuperaPermitidoNiLimiteTarjeta() {
		double totalCompra = credito.getFactura().calcularTotal();
		assertTrue(totalCompra <= MONTO_MAXIMO, "El total no deberia superar el monto permitido de $2.500.000");
		assertTrue(totalCompra <= tarjeta.getLimiteCompra(),
				"El total no deberia superar el monto disponible en la tarjeta del cliente");
	}
}
