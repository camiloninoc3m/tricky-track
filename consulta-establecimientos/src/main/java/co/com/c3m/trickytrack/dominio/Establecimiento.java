package co.com.c3m.trickytrack.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Establecimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private String direccion;
	private Double longitud;
	private Double latitud;
	private Boolean lunes;
	private Boolean martes;
	private Boolean miercoles;
	private Boolean jueves;
	private Boolean viernes;
	private Boolean sabado;
	private Boolean domingo;
	private Date horaInicio;
	private Date horaCierre;
	private List<String> generosMusicales = new ArrayList<>();
	
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
	public List<String> getGenerosMusicales() {
		return generosMusicales;
	}
	public void setGenerosMusicales(List<String> generosMusicales) {
		this.generosMusicales = generosMusicales;
	}
}
