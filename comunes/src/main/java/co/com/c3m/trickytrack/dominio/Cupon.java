package co.com.c3m.trickytrack.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(
		uniqueConstraints=@UniqueConstraint(columnNames={"promocion_id","cliente_id"})
)
public class Cupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String codigoQR;

	@NotNull
	@Temporal(TemporalType.TIME)
	private Date fechaAdquisicion = new Date();
	
	@Temporal(TemporalType.TIME)
	private Date fechaRedencion;
	
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotNull
	@ManyToOne
	private Promocion promocion;
	
	public Cupon() {
		super();
	}
	
	public Cupon(@NotNull Promocion promocion, @NotNull Cliente cliente, @NotNull String codigoQR) {
		super();
		this.promocion = promocion;
		this.cliente = cliente;
		this.codigoQR = codigoQR;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCodigoQR() {
		return codigoQR;
	}

	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}

	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Date getFechaRedencion() {
		return fechaRedencion;
	}

	public void setFechaRedencion(Date fechaRedencion) {
		this.fechaRedencion = fechaRedencion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((promocion == null) ? 0 : promocion.hashCode());
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
		Cupon other = (Cupon) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (promocion == null) {
			if (other.promocion != null)
				return false;
		} else if (!promocion.equals(other.promocion))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return promocion.getNombre();
	}
}
