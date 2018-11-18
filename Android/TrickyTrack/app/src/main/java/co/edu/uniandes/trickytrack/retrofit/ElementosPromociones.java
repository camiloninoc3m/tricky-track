package co.edu.uniandes.trickytrack.retrofit;

import java.io.Serializable;
import java.util.List;

public class ElementosPromociones implements Serializable {
    public List<ExamplePromocion> getElementosPromocion() {
        return elementos;
    }

    public void setElementos(List<ExamplePromocion> elementos) {
        this.elementos = elementos;
    }

    List<ExamplePromocion> elementos;
}
