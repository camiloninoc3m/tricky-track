package co.com.c3m.trickytrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.RegistroPromocionDTO;
import co.com.c3m.trickytrack.api.RegistroPromocionResDTO;
import co.com.c3m.trickytrack.dominio.Promocion;
import co.com.c3m.trickytrack.repositorio.EstablecimientoDAO;
import co.com.c3m.trickytrack.repositorio.PromocionDAO;

@RestController
public class RegistroPromocionController {

	@Autowired
	private EstablecimientoDAO establecimientoDao;
	
	@Autowired
	private PromocionDAO dao;
	
	@RequestMapping(path="/promocion", method=RequestMethod.POST)
	public RegistroPromocionResDTO registrarPromocion(
			@RequestBody RegistroPromocionDTO dto) {
		
		Promocion entidad = new Promocion();
		entidad.setId(dto.getId());
		entidad.setNombre(dto.getNombre());
		entidad.setValor(dto.getValor());
		entidad.setFechaInicio(dto.getFechaInicio());
		entidad.setFechaFin(dto.getFechaFin());
		entidad.setCantidad(dto.getCantidad());
		entidad.setActiva(dto.getActiva());
		
		//TODO guardar en S3
		entidad.setImagen("");
		
		if (null == dto.getId()) {
			entidad.setEstablecimiento(establecimientoDao.findByCelular(dto.getCelular()));
		}
		
		Promocion buscada = 
				dao.findByEstablecimientoAndNombre(entidad.getEstablecimiento(), entidad.getNombre());
		
		if (buscada!=null) {
			throw new RuntimeException("Ya existe una promocion con este nombre para el establecimiento");
		}
		
		dao.save(entidad);
		
		RegistroPromocionResDTO respuesta = new RegistroPromocionResDTO();
		respuesta.setId(entidad.getId());
		return respuesta;
	}
}
