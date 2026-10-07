package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

	// el test se decrementa el stock de un producto en la cantidad indicada
	@Test
	void testDecrementarStock() {
		Producto producto = new Producto(1L, "Yerba Mate 1kg", 3500, "Argentina");
		Stock stock = new Stock(50, producto);
		int cantidadADescontar = 10;

		stock.decrementarStock(cantidadADescontar);

		assertEquals(40, stock.getCantidad(),
				"El stock debería haberse decrementado en la cantidad indicada");
	}
}