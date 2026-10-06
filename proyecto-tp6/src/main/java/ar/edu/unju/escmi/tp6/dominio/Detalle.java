package ar.edu.unju.escmi.tp6.dominio;

public class Detalle {
    private int cantidad;
    private double importe;
    private boolean estadoAhora20; // Atributo que faltaba del diagrama
    private Producto producto;

    public Detalle() {}

    public Detalle(int cantidad, Producto producto, boolean estadoAhora20) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.estadoAhora20 = estadoAhora20;
        calcularImporte();
    }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getImporte() { return importe; }

    public void setImporte(double importe) { this.importe = importe; }

    public boolean isEstadoAhora20() { return estadoAhora20; }

    public void setEstadoAhora20(boolean estadoAhora20) { this.estadoAhora20 = estadoAhora20; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto producto) { this.producto = producto; }

    private void calcularImporte() {
        this.setImporte(this.cantidad * this.producto.getPrecioUnitario());
    }

    @Override
    public String toString() {
        return "PRODUCTO: " + producto + "\nCANTIDAD: " + cantidad + " | IMPORTE: " + importe + "\n";
    }
}