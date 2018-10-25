package co.com.c3m.trickytrack.web;

import java.util.ArrayList;
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
		StringBuilder qry = new StringBuilder("SELECT * FROM ESTABLECIMIENTO WHERE 1=1");
		
		if ((longitud!=null && latitud==null) || 
				(latitud!=null && longitud==null)) {
			throw new RuntimeException("Se debe ingresar latitud y longitud");
		}
		if (nombre!=null && !nombre.isEmpty()) {
			qry.append("AND NOMBRE LIKE ? ");
			parametros.add("%"+nombre+"%");
		}
		
		return jdbcTemplate.query(qry.toString(), parametros.toArray(), new EstablecimientoRowmapper() );
	}
}
