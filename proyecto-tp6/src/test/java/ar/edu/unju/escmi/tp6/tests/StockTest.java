package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

	public static final int STOCK_INICIAL = 50;

	private Stock stock;

	@BeforeEach
	void setUp() {
		Producto producto = new Producto(3112, "Heladera Ciclica Gafa", 160000, "Argentina");
		stock = new Stock(STOCK_INICIAL, producto);
	}

	@Test
	void testDecrementarStock() {
		int cantidadADescontar = 10;
		stock.decrementarStock(cantidadADescontar);
		assertEquals(STOCK_INICIAL - cantidadADescontar, stock.getCantidad(),
				"El stock deberia haberse decrementado en la cantidad indicada");
	}
}
