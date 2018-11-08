package co.com.c3m.trickytrack.util;

import java.util.Calendar;
import java.util.Date;

public class FechaUtil {

	public static Date construirTiempo(Integer hora, Integer minuto, Integer segundo) {
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, hora);  
        cal.set(Calendar.MINUTE, minuto);  
        cal.set(Calendar.SECOND, segundo);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime(); 
	}
	
	public static Date construirFechaTiempo(Integer ano, Integer mes, Integer dia,
			Integer hora, Integer minuto, Integer segundo) {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mes, dia, hora, minuto, segundo);
        return cal.getTime(); 
	}
	
	public static Date contruirInicioDia(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);;
		cal.set(Calendar.HOUR_OF_DAY, 00);  
        cal.set(Calendar.MINUTE, 00);  
        cal.set(Calendar.SECOND, 00);
        
        return cal.getTime();
	}
	
	public static Date contruirFinDia(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);;
		cal.set(Calendar.HOUR_OF_DAY, 23);  
        cal.set(Calendar.MINUTE, 59);  
        cal.set(Calendar.SECOND, 59);
        
        return cal.getTime();
	}
}
