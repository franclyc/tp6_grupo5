package ar.edu.unju.escmi.tp6.dominio;

public class Stock {
	 private int cantidad;
	 private Producto producto;

	 public Stock() {
     }

    public Stock(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public void decrementarStock(int cantidadADescontar) {
	if (cantidadADescontar <= 0) {
		throw new IllegalArgumentException("La cantidad a descontar debe ser mayor a 0");
	}
	if (cantidadADescontar > this.cantidad) {
		throw new IllegalArgumentException("Stock insuficiente");
	}
	this.cantidad -= cantidadADescontar;
}

}
