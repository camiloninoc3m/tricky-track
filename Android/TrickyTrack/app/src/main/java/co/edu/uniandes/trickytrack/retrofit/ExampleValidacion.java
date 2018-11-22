
package co.edu.uniandes.trickytrack.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExampleValidacion implements Serializable {

    @SerializedName("idEstablecimiento")
    @Expose
    private Integer idEstablecimiento;


    @SerializedName("idCliente")
    @Expose
    private Integer idCliente;

    public Integer getIdEstablecimiento() {
        return idEstablecimiento;
    }

    public void setIdEstablecimiento(Integer idEstablecimiento) {
        this.idEstablecimiento = idEstablecimiento;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
}
