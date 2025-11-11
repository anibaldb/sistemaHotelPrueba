package Clases;

import java.util.ArrayList;
import java.util.List;

public class ClaseGenerica <T>{
    private List<T> elementos;

    public ClaseGenerica() {

        this.elementos = new ArrayList<>();
    }

    public List<T> getElementos() {
        return elementos;
    }

    public void agregar(T objeto) throws Exception {
        if (elementos.contains(objeto)) {
            throw new Exception("El elemento ya existe en la base de datos");
        }

        elementos.add(objeto);
    }

    public void recorrer() {
        for (T obj : elementos) {
            System.out.println(obj.toString());
        }
    }

}
