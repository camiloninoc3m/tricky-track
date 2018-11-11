package co.com.c3m.trickytrack.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.Establecimiento;

@Repository
public interface EstablecimientoDAO extends CrudRepository<Establecimiento, Long>{
	
	Establecimiento findByCelular(String celular);
}
