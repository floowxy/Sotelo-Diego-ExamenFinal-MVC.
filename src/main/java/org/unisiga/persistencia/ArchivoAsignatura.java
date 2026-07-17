package org.unisiga.persistencia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.unisiga.librerias.SerializadoraGen;
import org.unisiga.model.Asignatura;

public class ArchivoAsignatura {
    private String ruta;

    public ArchivoAsignatura() {
        this.ruta = "datos/asignaturas.dat";
        crearCarpetaDatos();
    }

    private void crearCarpetaDatos() {
        File carpeta = new File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    public boolean guardarAsignaturas(List<Asignatura> asignaturas) {
        try {
            SerializadoraGen.serializar(ruta, new ArrayList<>(asignaturas));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Asignatura> cargarAsignaturas() {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                return new ArrayList<>();
            }
            Object obj = SerializadoraGen.deserializar(ruta);
            if (obj instanceof List) {
                return (List<Asignatura>) obj;
            }
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }
}
