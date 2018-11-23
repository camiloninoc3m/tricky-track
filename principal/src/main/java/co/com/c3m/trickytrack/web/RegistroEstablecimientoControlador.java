package co.com.c3m.trickytrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.RegistroEstablecimientoResDTO;
import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.repositorio.EstablecimientoDAO;

@RestController
public class RegistroEstablecimientoControlador {

	@Autowired
	private EstablecimientoDAO dao;
	
	@RequestMapping(path="/establecimiento", method=RequestMethod.POST)
	public RegistroEstablecimientoResDTO registrarEstablecimiento(
			@RequestBody Establecimiento establecimiento) {
		Establecimiento buscado = dao.findByCelular(establecimiento.getCelular());
		if (buscado!=null) {
			throw new RuntimeException("El celular ingresado ya esta asociado a otro establecimiento con id:"+buscado.getId());
		}
		
		dao.save(establecimiento);
		
		RegistroEstablecimientoResDTO respuesta = new RegistroEstablecimientoResDTO();
		respuesta.setId(establecimiento.getId());
		return respuesta;
	}
}
