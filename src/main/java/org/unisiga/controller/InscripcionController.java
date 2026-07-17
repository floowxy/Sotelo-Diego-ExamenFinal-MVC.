package org.unisiga.controller;

import java.util.List;
import org.unisiga.model.*;
import org.unisiga.persistencia.ArchivoAsignatura;
import org.unisiga.persistencia.ArchivoEstudiante;

public class InscripcionController {
    private List<Estudiante> estudiantesDb;
    private List<Asignatura> asignaturasDb;
    private ArchivoEstudiante archivoEstudiante;
    private ArchivoAsignatura archivoAsignatura;

    public InscripcionController() {
        this.archivoEstudiante = new ArchivoEstudiante();
        this.archivoAsignatura = new ArchivoAsignatura();
        this.estudiantesDb = archivoEstudiante.cargarEstudiantes();
        this.asignaturasDb = archivoAsignatura.cargarAsignaturas();
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
        boolean okEstudiantes = archivoEstudiante.guardarEstudiantes(estudiantesDb);
        boolean okAsignaturas = archivoAsignatura.guardarAsignaturas(asignaturasDb);
        return okEstudiantes && okAsignaturas;
    }

    public String registrarEstudiante(String rut, String nombre, String correo, String matricula, int anioIngreso) {
        try {
            if (rut.isEmpty() || nombre.isEmpty() || correo.isEmpty() || matricula.isEmpty()) {
                return "Error: todos los campos son obligatorios.";
            }
            if (buscarEstudiante(matricula) != null) {
                return "Error: ya existe un estudiante con matricula " + matricula + ".";
            }
            Estudiante estudiante = new Estudiante(rut, nombre, correo, matricula, anioIngreso, 0.0f);
            estudiantesDb.add(estudiante);
            guardarDatos();
            return "Estudiante " + nombre + " registrado correctamente.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String registrarAsignatura(String codigo, String nombre, int creditos) {
        try {
            if (codigo.isEmpty() || nombre.isEmpty()) {
                return "Error: todos los campos son obligatorios.";
            }
            if (buscarAsignatura(codigo) != null) {
                return "Error: ya existe la asignatura " + codigo + ".";
            }
            Asignatura asignatura = new Asignatura(codigo, nombre, creditos);
            asignaturasDb.add(asignatura);
            guardarDatos();
            return "Asignatura " + nombre + " registrada correctamente.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String crearSeccionEnAsignatura(String codigoAsignatura, char idGrupo, int cupoMaximo, String horario) {
        try {
            Asignatura asignatura = buscarAsignatura(codigoAsignatura);
            if (asignatura == null) {
                return "Error: no existe la asignatura " + codigoAsignatura + ".";
            }
            asignatura.crearSeccion(idGrupo, cupoMaximo, horario);
            guardarDatos();
            return "Seccion " + idGrupo + " creada en " + asignatura.getNombre() + ".";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String agregarPrerrequisito(String codigoAsignatura, String codigoPrerrequisito) {
        try {
            Asignatura asignatura = buscarAsignatura(codigoAsignatura);
            Asignatura prerrequisito = buscarAsignatura(codigoPrerrequisito);
            if (asignatura == null || prerrequisito == null) {
                return "Error: asignatura o prerrequisito no existe.";
            }
            asignatura.agregarPrerrequisito(prerrequisito);
            guardarDatos();
            return "Prerrequisito " + prerrequisito.getNombre() + " agregado a " + asignatura.getNombre() + ".";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
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
