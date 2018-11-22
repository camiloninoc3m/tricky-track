
package co.edu.uniandes.trickytrack.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clientes {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("hombres")
    @Expose
    private Integer hombres;
    @SerializedName("mujeres")
    @Expose
    private Integer mujeres;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getHombres() {
        return hombres;
    }

    public void setHombres(Integer hombres) {
        this.hombres = hombres;
    }

    public Integer getMujeres() {
        return mujeres;
    }

    public void setMujeres(Integer mujeres) {
        this.mujeres = mujeres;
    }

}
