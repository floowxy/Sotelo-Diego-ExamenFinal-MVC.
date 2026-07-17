package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al profesor encargado de dictar cátedras.
 */
public class Academico extends MiembroUniversitario {
    private String idEmpleado;
    private String tipoContrato;
    private Departamento departamento; // Relación de agregación
    private List<Seccion> seccionesDictadas;

    public Academico(String rut, String nombre, String correo, String idEmpleado, String tipoContrato) {
        super(rut, nombre, correo);
        this.idEmpleado = idEmpleado;
        this.tipoContrato = tipoContrato;
        this.seccionesDictadas = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        if (password == null) {
            return false;
        }
        return password.contains("@");
    }

    /**
     * Registra la nota de un estudiante para una evaluación de la asignatura.
     * [REGLAS]: Validar parámetros, rango de notas [1.0, 7.0] y que la evaluación pertenezca a la asignatura.
     */
    public void registrarNota(Inscripcion inscripcion, Evaluacion evaluacion, float valorNota) {
        if (inscripcion == null || evaluacion == null) {
            throw new IllegalArgumentException("La inscripcion y la evaluacion no pueden ser nulas.");
        }
        if (valorNota < 1.0f || valorNota > 7.0f) {
            throw new IllegalArgumentException("La nota debe estar entre 1.0 y 7.0.");
        }
        if (evaluacion.getAsignatura() != inscripcion.getSeccion().getAsignatura()) {
            throw new IllegalArgumentException("La evaluacion no pertenece a la asignatura de la seccion.");
        }
        for (Calificacion c : inscripcion.getCalificaciones()) {
            if (c.getEvaluacion() == evaluacion) {
                c.setNota(valorNota);
                return;
            }
        }
        Calificacion calificacion = new Calificacion(valorNota, inscripcion, evaluacion);
        inscripcion.getCalificaciones().add(calificacion);
        evaluacion.getCalificaciones().add(calificacion);
    }

    // Getters y Setters
    public String getIdEmpleado() { return idEmpleado; }
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento depto) { this.departamento = depto; }
    public List<Seccion> getSeccionesDictadas() { return seccionesDictadas; }
}
