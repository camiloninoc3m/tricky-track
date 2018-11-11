package co.com.c3m.trickytrack.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity@
Table(
		uniqueConstraints=@UniqueConstraint(columnNames={"establecimiento_id", "nombre"})
)
public class Promocion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nombre;
	
	@NotNull
	private Integer valor;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	@NotNull
	private Boolean activa = Boolean.TRUE;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion = new Date();
	
	private Integer cantidad;
	
	private String imagen;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Establecimiento establecimiento;
	
	@OneToMany(mappedBy="promocion")
	private List<Cupon> cupones = new ArrayList<>();
	
	public Promocion() {
		super();
	}

	public Promocion(String nombre, Integer valor, Date fechaInicio, Date fechaFin, 
			Establecimiento establecimiento) {
		super();
		this.nombre = nombre;
		this.valor = valor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.establecimiento = establecimiento;
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

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
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
		result = prime * result + ((establecimiento == null) ? 0 : establecimiento.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Promocion other = (Promocion) obj;
		if (establecimiento == null) {
			if (other.establecimiento != null)
				return false;
		} else if (!establecimiento.equals(other.establecimiento))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
