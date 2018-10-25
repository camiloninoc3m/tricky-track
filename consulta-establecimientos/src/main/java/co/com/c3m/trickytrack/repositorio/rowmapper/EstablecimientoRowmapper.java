package co.com.c3m.trickytrack.repositorio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.com.c3m.trickytrack.dominio.Establecimiento;

public class EstablecimientoRowmapper implements RowMapper<Establecimiento>{

	@Override
	public Establecimiento mapRow(ResultSet rs, int rowNum) throws SQLException {
		Establecimiento establecimiento = new Establecimiento();
		
		establecimiento.setId(rs.getLong("ID"));
		establecimiento.setNombre(rs.getString("NOMBRE"));
		establecimiento.setDireccion(rs.getString("DIRECCION"));
		establecimiento.setLatitud(rs.getDouble("LATITUD"));
		establecimiento.setLongitud(rs.getDouble("LONGITUD"));
		establecimiento.setLunes(rs.getBoolean("LUNES"));
		establecimiento.setMartes(rs.getBoolean("MARTES"));
		establecimiento.setMiercoles(rs.getBoolean("MIERCOLES"));
		establecimiento.setJueves(rs.getBoolean("JUEVES"));
		establecimiento.setViernes(rs.getBoolean("VIERNES"));
		establecimiento.setSabado(rs.getBoolean("SABADO"));
		establecimiento.setDomingo(rs.getBoolean("DOMINGO"));
		establecimiento.setHoraInicio(rs.getTime("HORA_INICIO"));
		establecimiento.setHoraCierre(rs.getTime("HORA_CIERRE"));
		
		return establecimiento;
	}
}
