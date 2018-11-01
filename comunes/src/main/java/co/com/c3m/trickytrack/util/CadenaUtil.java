package co.com.c3m.trickytrack.util;

public class CadenaUtil {

	public static String repetir(String cadena, Integer repeticiones, String separador) {
		String repetido = cadena;
		for (int i = 1; i < repeticiones; i++) {
			repetido += (separador+cadena);
		}
		return repetido;
	}
}
