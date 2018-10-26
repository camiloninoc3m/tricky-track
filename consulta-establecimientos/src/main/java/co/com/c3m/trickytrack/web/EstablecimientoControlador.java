package co.com.c3m.trickytrack.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
		List<String> parametros = new ArrayList<>();
		StringBuilder qry = new StringBuilder("SELECT * FROM ESTABLECIMIENTO WHERE 1=1 ");

		if ((longitud!=null && latitud==null) || 
				(latitud!=null && longitud==null)) {
			throw new RuntimeException("Se debe ingresar latitud y longitud");
		}else {
			qry.append("AND ID IN (SELECT ID FROM (SELECT id, ")
			.append("(6371 * acos ( cos ( radians(4.652052) )")
			.append("* cos( radians( latitud ) ) ")
			.append("* cos( radians( longitud ) - radians(-74.0555773) ) ")
			.append(" sin ( radians(4.652052) ) ")
			.append(" * sin( radians( latitud ) ) )) AS distance ")
			.append(" FROM establecimiento ")
			.append(" HAVING distance < 1 ")
			.append(" ORDER BY distance")
			.append(" LIMIT 0 , 20) AS TMP) ");
		}

		if (nombre!=null && !nombre.isEmpty()) {
			qry.append("AND NOMBRE LIKE ? ");
			parametros.add("%"+nombre+"%");
		}
		if (validaHorario) {
			Calendar cal = Calendar.getInstance();
			int dia = cal.get(Calendar.DAY_OF_WEEK);

			switch (dia) {
			case 1:
				qry.append(" AND LUNES=TRUE ");
				break;
			case 2:
				qry.append(" AND MARTES=TRUE ");
				break;
			case 3:
				qry.append(" AND MIERCOLES=TRUE ");
				break;
			case 4:
				qry.append(" AND JUEVES=TRUE ");
				break;
			case 5:
				qry.append("AND VIERNES=TRUE ");
				break;
			case 6:
				qry.append(" AND SABADO=TRUE ");
				break;
			case 7:
				qry.append(" AND DOMMINGO=TRUE ");
				break;
			default:
				break;
			}

			qry.append("AND ? BETWEEN HORA_INICIO AND HORA_CIERRE ");
		}

		return jdbcTemplate.query(qry.toString(), parametros.toArray(), new EstablecimientoRowmapper() );
	}
}
