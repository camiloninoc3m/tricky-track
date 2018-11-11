package co.com.c3m.trickytrack.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.dominio.Promocion;

@Repository
public interface PromocionDAO extends CrudRepository<Promocion, Long>{

	List<Promocion> findAllByEstablecimiento(Establecimiento establecimiento);
	
	@Query("select p from Promocion p  "
			+ "where p.establecimiento=:establecimiento "
			+ "and (current_timestamp() between p.fechaInicio and p.fechaFin) "
			+ "and (p.cantidad is null or p.cantidad is not null and p.cantidad>p.cupones.size) ")
	List<Promocion> encontrarDisponibles(@Param("establecimiento")Establecimiento establecimiendo);
}
