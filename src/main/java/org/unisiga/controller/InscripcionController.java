package org.unisiga.controller;

import java.util.List;
import org.unisiga.model.*;
import org.unisiga.persistencia.ArchivoUniSiga;
import org.unisiga.persistencia.ContenedorDatos;

public class InscripcionController {
    private List<Estudiante> estudiantesDb;
    private List<Asignatura> asignaturasDb;
    private ArchivoUniSiga archivo;

    public InscripcionController() {
        this.archivo = new ArchivoUniSiga();
        ContenedorDatos datos = archivo.cargar();
        this.estudiantesDb = datos.getEstudiantes();
        this.asignaturasDb = datos.getAsignaturas();
    }

    public void registrarEstudianteEnDb(Estudiante e) {
        if (e == null || buscarEstudiante(e.getMatricula()) != null) {
            return;
        }
        estudiantesDb.add(e);
    }

    public void registrarAsignaturaEnDb(Asignatura a) {
        if (a == null || buscarAsignatura(a.getCodigo()) != null) {
            return;
        }
        asignaturasDb.add(a);
    }

    public String inscribirSeccionEstudiante(String matricula, String codigoAsignatura, char idGrupo) {
        try {
            Estudiante estudiante = buscarEstudiante(matricula);
            if (estudiante == null) {
                return "Error: no existe el estudiante con matricula " + matricula + ".";
            }

            Asignatura asignatura = buscarAsignatura(codigoAsignatura);
            if (asignatura == null) {
                return "Error: no existe la asignatura " + codigoAsignatura + ".";
            }

            Seccion seccion = null;
            for (Seccion s : asignatura.getSecciones()) {
                if (s.getIdGrupo() == idGrupo) {
                    seccion = s;
                    break;
                }
            }
            if (seccion == null) {
                return "Error: la asignatura " + asignatura.getNombre() + " no tiene la seccion " + idGrupo + ".";
            }

            for (Asignatura prer : asignatura.getPrerrequisitos()) {
                if (!tieneAprobada(estudiante, prer)) {
                    return "Error: " + estudiante.getNombre() + " no tiene aprobado el prerrequisito " + prer.getNombre() + ".";
                }
            }

            estudiante.inscribirSeccion(seccion);
            guardarDatos();
            return "Inscripcion exitosa: " + estudiante.getNombre() + " en " + asignatura.getNombre() + " seccion " + idGrupo + ".";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public boolean guardarDatos() {
        return archivo.guardar(estudiantesDb, asignaturasDb);
    }

    public Estudiante buscarEstudiante(String matricula) {
        for (Estudiante e : estudiantesDb) {
            if (e.getMatricula().equals(matricula)) {
                return e;
            }
        }
        return null;
    }

    public Asignatura buscarAsignatura(String codigo) {
        for (Asignatura a : asignaturasDb) {
            if (a.getCodigo().equals(codigo)) {
                return a;
            }
        }
        return null;
    }

    private boolean tieneAprobada(Estudiante estudiante, Asignatura asignatura) {
        for (Inscripcion i : estudiante.getInscripciones()) {
            if (i.getSeccion().getAsignatura().getCodigo().equals(asignatura.getCodigo())
                    && "Aprobado".equals(i.getEstadoInscripcion())) {
                return true;
            }
        }
        return false;
    }

    public List<Estudiante> getEstudiantesDb() {
        return estudiantesDb;
    }

    public List<Asignatura> getAsignaturasDb() {
        return asignaturasDb;
    }
}
