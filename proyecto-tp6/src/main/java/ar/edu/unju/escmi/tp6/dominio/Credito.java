package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.exceptions.LimiteTarjetaInsuficienteException;
import ar.edu.unju.escmi.tp6.exceptions.MontoExcedidoException;
import ar.edu.unju.escmi.tp6.exceptions.TarjetaVencidaException;

public class Credito {

	public static final int CANTIDAD_CUOTAS = 20;
	public static final double MONTO_MAXIMO = 2500000;
	public static final double MONTO_MAXIMO_TELEFONOS = 1000000;

	private double montoCredito;
	private TarjetaCredito tarjetaCredito;
	private Factura factura;
	private List<Cuota> cuotas = new ArrayList<Cuota>();

	public Credito() {

	}

	/**
	 * Crea el credito validando las reglas del programa Ahora 20 y genera las 20 cuotas.
	 * Composicion: el credito es el responsable de crear sus cuotas.
	 */
	public Credito(TarjetaCredito tarjetaCredito, Factura factura)
			throws TarjetaVencidaException, MontoExcedidoException, LimiteTarjetaInsuficienteException {
		validarTarjeta(tarjetaCredito);
		validarFactura(factura);
		validarMontos(tarjetaCredito, factura);
		this.tarjetaCredito = tarjetaCredito;
		this.factura = factura;
		this.montoCredito = factura.calcularTotal();
		generarCuotas();
	}

	public double getMontoCredito() {
		return montoCredito;
	}

	public void setMontoCredito(double montoCredito) {
		this.montoCredito = montoCredito;
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	private void validarTarjeta(TarjetaCredito tarjetaCredito) throws TarjetaVencidaException {
		if (tarjetaCredito == null) {
			throw new IllegalArgumentException("El credito necesita una tarjeta de credito");
		}
		if (!tarjetaCredito.estaVigente()) {
			throw new TarjetaVencidaException("La tarjeta " + tarjetaCredito.getNumero() + " vencio el "
					+ tarjetaCredito.getFechaCaducacion());
		}
	}

	private void validarFactura(Factura factura) {
		if (factura == null || factura.getDetalles().isEmpty()) {
			throw new IllegalArgumentException("El credito necesita una factura con al menos un producto");
		}
		if (!factura.esFacturaAhora20()) {
			throw new IllegalArgumentException("La factura tiene productos que no son de fabricacion nacional");
		}
	}

	private void validarMontos(TarjetaCredito tarjetaCredito, Factura factura)
			throws MontoExcedidoException, LimiteTarjetaInsuficienteException {
		double total = factura.calcularTotal();
		double totalTelefonos = 0;
		for (Detalle detalle : factura.getDetalles()) {
			if (detalle.getProducto() != null && detalle.getProducto().isEsTelefono()) {
				totalTelefonos += detalle.getImporte();
			}
		}
		if (total > MONTO_MAXIMO) {
			throw new MontoExcedidoException("El monto de la compra supera el limite de $2.500.000 del programa Ahora 20");
		}
		if (totalTelefonos > MONTO_MAXIMO_TELEFONOS) {
			throw new MontoExcedidoException("El monto en telefonos supera el limite de $1.000.000 del programa Ahora 20");
		}
		if (total > tarjetaCredito.getLimiteCompra()) {
			throw new LimiteTarjetaInsuficienteException("La tarjeta no tiene limite suficiente para la compra. Disponible: "
					+ tarjetaCredito.getLimiteCompra());
		}
	}

	public void generarCuotas() {
		cuotas = new ArrayList<Cuota>();
		double montoCuota = this.montoCredito / CANTIDAD_CUOTAS;
		LocalDate fechaGeneracion = LocalDate.now();
		LocalDate fechaVencimiento = LocalDate.now();

		for (int nroCuota = 1; nroCuota <= CANTIDAD_CUOTAS; nroCuota++) {
			fechaVencimiento = fechaVencimiento.plusMonths(1);
			cuotas.add(new Cuota(montoCuota, nroCuota, fechaGeneracion, fechaVencimiento));
		}
	}

	public void mostrarCredito() {
		System.out.println("Tarjeta De Credito: " + tarjetaCredito + "\n" + factura + "\nCuotas:\n");
		for (Cuota cuota : cuotas) {
			System.out.println(cuota);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Credito otro = (Credito) obj;
		if (this.factura == null || otro.factura == null) {
			return false;
		}
		return this.factura.getNroFactura() == otro.factura.getNroFactura();
	}

	@Override
	public int hashCode() {
		return factura == null ? 0 : Long.hashCode(factura.getNroFactura());
	}

}
