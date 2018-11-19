package co.com.c3m.trickytrack.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.Cliente;
import co.com.c3m.trickytrack.dominio.Cupon;
import co.com.c3m.trickytrack.dominio.Promocion;

@Repository
public interface CuponDAO extends CrudRepository<Cupon, Long>{

	Cupon findByPromocionAndCliente(Promocion promocion, Cliente cliente);
}
