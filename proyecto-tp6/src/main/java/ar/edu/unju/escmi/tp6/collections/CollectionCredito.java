package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Credito;

public class CollectionCredito {

	 public static List<Credito> creditos = new ArrayList<Credito>();

	 public static boolean agregarCredito(Credito credito) {

		if (credito == null) {
			System.out.println("\nNO SE PUEDE GUARDAR EL CREDITO: el credito es nulo.");
			return false;
		}

		if (creditos.contains(credito)) {
			System.out.println("\nNO SE PUEDE GUARDAR EL CREDITO: ya existe en la lista.");
			return false;
		}

		creditos.add(credito);
		return true;
	}
}
