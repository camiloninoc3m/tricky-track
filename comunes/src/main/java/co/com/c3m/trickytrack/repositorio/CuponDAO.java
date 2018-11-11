package co.com.c3m.trickytrack.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.Cupon;

@Repository
public interface CuponDAO extends CrudRepository<Cupon, Long>{

}
