package co.com.c3m.trickytrack.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.PromocionDTO;
import co.com.c3m.trickytrack.conversor.PromocionConversor;
import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.repositorio.PromocionDAO;

@RestController
public class ConsultaPromocionesControlador {
	
	@Autowired
	private PromocionConversor conversor;

	@Autowired
	private PromocionDAO dao;
	
	@RequestMapping("/promociones/establecimiento/{idEstablecimiento}")
	public List<PromocionDTO> obtenerPromociones(
			@PathVariable("idEstablecimiento") Long idEstablecimiento){
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setId(idEstablecimiento);
		return conversor.listEntidadesADtos(dao.encontrarDisponibles(establecimiento));
	}
}
