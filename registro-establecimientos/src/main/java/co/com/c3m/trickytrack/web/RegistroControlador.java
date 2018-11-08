package co.com.c3m.trickytrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.repositorio.EstablecimientoDAO;

@RestController
public class RegistroControlador {

	@Autowired
	private EstablecimientoDAO dao;
	
	@RequestMapping(path="/establecimiento", method=RequestMethod.POST)
	public Long registrarEstablecimiento(
			@RequestBody Establecimiento establecimiento) {
		dao.save(establecimiento);
		return establecimiento.getId();
	}
}
