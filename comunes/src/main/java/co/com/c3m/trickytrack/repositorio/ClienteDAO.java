package co.com.c3m.trickytrack.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.c3m.trickytrack.dominio.Cliente;

@Repository
public interface ClienteDAO extends CrudRepository<Cliente, Long>{

	Cliente findByCelular(String celular);
}
