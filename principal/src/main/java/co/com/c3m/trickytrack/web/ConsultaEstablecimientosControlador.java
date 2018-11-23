package co.com.c3m.trickytrack.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.ConteoClientesDTO;
import co.com.c3m.trickytrack.api.ConteoEdadesDTO;
import co.com.c3m.trickytrack.api.ConteoGeneroDTO;
import co.com.c3m.trickytrack.api.ReporteClientesResDTO;
import co.com.c3m.trickytrack.dominio.Cliente;
import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.dominio.GeneroMusical;
import co.com.c3m.trickytrack.dominio.TipoGenero;
import co.com.c3m.trickytrack.repositorio.ClienteDAO;
import co.com.c3m.trickytrack.repositorio.EstablecimientoDAO;
import co.com.c3m.trickytrack.repositorio.GeneroDAO;
import co.com.c3m.trickytrack.repositorio.rowmapper.EstablecimientoRowmapper;
import co.com.c3m.trickytrack.util.CadenaUtil;
import co.com.c3m.trickytrack.util.FechaUtil;

@RestController
public class ConsultaEstablecimientosControlador {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EstablecimientoDAO dao;
	
	@Autowired
	private ClienteDAO clienteDao;
	
	@Autowired
	private GeneroDAO generoDao;

	@RequestMapping("/establecimientos")
	public List<Establecimiento> obtenerEstablecimientos(
			@RequestParam String nombre,
			@RequestParam Double longitud,
			@RequestParam Double latitud,
			@RequestParam Integer distancia,
			@RequestParam Boolean validaHorario,
			@RequestParam List<String> generos){
		List<Object> parametros = new ArrayList<>();
		StringBuilder qry = new StringBuilder("SELECT e.* FROM establecimiento e ")
				.append("WHERE 1=1 ");

		if ((longitud!=null && latitud==null) || 
				(latitud!=null && longitud==null)) {
			throw new RuntimeException("Se debe ingresar latitud y longitud");
		}else if (null!=distancia && longitud!=null && latitud!=null){
			qry.append("AND e.ID IN (SELECT ID FROM (SELECT id, ")
			.append("(6371*acos(cos(radians(?)) ")
			.append("*cos(radians(latitud)) ")
			.append("*cos(radians(longitud)-radians(?)) ")
			.append("+sin(radians(?)) ")
			.append("*sin(radians(latitud)))) AS distance ")
			.append("FROM establecimiento ")
			.append("HAVING distance < ? ")
			.append("ORDER BY distance ")
			.append("LIMIT 0 , 20) AS TMP) ");

			parametros.add(latitud);
			parametros.add(longitud);
			parametros.add(latitud);
			parametros.add(distancia*0.001);
		}

		if (nombre!=null && !nombre.isEmpty()) {
			qry.append("AND e.NOMBRE LIKE ? ");
			parametros.add("%"+nombre+"%");
		}
		if (null!=validaHorario && validaHorario) {
			Calendar cal = Calendar.getInstance(
					TimeZone.getTimeZone("UTC-5"), 
					Locale.forLanguageTag("es"));
			System.out.println("Hora actual:"+cal.getTime());
			int dia = cal.get(Calendar.DAY_OF_WEEK);
			switch (dia) {
			case Calendar.SUNDAY:
				qry.append("AND e.domingo=TRUE ");
				break;
			case Calendar.MONDAY:
				qry.append("AND e.lunes=TRUE ");
				break;
			case Calendar.TUESDAY:
				qry.append("AND e.martes=TRUE ");
				break;
			case Calendar.WEDNESDAY:
				qry.append("AND e.miercoles=TRUE ");
				break;
			case Calendar.THURSDAY:
				qry.append("AND e.jueves=TRUE ");
				break;
			case Calendar.FRIDAY:
				qry.append("AND e.viernes=TRUE ");
				break;
			case Calendar.SATURDAY:
				qry.append("AND e.sabado=TRUE ");
				break;
			default:
				break;
			}

			qry.append("AND TIME(?) BETWEEN e.hora_inicio AND '23:59:59' ")
			.append("OR TIME(?) BETWEEN '00:00:00' AND e.hora_cierre ");

			parametros.add(cal.getTime());
			parametros.add(cal.getTime());
		}
		
		if(null!=generos && !generos.isEmpty()) {
			qry.append("AND id in (select establecimiento_id from establecimiento_generos where generos_id in (select id from genero_musical where nombre in ("+CadenaUtil.repetir("?", generos.size(), ",")+"))) ");
			
			parametros.addAll(generos);
		}

		qry.append("LIMIT 0,20 ");

		return jdbcTemplate.query(qry.toString(), parametros.toArray(), new EstablecimientoRowmapper() );
	}
	
	@RequestMapping(path="/establecimiento/{id}/reporteClientes", method=RequestMethod.GET)
	public ReporteClientesResDTO obtenerReporte(
			@PathVariable("id")Long id) {
		ReporteClientesResDTO response = new ReporteClientesResDTO();
		
		Establecimiento establecimiento = dao.findById(id).get();
		Date ahora = new Date();
		Date fechaInicio = FechaUtil.reducirHoras(ahora, 1);
		Date fechaFin = FechaUtil.agregarHoras(ahora, 1);
		
		List<Cliente> clientes = clienteDao.obtenerPorCuponesRedimidos(
				fechaInicio, fechaFin, establecimiento);
		
		Iterable<GeneroMusical> generos = generoDao.findAll();
		
		Integer total = 0;
		Integer hombres = 0;
		Integer mujeres = 0;
		
		Integer edad;
		Integer hasta26 = 0;
		Integer de27a35 = 0;
		Integer de36a45 = 0;
		Integer desde49 = 0;
		
		List<ConteoGeneroDTO> conteoGeneros = new ArrayList<>();
		
		for (GeneroMusical genero : generos) {
			ConteoGeneroDTO conteo = new ConteoGeneroDTO(genero.getNombre(),0);
			conteoGeneros.add(conteo);
		}
		
		for (Cliente cliente : clientes) {
			total ++;
			if (TipoGenero.HOMBRE.equals(cliente.getGenero())) {
				hombres  ++;
			}else {
				mujeres ++;
			}
			
			edad = cliente.getEdad();
			
			if (edad<=26) {
				hasta26 ++;
			}else if(edad>=27 && edad<=35) {
				de27a35++;
			}else if(edad>=36 && edad<=45) {
				de36a45++;
			}else {
				desde49++;
			}
			
			for (GeneroMusical genero : generos) {
				ConteoGeneroDTO conteo = new ConteoGeneroDTO(genero.getNombre(),1);
				int index = conteoGeneros.indexOf(conteo);
				
				if (cliente.getGeneros().contains(genero)) {
					conteoGeneros.remove(conteo);
					conteo.setTotal(conteoGeneros.get(index).getTotal() +1);
					conteoGeneros.add(conteo);
				}
			}
		}
		
		ConteoClientesDTO conteoCliente = new ConteoClientesDTO();
		conteoCliente.setTotal(total);
		conteoCliente.setHombres(hombres);
		conteoCliente.setMujeres(mujeres);
		
		ConteoEdadesDTO conteoEdades = new ConteoEdadesDTO();
		conteoEdades.setHasta26(hasta26);
		conteoEdades.setDe27a35(de27a35);
		conteoEdades.setDe36a45(de36a45);
		conteoEdades.setDesde49(desde49);
		
		response.setClientes(conteoCliente);
		response.setEdades(conteoEdades);
		response.setGeneros(conteoGeneros);
		
		return response;
	}
}
