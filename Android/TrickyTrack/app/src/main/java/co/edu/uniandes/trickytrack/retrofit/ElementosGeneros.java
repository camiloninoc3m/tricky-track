package co.edu.uniandes.trickytrack.retrofit;

import java.io.Serializable;
import java.util.List;

public class ElementosGeneros implements Serializable {
    public List<ExampleGeneros> getElementosGeneros() {
        return elementos;
    }

    public void setElementosGeneros(List<ExampleGeneros> elementos) {
        this.elementos = elementos;
    }

    List<ExampleGeneros> elementos;
}
