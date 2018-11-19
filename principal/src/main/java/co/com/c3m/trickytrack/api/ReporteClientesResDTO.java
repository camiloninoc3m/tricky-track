package co.com.c3m.trickytrack.api;

import java.util.List;

public class ReporteClientesResDTO {
	
	private ConteoClientesDTO clientes;
	
	private ConteoEdadesDTO edades;
	
	private List<ConteoGeneroDTO> generos;

	public ConteoClientesDTO getClientes() {
		return clientes;
	}

	public void setClientes(ConteoClientesDTO clientes) {
		this.clientes = clientes;
	}

	public ConteoEdadesDTO getEdades() {
		return edades;
	}

	public void setEdades(ConteoEdadesDTO edades) {
		this.edades = edades;
	}

	public List<ConteoGeneroDTO> getGeneros() {
		return generos;
	}

	public void setGeneros(List<ConteoGeneroDTO> generos) {
		this.generos = generos;
	}
}
