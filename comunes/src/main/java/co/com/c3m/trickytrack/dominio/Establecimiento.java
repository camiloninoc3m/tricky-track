package co.com.c3m.trickytrack.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import co.com.c3m.trickytrack.util.FechaUtil;

@Entity
public class Establecimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	
	@NotNull
	@Column(unique=true)
	private String celular;
	
	private String direccion;
	
	@NotNull
	private Double longitud;
	
	@NotNull
	private Double latitud;
	
	@NotNull
	private Boolean lunes = Boolean.TRUE;
	
	@NotNull
	private Boolean martes = Boolean.TRUE;
	
	@NotNull
	private Boolean miercoles = Boolean.TRUE;
	
	@NotNull
	private Boolean jueves = Boolean.TRUE;
	
	@NotNull
	private Boolean viernes = Boolean.TRUE;
	
	@NotNull
	private Boolean sabado = Boolean.TRUE;
	
	@NotNull
	private Boolean domingo = Boolean.TRUE;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date horaInicio = FechaUtil.construirTiempo(12, 00, 00);
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date horaCierre = FechaUtil.construirTiempo(03, 00, 00);
	
	@ManyToMany
	private List<GeneroMusical> generos = new ArrayList<>();
	
	@OneToMany(mappedBy="establecimiento")
	private List<Promocion> promociones = new ArrayList<>();

	public Establecimiento() {
		super();
	}

	public Establecimiento(String nombre, String celular, Double longitud,
			Double latitud) {
		super();
		this.nombre = nombre;
		this.celular = celular;
		this.longitud = longitud;
		this.latitud = latitud;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Boolean getLunes() {
		return lunes;
	}

	public void setLunes(Boolean lunes) {
		this.lunes = lunes;
	}

	public Boolean getMartes() {
		return martes;
	}

	public void setMartes(Boolean martes) {
		this.martes = martes;
	}

	public Boolean getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(Boolean miercoles) {
		this.miercoles = miercoles;
	}

	public Boolean getJueves() {
		return jueves;
	}

	public void setJueves(Boolean jueves) {
		this.jueves = jueves;
	}

	public Boolean getViernes() {
		return viernes;
	}

	public void setViernes(Boolean viernes) {
		this.viernes = viernes;
	}

	public Boolean getSabado() {
		return sabado;
	}

	public void setSabado(Boolean sabado) {
		this.sabado = sabado;
	}

	public Boolean getDomingo() {
		return domingo;
	}

	public void setDomingo(Boolean domingo) {
		this.domingo = domingo;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}

	public List<GeneroMusical> getGeneros() {
		return generos;
	}

	public void setGeneros(List<GeneroMusical> generos) {
		this.generos = generos;
	}

	public List<Promocion> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
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
		Establecimiento other = (Establecimiento) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  nombre;
	}
}
