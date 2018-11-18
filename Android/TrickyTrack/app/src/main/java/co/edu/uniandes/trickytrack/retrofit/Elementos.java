package co.edu.uniandes.trickytrack.retrofit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Elementos  implements Serializable {
    public List<Example> getElementos() {
        return elementos;
    }

    public void setElementos(List<Example> elementos) {
        this.elementos = elementos;
    }

    List<Example> elementos;
}
