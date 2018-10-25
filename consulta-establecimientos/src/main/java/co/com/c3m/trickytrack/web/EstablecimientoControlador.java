package co.com.c3m.trickytrack.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.dominio.Establecimiento;

@RestController
public class EstablecimientoControlador {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@RequestMapping("/establecimientos")
	public List<Establecimiento> obtenerEstablecimientos(
			@RequestParam String nombre,
			@RequestParam Double longitud,
			@RequestParam Double latitud,
			@RequestParam Boolean lunes,
			@RequestParam Boolean martes,
			@RequestParam Boolean miercoles,
			@RequestParam Boolean jueves,
			@RequestParam Boolean viernes,
			@RequestParam Boolean sabado,
			@RequestParam Boolean domingo,
			@RequestParam Date horaInicio,
			@RequestParam Date horaCierre,
			@RequestParam List<String> generos){
		List<String> parametros = new ArrayList<>();
		StringBuilder qry = new StringBuilder("SELECT * FROM ESTABLECIMIENTO WHERE 1=1");
		
		if ((longitud!=null && latitud==null) || 
				(latitud!=null && longitud==null)) {
			throw new RuntimeException("Se debe ingresar latitud y longitud");
		}
		if (nombre!=null && !nombre.isEmpty()) {
			qry.append("AND NOMBRE LIKE ? ");
			parametros.add("%"+nombre+"%");
		}
		
		return jdbcTemplate.queryForList(qry.toString(), parametros.toArray(), Establecimiento.class);
	}
}
