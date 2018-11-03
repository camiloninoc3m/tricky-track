package co.com.c3m.trickytrack.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.GeneroMusical;

@Repository
public interface GeneroDAO extends CrudRepository<GeneroMusical, Long>{

}
