package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {
	
	public static final int MONTO_1 = 500000;
	public static final int MONTO_2 = 1500000;
	public static final int MONTO_3 = 1000;
	public static final double MONTO_MAXIMO = 2500000;
	
	@Test
	void testMontoCreditoValido() {
		double montoObtenido = crearFactura().calcularTotal();
		assertTrue(montoObtenido <= MONTO_MAXIMO , "El monto total no debería superar al monto permitido");
	}

	@Test
	void testSumaDetallesIgualTotalFactura() {
		Factura factura = crearFactura();
		double sumaEsperada = MONTO_1 + MONTO_2 + MONTO_3;
		assertEquals(sumaEsperada, factura.calcularTotal(), 0.001,
				"La suma de los importes debe ser igual al total de la factura");
	}

	@Test
	void testMontoCompraValidoConTarjeta() {
		TarjetaCredito tarjeta = new TarjetaCredito();
		tarjeta.setLimiteCompra(3000000);

		double totalCompra = crearFactura().calcularTotal();

		assertTrue(totalCompra <= MONTO_MAXIMO,
				"El total no debería superar el monto permitido");
		assertTrue(totalCompra <= tarjeta.getLimiteCompra(),
				"El total no debería superar el monto disponible en la tarjeta");
	}
	
	private Factura crearFactura() {
		Factura factura = new Factura();
		factura.setDetalles(crearListaDetalles());
		return factura;
	}
	
	private List<Detalle> crearListaDetalles(){
		List<Detalle> listaDetalles = new ArrayList<Detalle>();
		Detalle detalle1 = new Detalle();
		detalle1.setImporte(MONTO_1);
		Detalle detalle2 = new Detalle();
		detalle2.setImporte(MONTO_2);
		Detalle detalle3 = new Detalle();
		detalle3.setImporte(MONTO_3);
		listaDetalles.add(detalle1);
		listaDetalles.add(detalle2);
		listaDetalles.add(detalle3);
		return listaDetalles;
		
	}

}
