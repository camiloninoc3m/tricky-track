package co.com.c3m.trickytrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.api.EntradaRedimirDTO;
import co.com.c3m.trickytrack.api.RedimirPromocionResDTO;
import co.com.c3m.trickytrack.dominio.Cliente;
import co.com.c3m.trickytrack.dominio.Cupon;
import co.com.c3m.trickytrack.dominio.Promocion;
import co.com.c3m.trickytrack.repositorio.ClienteDAO;
import co.com.c3m.trickytrack.repositorio.CuponDAO;

@RestController
public class RedimirPromocionesControlador {
	
	@Autowired
	private ClienteDAO clienteDao;
	
	@Autowired
	private CuponDAO dao;

	@RequestMapping(path="/cupon", method=RequestMethod.POST)
	public RedimirPromocionResDTO redimirPromocion(
			@RequestBody EntradaRedimirDTO entrada){
		Promocion promocion = new Promocion();
		promocion.setId(entrada.getIdPromocion());
		
		//TODO Generar QR y almacenar en S3
		String codigoQR = "http://bucket.s3.amazonaws.com/0987654321";
		
		Cliente cliente = clienteDao.findByCelular(entrada.getCelular());
		
		Cupon buscado = dao.findByPromocionAndCliente(promocion, cliente);
		
		if (buscado!=null) {
			throw new RuntimeException("El cliente ya ha adquirido un cupon para esa promocion");
		
		}
		
		Cupon cupon = new Cupon(promocion, cliente, codigoQR);
		dao.save(cupon);
		
		RedimirPromocionResDTO respuesta = new RedimirPromocionResDTO();
		respuesta.setUrl(codigoQR);
		return respuesta;
	}
}
