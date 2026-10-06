package ar.edu.unju.escmi.tp6.dominio;

public class Stock {
     private int cantidad;
     private Producto producto;

     public Stock() {}

    public Stock(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    // Netodos agregados del diagrama
    public boolean validarStockDisponible(int cantidadRequerida) {
        return this.cantidad >= cantidadRequerida;
    }

    public void actualizarStock(int nuevaCantidad) {
        this.cantidad = nuevaCantidad;
    }

    public void decrementarStock(int cantidadVendida) {
        if(validarStockDisponible(cantidadVendida)) {
            this.cantidad -= cantidadVendida;
        }
    }
}