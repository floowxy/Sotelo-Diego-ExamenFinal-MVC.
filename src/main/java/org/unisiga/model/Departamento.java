package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un Departamento Académico (ej: Computación).
 * [EVALUACIÓN]: Demostrar la implementación del concepto de agregación (el académico pertenece, pero tiene ciclo independiente).
 */
public class Departamento implements java.io.Serializable {
    private String codigoDepto;
    private String nombre;
    private List<Academico> academicos;

    public Departamento(String codigoDepto, String nombre) {
        this.codigoDepto = codigoDepto;
        this.nombre = nombre;
        this.academicos = new ArrayList<>();
    }

    public void asociarAcademico(Academico acad) {
        if (acad == null) {
            return;
        }
        if (this.academicos.contains(acad)) {
            return;
        }
        this.academicos.add(acad);
        acad.setDepartamento(this);
    }

    public String getCodigoDepto() { return codigoDepto; }
    public String getNombre() { return nombre; }
    public List<Academico> getAcademicos() { return academicos; }
}
