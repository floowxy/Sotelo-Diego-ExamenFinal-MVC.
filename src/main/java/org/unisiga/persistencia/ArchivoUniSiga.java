package org.unisiga.persistencia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.unisiga.librerias.SerializadoraGen;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Estudiante;

public class ArchivoUniSiga {
    private String ruta;

    public ArchivoUniSiga() {
        this.ruta = "datos/unisiga.dat";
        crearCarpetaDatos();
    }

    private void crearCarpetaDatos() {
        File carpeta = new File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    public boolean guardar(List<Estudiante> estudiantes, List<Asignatura> asignaturas) {
        try {
            ContenedorDatos contenedor = new ContenedorDatos(estudiantes, asignaturas);
            SerializadoraGen.serializar(ruta, contenedor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ContenedorDatos cargar() {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                return new ContenedorDatos(new ArrayList<>(), new ArrayList<>());
            }
            Object obj = SerializadoraGen.deserializar(ruta);
            if (obj instanceof ContenedorDatos) {
                return (ContenedorDatos) obj;
            }
        } catch (Exception e) {
        }
        return new ContenedorDatos(new ArrayList<>(), new ArrayList<>());
    }
}
