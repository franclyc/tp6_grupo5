package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class CollectionStock {

	public static List<Stock> stocks = new ArrayList<Stock>();

	public static void precargarStocks() {
		if (stocks.isEmpty()) {
			stocks = new ArrayList<Stock>();
			stocks.add(new Stock(12, CollectionProducto.productos.get(0)));
			stocks.add(new Stock(22, CollectionProducto.productos.get(1)));
			stocks.add(new Stock(13, CollectionProducto.productos.get(2)));
			stocks.add(new Stock(101, CollectionProducto.productos.get(3)));
			stocks.add(new Stock(87, CollectionProducto.productos.get(4)));
			stocks.add(new Stock(45, CollectionProducto.productos.get(5)));
			stocks.add(new Stock(16, CollectionProducto.productos.get(6)));
			stocks.add(new Stock(8, CollectionProducto.productos.get(7)));
			stocks.add(new Stock(5, CollectionProducto.productos.get(8)));
			stocks.add(new Stock(21, CollectionProducto.productos.get(9)));
			stocks.add(new Stock(17, CollectionProducto.productos.get(10)));
			stocks.add(new Stock(11, CollectionProducto.productos.get(11)));
			stocks.add(new Stock(8, CollectionProducto.productos.get(12)));
			stocks.add(new Stock(14, CollectionProducto.productos.get(13)));
			stocks.add(new Stock(4, CollectionProducto.productos.get(14)));
			stocks.add(new Stock(15, CollectionProducto.productos.get(15)));
			stocks.add(new Stock(28, CollectionProducto.productos.get(16)));
			stocks.add(new Stock(47, CollectionProducto.productos.get(17)));
			stocks.add(new Stock(33, CollectionProducto.productos.get(18)));
			stocks.add(new Stock(13, CollectionProducto.productos.get(19)));
		}
	}

		public static void agregarStock(Stock stock) {
		if (stock == null || stock.getProducto() == null) {
			throw new IllegalArgumentException("No se puede guardar un stock sin producto");
		}
		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).getProducto() == stock.getProducto()) {
				stocks.set(i, stock);
				return;
			}
		}
		stocks.add(stock);
	}

		public static void reducirStock(Stock stock, int cantidad) {
		if (stock == null || !stocks.contains(stock)) {
			throw new IllegalArgumentException("El stock indicado no esta registrado");
		}
		stock.decrementarStock(cantidad);
	}

	public static Stock buscarStock(Producto producto) {
		for (Stock sto : stocks) {
			if (sto.getProducto() == producto) {
				return sto;
			}
		}
		return null;
	}
}
