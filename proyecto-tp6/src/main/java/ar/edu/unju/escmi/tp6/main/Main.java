package ar.edu.unju.escmi.tp6.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.exceptions.ClienteNoEncontradoException;
import ar.edu.unju.escmi.tp6.exceptions.LimiteTarjetaInsuficienteException;
import ar.edu.unju.escmi.tp6.exceptions.MontoExcedidoException;
import ar.edu.unju.escmi.tp6.exceptions.StockInsuficienteException;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		CollectionCliente.precargarClientes();
        CollectionTarjetaCredito.precargarTarjetas();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
        int opcion = 0;
        try {
            do {
                System.out.println("\n====== Menu Principal =====");
                System.out.println("1- Realizar una venta");
                System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
                System.out.println("3- Mostrar lista de los electrodomésticos");
                System.out.println("4- Consultar stock");
                System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
                System.out.println("6- Salir");

                System.out.println("Ingrese su opcion: ");
                try {
                    opcion = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nDebe ingresar un numero valido");
                    scanner.nextLine();
                    opcion = 0;
                } catch (StockInsuficienteException e) {
                    System.out.println("\n" + e.getMessage());
                } catch (MontoExcedidoException e) {
                    System.out.println("\n" + e.getMessage());
                } catch (LimiteTarjetaInsuficienteException e) {
                    System.out.println("\n" + e.getMessage());
                } catch (ClienteNoEncontradoException e) {
                    System.out.println("\n" + e.getMessage());
                }
            } while (opcion != 6);
        } finally {
            scanner.close();
        }
	}

}
