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

class CuotaTest {

	public static final int CANTIDAD_CUOTAS_PERMITIDAS = 20;

	private Credito credito;

	@BeforeEach
	void setUp() {
		Cliente cliente = new Cliente(12345678L, "Juan Perez", "Calle Falsa 123", "388123456");
		TarjetaCredito tarjeta = new TarjetaCredito(1111222233334444L, LocalDate.now().plusYears(2), cliente,
				3000000);
		Factura factura = new Factura(LocalDate.now(), 1, cliente);
		factura.agregarDetalle(1, new Producto(4111, "Lavarropas Semi 7 Kgs Columbia", 210000, "Argentina"));
		credito = new Credito(tarjeta, factura);
	}

	@Test
	void testListaCuotasNoNull() {
		assertNotNull(credito.getCuotas(), "La lista de cuotas no deberia ser null");
	}

	@Test
	void testListaTiene20Cuotas() {
		assertEquals(CANTIDAD_CUOTAS_PERMITIDAS, credito.getCuotas().size(),
				"La lista deberia tener exactamente 20 cuotas");
	}

	@Test
	void testCuotasGeneradasNoSuperanPermitidas() {
		assertTrue(credito.getCuotas().size() <= CANTIDAD_CUOTAS_PERMITIDAS,
				"La cantidad de cuotas generadas no deberia superar las permitidas");
	}
}
