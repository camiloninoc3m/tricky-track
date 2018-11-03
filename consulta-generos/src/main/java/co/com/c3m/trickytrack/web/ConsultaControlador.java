package co.com.c3m.trickytrack.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.c3m.trickytrack.dominio.GeneroMusical;
import co.com.c3m.trickytrack.repositorio.GeneroDAO;

@RestController
public class ConsultaControlador {

	@Autowired
	private GeneroDAO dao;

	@RequestMapping("/generos")
	public List<GeneroMusical> obtenerTodos(){
		List<GeneroMusical> generos = new ArrayList<>();
		dao.findAll().iterator().forEachRemaining(generos::add);
		return generos;
	}
}
