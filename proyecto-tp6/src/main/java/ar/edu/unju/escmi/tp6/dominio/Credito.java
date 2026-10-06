package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.exceptions.LimiteTarjetaInsuficienteException;
import ar.edu.unju.escmi.tp6.exceptions.MontoExcedidoException;

public class Credito {
    private double montoCredito; // Atributo que faltaba del diagrama
    private TarjetaCredito tarjetaCredito;
    private Factura factura;
    private List<Cuota> cuotas = new ArrayList<Cuota>();

    public Credito() {}

    public Credito(TarjetaCredito tarjetaCredito, Factura factura, List<Cuota> cuotas) {
        double total = factura.calcularTotal();
        double totalTelefonos = 0;
        for (Detalle detalle : factura.getDetalles()) {
            if (detalle.getProducto().isEsTelefono()) {
                totalTelefonos += detalle.getImporte();
            }
        }
        if (total > 2500000 || totalTelefonos > 1000000) {
            throw new MontoExcedidoException("El monto de la compra supera el limite permitido por Ahora 20");
        }
        if (total > tarjetaCredito.getLimiteCompra()) {
            throw new LimiteTarjetaInsuficienteException("La tarjeta no tiene limite suficiente para la compra");
        }
        this.tarjetaCredito = tarjetaCredito;
        this.factura = factura;
        this.cuotas = cuotas;
        this.montoCredito = factura.calcularTotal();
        generarCuotas();
    }

    public Credito(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    public double getMontoCredito() { return montoCredito; }

    public void setMontoCredito(double montoCredito) { this.montoCredito = montoCredito; }

    public TarjetaCredito getTarjetaCredito() { return tarjetaCredito; }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) { this.tarjetaCredito = tarjetaCredito; }

    public Factura getFactura() { return factura; }

    public void setFactura(Factura factura) { this.factura = factura; }

    public List<Cuota> getCuotas() { return cuotas; }
    
    public void setCuotas(List<Cuota> cuotas) { this.cuotas = cuotas; }
    
    public void generarCuotas() {
        // Se cambió a 20 cuotas como exige el plan "Ahora 20"
        double montoCuota = this.factura.calcularTotal() / 20; 
        int nroCuota = 0;
        LocalDate currentDate = LocalDate.now();
        LocalDate auxDate = LocalDate.now();

        for (int i = 0; i < 20; i++) {
            nroCuota++;
            Cuota cuota = new Cuota();
            cuota.setMonto(montoCuota);
            cuota.setNroCuota(nroCuota);
            cuota.setFechaGeneracion(currentDate); 
            auxDate = auxDate.plusMonths(1);
            cuota.setFechaVencimiento(auxDate);
            cuotas.add(cuota);
        }
    }

    public void mostarCredito() {
        System.out.println("Tarjeta De Credito: " + tarjetaCredito + "\n" + factura + "\nCant. Cuotas:\n");
        for(Cuota cuota: cuotas) {
            System.out.println(cuota);
        }
    }
}