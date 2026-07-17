[1mdiff --git a/src/main/java/org/unisiga/controller/InscripcionController.java b/src/main/java/org/unisiga/controller/InscripcionController.java[m
[1mindex a852f60..94f915c 100644[m
[1m--- a/src/main/java/org/unisiga/controller/InscripcionController.java[m
[1m+++ b/src/main/java/org/unisiga/controller/InscripcionController.java[m
[36m@@ -1,35 +1,111 @@[m
[31m-package org.unisiga.controller;[m
[31m-[m
[31m-import java.util.ArrayList;[m
[31m-import java.util.List;[m
[31m-import org.unisiga.model.*;[m
[31m-[m
[31m-/**[m
[31m- * Controlador de lógica de negocio transaccional. Simula llamadas e interacciones de base de datos.[m
[31m- */[m
[31m-public class InscripcionController {[m
[31m-    private List<Estudiante> estudiantesDb;[m
[31m-    private List<Asignatura> asignaturasDb;[m
[31m-[m
[31m-    public InscripcionController() {[m
[31m-        this.estudiantesDb = new ArrayList<>();[m
[31m-        this.asignaturasDb = new ArrayList<>();[m
[31m-    }[m
[31m-[m
[31m-    // Métodos de sembrado (seeding) de bases de datos[m
[31m-    public void registrarEstudianteEnDb(Estudiante e) { estudiantesDb.add(e); }[m
[31m-    public void registrarAsignaturaEnDb(Asignatura a) { asignaturasDb.add(a); }[m
[31m-[m
[31m-    /**[m
[31m-     * Procesa la solicitud de inscripción de asignaturas.[m
[31m-     * [LÓGICA]: [m
[31m-     * 1. Buscar estudiante y asignatura.[m
[31m-     * 2. Obtener el grupo solicitado por composición.[m
[31m-     * 3. VALIDAR PRERREQUISITOS (Auto-asociación): El alumno debe tener aprobado el prerrequisito en su historial.[m
[31m-     * 4. Delegar la transacción al dominio del modelo.[m
[31m-     */[m
[31m-    public String inscribirSeccionEstudiante(String matricula, String codigoAsignatura, char idGrupo) {[m
[31m-        // TODO: Implementar la orquestación completa de la inscripción según la regla de negocio[m
[31m-        throw new UnsupportedOperationException("El controlador de inscripción no está implementado.");[m
[31m-    }[m
[31m-}[m
[32m+[m[32mpackage org.unisiga.controller;[m
[32m+[m
[32m+[m[32mimport java.util.List;[m
[32m+[m[32mimport org.unisiga.model.*;[m
[32m+[m[32mimport org.unisiga.persistencia.ArchivoUniSiga;[m
[32m+[m[32mimport org.unisiga.persistencia.ContenedorDatos;[m
[32m+[m
[32m+[m[32mpublic class InscripcionController {[m
[32m+[m[32m    private List<Estudiante> estudiantesDb;[m
[32m+[m[32m    private List<Asignatura> asignaturasDb;[m
[32m+[m[32m    private ArchivoUniSiga archivo;[m
[32m+[m
[32m+[m[32m    public InscripcionController() {[m
[32m+[m[32m        this.archivo = new ArchivoUniSiga();[m
[32m+[m[32m        ContenedorDatos datos = archivo.cargar();[m
[32m+[m[32m        this.estudiantesDb = datos.getEstudiantes();[m
[32m+[m[32m        this.asignaturasDb = datos.getAsignaturas();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void registrarEstudianteEnDb(Estudiante e) {[m
[32m+[m[32m        if (e == null || buscarEstudiante(e.getMatricula()) != null) {[m
[32m+[m[32m            return;[m
[32m+[m[32m        }[m
[32m+[m[32m        estudiantesDb.add(e);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void registrarAsignaturaEnDb(Asignatura a) {[m
[32m+[m[32m        if (a == null || buscarAsignatura(a.getCodigo()) != null) {[m
[32m+[m[32m            return;[m
[32m+[m[32m        }[m
[32m+[m[32m        asignaturasDb.add(a);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String inscribirSeccionEstudiante(String matricula, String codigoAsignatura, char idGrupo) {[m
[32m+[m[32m        try {[m
[32m+[m[32m            Estudiante estudiante = buscarEstudiante(matricula);[m
[32m+[m[32m            if (estudiante == null) {[m
[32m+[m[32m                return "Error: no existe el estudiante con matricula " + matricula + ".";[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            Asignatura asignatura = buscarAsignatura(codigoAsignatura);[m
[32m+[m[32m            if (asignatura == null) {[m
[32m+[m[32m                return "Error: no existe la asignatura " + codigoAsignatura + ".";[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            Seccion seccion = null;[m
[32m+[m[32m            for (Seccion s : asignatura.getSecciones()) {[m
[32m+[m[32m                if (s.getIdGrupo() == idGrupo) {[m
[32m+[m[32m                    seccion = s;[m
[32m+[m[32m                    break;[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m            if (seccion == null) {[m
[32m+[m[32m                return "Error: la asignatura " + asignatura.getNombre() + " no tiene la seccion " + idGrupo + ".";[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            for (Asignatura prer : asignatura.getPrerrequisitos()) {[m
[32m+[m[32m                if (!tieneAprobada(estudiante, prer)) {[m
[32m+[m[32m                    return "Error: " + estudiante.getNombre() + " no tiene aprobado el prerrequisito " + prer.getNombre() + ".";[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            estudiante.inscribirSeccion(seccion);[m
[32m+[m[32m            guardarDatos();[m
[32m+[m[32m            return "Inscripcion exitosa: " + estudiante.getNombre() + " en " + asignatura.getNombre() + " seccion " + idGrupo + ".";[m
[32m+[m
[32m+[m[32m        } catch (Exception e) {[m
[32m+[m[32m            return "Error: " + e.getMessage();[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public boolean guardarDatos() {[m
[32m+[m[32m        return archivo.guardar(estudiantesDb, asignaturasDb);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public Estudiante buscarEstudiante(String matricula) {[m
[32m+[m[32m        for (Estudiante e : estudiantesDb) {[m
[32m+[m[32m            if (e.getMatricula().equals(matricula)) {[m
[32m+[m[32m                return e;[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        return null;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public Asignatura buscarAsignatura(String codigo) {[m
[32m+[m[32m        for (Asignatura a : asignaturasDb) {[m
[32m+[m[32m            if (a.getCodigo().equals(codigo)) {[m
[32m+[m[32m                return a;[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        return null;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private boolean tieneAprobada(Estudiante estudiante, Asignatura asignatura) {[m
[32m+[m[32m        for (Inscripcion i : estudiante.getInscripciones()) {[m
[32m+[m[32m            if (i.getSeccion().getAsignatura().getCodigo().equals(asignatura.getCodigo())[m
[32m+[m[32m                    && "Aprobado".equals(i.getEstadoInscripcion())) {[m
[32m+[m[32m                return true;[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        return false;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public List<Estudiante> getEstudiantesDb() {[m
[32m+[m[32m        return estudiantesDb;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public List<Asignatura> getAsignaturasDb() {[m
[32m+[m[32m        return asignaturasDb;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/java/org/unisiga/model/Academico.java b/src/main/java/org/unisiga/model/Academico.java[m
[1mindex 46b686c..aa96336 100644[m
[1m--- a/src/main/java/org/unisiga/model/Academico.java[m
[1m+++ b/src/main/java/org/unisiga/model/Academico.java[m
[36m@@ -32,8 +32,24 @@[m [mpublic class Academico extends MiembroUniversitario {[m
      * [REGLAS]: Validar parámetros, rango de notas [1.0, 7.0] y que la evaluación pertenezca a la asignatura.[m
      */[m
     public void registrarNota(Inscripcion inscripcion, Evaluacion evaluacion, float valorNota) {[m
[31m-        // TODO: Implementar la validación e inserción/actualización de la nota (Tres Vías)[m
[31m-        throw new UnsupportedOperationException("Método registrarNota() no implementado aún.");[m
[32m+[m[32m        if (inscripcion == null || evaluacion == null) {[m[41m[m
[32m+[m[32m            throw new IllegalArgumentException("La inscripcion y la evaluacion no pueden ser nulas.");[m[41m[m
[32m+[m[32m        }[m[41m[m
[32m+[m[32m        if (valorNota < 1.0f || valorNota > 7.0f) {[m[41m[m
[32m+[m[32m            throw new IllegalArgumentException("La nota debe estar entre 1.0 y 7.0.");[m[41m[m
[32m+[m[32m        }[m[41m[m
[32m+[m[32m        if (evaluacion.getAsignatura() != inscripcion.getSeccion().getAsignatura()) {[m[41m[m
[32m+[m[32m            throw new IllegalArgumentException("La evaluacion no pertenece a la asignatura de la seccion.");[m[41m[m
[32m+[m[32m        }[m[41m[m
[32m+[m[32m        for (Calificacion c : inscripcion.getCalificaciones()) {[m[41m[m
[32m+[m[32m            if (c.getEvaluacion() == evaluacion) {[m[41m[m
[32m+[m[32m                c.setNota(valorNota);[m[41m[m
[32m+[m[32m                return;[m[41m[m
[32m+[m[32m            }[m[41m[m
[32m+[m[32m        }[m[41m[m
[32m+[m[32m        Calificacion calificacion = new Calificacion(valorNota, inscripcion, evaluacion);[m[41m[m
[32m+[m[32m        inscripcion.getCalificaciones().add(calificacion);[m[41m[m
[32m+[m[32m        evaluacion.getCalificaciones().add(calificacion);[m[41m[m
     }[m
 [m
     // Getters y Setters[m
