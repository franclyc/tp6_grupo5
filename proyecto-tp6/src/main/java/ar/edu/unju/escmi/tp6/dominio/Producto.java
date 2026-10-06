package ar.edu.unju.escmi.tp6.dominio;

public class Producto {
    private long codigo;
    private String descripcion;
    private double precioUnitario;
    private String origenFabricacion;
    private boolean esTelefono; // Agregado para controlar el limite de $1.000.000

    public Producto() {}

    public Producto(long codigo, String descripcion, double precioUnitario, String origenFabricacion, boolean esTelefono) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.origenFabricacion = origenFabricacion;
        this.esTelefono = esTelefono;
    }

    public long getCodigo() { return codigo; }

    public void setCodigo(long codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }

    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public String getOrigenFabricacion() { return origenFabricacion; }

    public void setOrigenFabricacion(String origenFabricacion) { this.origenFabricacion = origenFabricacion; }
    
    public boolean isEsTelefono() { return esTelefono; }

    public void setEsTelefono(boolean esTelefono) { this.esTelefono = esTelefono; }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " Descripcion: " + descripcion + " Precio Unitario: " + precioUnitario
                + " Origen fabricacion: " + origenFabricacion;
    }
}