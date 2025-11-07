package Clases;

import java.util.ArrayList;

public class ClaseGenerica <T>{
    private ArrayList <T> lista = new ArrayList<>();


    public void Agregar(T objeto) {
        lista.add(objeto);
    }

    public void Recorrer(T objeto) {
        for (T obj : lista) {
            System.out.println(obj.toString());
        }
    }
}
