package org.unisiga.persistencia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.unisiga.librerias.SerializadoraGen;
import org.unisiga.model.Estudiante;

public class ArchivoEstudiante {
    private String ruta;

    public ArchivoEstudiante() {
        this.ruta = "datos/estudiantes.dat";
        crearCarpetaDatos();
    }

    private void crearCarpetaDatos() {
        File carpeta = new File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    public boolean guardarEstudiantes(List<Estudiante> estudiantes) {
        try {
            SerializadoraGen.serializar(ruta, new ArrayList<>(estudiantes));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Estudiante> cargarEstudiantes() {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                return new ArrayList<>();
            }
            Object obj = SerializadoraGen.deserializar(ruta);
            if (obj instanceof List) {
                return (List<Estudiante>) obj;
            }
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }
}
