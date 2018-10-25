package co.com.c3m.trickytrack.util;

import java.util.Calendar;
import java.util.Date;

public class FechaUtil {

	public static Date obtenerFechaTiempo(Integer hora, Integer minuto, Integer segundo) {
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, hora);  
        cal.set(Calendar.MINUTE, minuto);  
        cal.set(Calendar.SECOND, segundo);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime(); 
	}
}
