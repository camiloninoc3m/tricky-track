package co.com.c3m.trickytrack.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import co.com.c3m.trickytrack.api.ConsultaPromocionDTO;
import co.com.c3m.trickytrack.dominio.Promocion;

@Component
public class PromocionConversor {

	public ConsultaPromocionDTO entidadADto(Promocion promocion) {
		ConsultaPromocionDTO dto = new ConsultaPromocionDTO();
		dto.setId(promocion.getId());
		dto.setNombre(promocion.getNombre());
		dto.setFechaInicio(promocion.getFechaInicio());
		dto.setFechaFin(promocion.getFechaFin());
		dto.setImagen(promocion.getImagen());
		
		return dto;
	}
	
	public List<ConsultaPromocionDTO> listEntidadesADtos(List<Promocion> promociones){
		List<ConsultaPromocionDTO> dtos = new ArrayList<>();
		for (Promocion entidad : promociones) {
			dtos.add(entidadADto(entidad));
		}
		
		return dtos;
	}
}
