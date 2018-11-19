package co.com.c3m.trickytrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.RegistroClienteResDTO;
import co.com.c3m.trickytrack.dominio.Cliente;
import co.com.c3m.trickytrack.repositorio.ClienteDAO;

@RestController
public class RegistroClienteControlador {

	@Autowired
	private ClienteDAO dao;
	
	@RequestMapping(path="/cliente", method=RequestMethod.POST)
	public RegistroClienteResDTO registrarCliente(
			@RequestBody Cliente cliente) {
		Cliente buscado = dao.findByCelular(cliente.getCelular());
		
		if (buscado!=null) {
			throw new RuntimeException("El celular ingresado ya pertenece a otro cliente con id:"+buscado.getId());
		}
		
		dao.save(cliente);
		RegistroClienteResDTO respuesta = new RegistroClienteResDTO();
		respuesta.setId(cliente.getId());
		return respuesta;
	}
}
