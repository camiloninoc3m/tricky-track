package co.com.c3m.trickytrack.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.repositorio.rowmapper.EstablecimientoRowmapper;

@RestController
public class EstablecimientoControlador {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/establecimientos")
	public List<Establecimiento> obtenerEstablecimientos(
			@RequestParam String nombre,
			@RequestParam Double longitud,
			@RequestParam Double latitud,
			@RequestParam Boolean validaHorario,
			@RequestParam List<String> generos){
		List<Object> parametros = new ArrayList<>();
		StringBuilder qry = new StringBuilder("SELECT * FROM establecimiento WHERE 1=1 ");

		if ((longitud!=null && latitud==null) || 
				(latitud!=null && longitud==null)) {
			throw new RuntimeException("Se debe ingresar latitud y longitud");
		}else if (longitud!=null && latitud!=null){
			qry.append("AND ID IN (SELECT ID FROM (SELECT id, ")
			.append("(6371*acos(cos(radians(?)) ")
			.append("*cos(radians(latitud)) ")
			.append("*cos(radians(longitud)-radians(?)) ")
			.append("+sin(radians(?)) ")
			.append("*sin(radians(latitud)))) AS distance ")
			.append("FROM establecimiento ")
			.append("HAVING distance < 1 ")
			.append("ORDER BY distance ")
			.append("LIMIT 0 , 20) AS TMP) ");
			
			parametros.add(latitud);
			parametros.add(longitud);
			parametros.add(latitud);
		}

		if (nombre!=null && !nombre.isEmpty()) {
			qry.append("AND NOMBRE LIKE ? ");
			parametros.add("%"+nombre+"%");
		}
		if (null!=validaHorario && validaHorario) {
			Calendar cal = Calendar.getInstance(
					TimeZone.getTimeZone("UTC-5"), 
					Locale.forLanguageTag("es"));
			int dia = cal.get(Calendar.DAY_OF_WEEK);
			dia--;
			switch (dia) {
			case 1:
				qry.append(" AND domingo=TRUE ");
				break;
			case 2:
				qry.append(" AND lunes=TRUE ");
				break;
			case 3:
				qry.append(" AND martes=TRUE ");
				break;
			case 4:
				qry.append(" AND miercoles=TRUE ");
				break;
			case 5:
				qry.append(" AND jueves=TRUE ");
				break;
			case 6:
				qry.append("AND viernes=TRUE ");
				break;
			case 7:
				qry.append(" AND sabado=TRUE ");
				break;
			default:
				break;
			}

			qry.append("AND TIME(?) BETWEEN hora_inicio AND '23:59:59' ")
			.append("OR TIME(?) BETWEEN '00:00:00' AND hora_cierre ");

			parametros.add(cal.getTime());
			parametros.add(cal.getTime());
		}

		return jdbcTemplate.query(qry.toString(), parametros.toArray(), new EstablecimientoRowmapper() );
	}
}
