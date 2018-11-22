
package co.edu.uniandes.trickytrack.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Edades {

    @SerializedName("hasta26")
    @Expose
    private Integer hasta26;
    @SerializedName("de27a35")
    @Expose
    private Integer de27a35;
    @SerializedName("de36a45")
    @Expose
    private Integer de36a45;
    @SerializedName("desde49")
    @Expose
    private Integer desde49;

    public Integer getHasta26() {
        return hasta26;
    }

    public void setHasta26(Integer hasta26) {
        this.hasta26 = hasta26;
    }

    public Integer getDe27a35() {
        return de27a35;
    }

    public void setDe27a35(Integer de27a35) {
        this.de27a35 = de27a35;
    }

    public Integer getDe36a45() {
        return de36a45;
    }

    public void setDe36a45(Integer de36a45) {
        this.de36a45 = de36a45;
    }

    public Integer getDesde49() {
        return desde49;
    }

    public void setDesde49(Integer desde49) {
        this.desde49 = desde49;
    }

}
