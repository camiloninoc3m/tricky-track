
package co.edu.uniandes.trickytrack.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Generos {

    @SerializedName("rock")
    @Expose
    private Integer rock;
    @SerializedName("punk")
    @Expose
    private Integer punk;
    @SerializedName("newage")
    @Expose
    private Integer newage;
    @SerializedName("vallenato")
    @Expose
    private Integer vallenato;
    @SerializedName("salsa")
    @Expose
    private Integer salsa;
    @SerializedName("anos80")
    @Expose
    private Integer anos80;
    @SerializedName("merengue")
    @Expose
    private Integer merengue;
    @SerializedName("regueton")
    @Expose
    private Integer regueton;
    @SerializedName("metal")
    @Expose
    private Integer metal;
    @SerializedName("crossover")
    @Expose
    private Integer crossover;
    @SerializedName("dancehall")
    @Expose
    private Integer dancehall;

    public Integer getRock() {
        return rock;
    }

    public void setRock(Integer rock) {
        this.rock = rock;
    }

    public Integer getPunk() {
        return punk;
    }

    public void setPunk(Integer punk) {
        this.punk = punk;
    }

    public Integer getNewage() {
        return newage;
    }

    public void setNewage(Integer newage) {
        this.newage = newage;
    }

    public Integer getVallenato() {
        return vallenato;
    }

    public void setVallenato(Integer vallenato) {
        this.vallenato = vallenato;
    }

    public Integer getSalsa() {
        return salsa;
    }

    public void setSalsa(Integer salsa) {
        this.salsa = salsa;
    }

    public Integer getAnos80() {
        return anos80;
    }

    public void setAnos80(Integer anos80) {
        this.anos80 = anos80;
    }

    public Integer getMerengue() {
        return merengue;
    }

    public void setMerengue(Integer merengue) {
        this.merengue = merengue;
    }

    public Integer getRegueton() {
        return regueton;
    }

    public void setRegueton(Integer regueton) {
        this.regueton = regueton;
    }

    public Integer getMetal() {
        return metal;
    }

    public void setMetal(Integer metal) {
        this.metal = metal;
    }

    public Integer getCrossover() {
        return crossover;
    }

    public void setCrossover(Integer crossover) {
        this.crossover = crossover;
    }

    public Integer getDancehall() {
        return dancehall;
    }

    public void setDancehall(Integer dancehall) {
        this.dancehall = dancehall;
    }

}
