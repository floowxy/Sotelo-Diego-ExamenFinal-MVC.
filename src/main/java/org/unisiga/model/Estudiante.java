package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al alumno de la universidad.
 * [EVALUACIÓN]: El estudiante debe implementar el encapsulamiento y el método de inscripción.
 */
public class Estudiante extends MiembroUniversitario {
    private String matricula;
    private int anioIngreso;
    private float promedioPpa;
    private List<Inscripcion> inscripciones;

    public Estudiante(String rut, String nombre, String correo, String matricula, int anioIngreso, float promedioPpa) {
        super(rut, nombre, correo);
        this.matricula = matricula;
        this.anioIngreso = anioIngreso;
        this.promedioPpa = promedioPpa;
        this.inscripciones = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 8;
    }

    /**
     * Realiza el proceso de inscripción en una sección.
     * [REGLAS]: Validar que la sección no sea nula y que cuente con cupos disponibles.
     */
    public void inscribirSeccion(Seccion seccion) {
        if (seccion == null) {
            throw new IllegalArgumentException("La seccion no puede ser nula.");
        }
        if (seccion.getInscripciones().size() >= seccion.getCupoMaximo()) {
            throw new IllegalStateException("La seccion " + seccion.getIdGrupo() + " no tiene cupos disponibles.");
        }
        for (Inscripcion i : this.inscripciones) {
            if (i.getSeccion() == seccion) {
                throw new IllegalStateException("El estudiante ya esta inscrito en esta seccion.");
            }
        }
        Inscripcion inscripcion = new Inscripcion(this, seccion);
        this.inscripciones.add(inscripcion);
        seccion.getInscripciones().add(inscripcion);
    }

    // Getters y Setters
    public String getMatricula() { return matricula; }
    public float getPromedioPpa() { return promedioPpa; }
    public List<Inscripcion> getInscripciones() { return inscripciones; }
}
