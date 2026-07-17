package org.unisiga.persistencia;

import java.io.Serializable;
import java.util.List;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Estudiante;

public class ContenedorDatos implements Serializable {
    private List<Estudiante> estudiantes;
    private List<Asignatura> asignaturas;

    public ContenedorDatos(List<Estudiante> estudiantes, List<Asignatura> asignaturas) {
        this.estudiantes = estudiantes;
        this.asignaturas = asignaturas;
    }

    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public List<Asignatura> getAsignaturas() { return asignaturas; }
}
