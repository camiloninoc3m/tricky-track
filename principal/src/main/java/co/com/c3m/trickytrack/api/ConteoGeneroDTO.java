package co.com.c3m.trickytrack.api;

public class ConteoGeneroDTO {
	
	private String genero;
	
	private Integer total;
	
	public ConteoGeneroDTO() {
		super();
	}

	public ConteoGeneroDTO(String genero, Integer total) {
		super();
		this.genero = genero;
		this.total = total;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genero == null) ? 0 : genero.hashCode());
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
		ConteoGeneroDTO other = (ConteoGeneroDTO) obj;
		if (genero == null) {
			if (other.genero != null)
				return false;
		} else if (!genero.equals(other.genero))
			return false;
		return true;
	}
}
