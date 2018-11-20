package co.com.c3m.trickytrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.ConsultaUsuarioResDTO;
import co.com.c3m.trickytrack.api.RegistroClienteResDTO;
import co.com.c3m.trickytrack.dominio.Cliente;
import co.com.c3m.trickytrack.dominio.Establecimiento;
import co.com.c3m.trickytrack.repositorio.ClienteDAO;
import co.com.c3m.trickytrack.repositorio.EstablecimientoDAO;

@RestController
public class RegistroClienteControlador {

	@Autowired
	private EstablecimientoDAO establecimientoDao;
	
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
	
	@RequestMapping(path="/usuario/celular/{celular}", method=RequestMethod.GET)
	public ConsultaUsuarioResDTO consultarUsuario(
			@PathVariable("celular") String celular) {
		ConsultaUsuarioResDTO respuesta = new ConsultaUsuarioResDTO();
		Cliente cliente = dao.findByCelular(celular);
		Establecimiento establecimiento = establecimientoDao.findByCelular(celular);
		
		if (cliente!=null) {
			respuesta.setIdCliente(cliente.getId());
		}
		
		if (establecimiento!=null) {
			respuesta.setIdEstablecimiento(establecimiento.getId());
		}
		
		return respuesta;
	}
}
