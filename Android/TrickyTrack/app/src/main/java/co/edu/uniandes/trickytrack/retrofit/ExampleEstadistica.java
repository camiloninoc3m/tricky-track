
package co.edu.uniandes.trickytrack.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleEstadistica {

    @SerializedName("clientes")
    @Expose
    private Clientes clientes;
    @SerializedName("edades")
    @Expose
    private Edades edades;
    /*@SerializedName("generos")
    @Expose
    private Generos generos;*/
    @SerializedName("generos")
    @Expose
    private List<Object> generos = null;

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Edades getEdades() {
        return edades;
    }

    public void setEdades(Edades edades) {
        this.edades = edades;
    }

   /* public Generos getGeneros() {
        return generos;
    }

    public void setGeneros(Generos generos) {
        this.generos = generos;
    }
*/
   public List<Object> getGeneros() {
       return generos;
   }

    public void setGeneros(List<Object> generos) {
        this.generos = generos;
    }
}
