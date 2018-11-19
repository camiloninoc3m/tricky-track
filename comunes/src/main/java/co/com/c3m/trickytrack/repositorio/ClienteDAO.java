package co.com.c3m.trickytrack.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.Cliente;
import co.com.c3m.trickytrack.dominio.Establecimiento;

@Repository
public interface ClienteDAO extends CrudRepository<Cliente, Long>{

	Cliente findByCelular(String celular);
	
	@Query("select cu.cliente from Cupon cu "
			+ "where cu.fechaRedencion between :fechaInicio and :fechaFin "
			+ "and cu.promocion.establecimiento = :establecimiento ")
	List<Cliente> obtenerPorCuponesRedimidos(
			@Param("fechaInicio")Date fechaInicio, 
			@Param("fechaFin")Date fechaFin, 
			@Param("establecimiento")Establecimiento establecimiento);
}
