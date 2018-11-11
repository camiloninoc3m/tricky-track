package co.com.c3m.trickytrack.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	
	@NotNull
	@Column(unique=true)
	private String celular;
	
	@NotNull
	private Integer edad;
	
	@NotNull
	private TipoGenero genero;
	
	@ManyToMany
	private List<GeneroMusical> generos = new ArrayList<>();
	
	@OneToMany(mappedBy="cliente")
	private List<Cupon> cupones = new ArrayList<>();
	
	public Cliente() {
		super();
	}
	
	public Cliente(String nombre, String celular, Integer edad, TipoGenero genero) {
		super();
		this.nombre = nombre;
		this.celular = celular;
		this.edad = edad;
		this.genero = genero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public TipoGenero getGenero() {
		return genero;
	}

	public void setGenero(TipoGenero genero) {
		this.genero = genero;
	}

	public List<GeneroMusical> getGeneros() {
		return generos;
	}

	public void setGeneros(List<GeneroMusical> generos) {
		this.generos = generos;
	}
	
	public List<Cupon> getCupones() {
		return cupones;
	}

	public void setCupones(List<Cupon> cupones) {
		this.cupones = cupones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
