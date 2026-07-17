package org.unisiga.controller;

import javax.swing.JOptionPane;
import org.unisiga.view.VistaMenu;
import org.unisiga.view.VistaRegistroAsignatura;
import org.unisiga.view.VistaRegistroEstudiante;
import org.unisiga.view.VistaInscripcion;

public class ControladorMenu {
    private VistaMenu vista;
    private InscripcionController controlador;

    public ControladorMenu(VistaMenu vista, InscripcionController controlador) {
        this.vista = vista;
        this.controlador = controlador;
        this.vista.btnRegistrarEstudiante.addActionListener(e -> abrirRegistroEstudiante());
        this.vista.btnRegistrarAsignatura.addActionListener(e -> abrirRegistroAsignatura());
        this.vista.btnInscripcion.addActionListener(e -> abrirInscripcion());
        this.vista.btnSalir.addActionListener(e -> salir());
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void abrirRegistroEstudiante() {
        vista.setVisible(false);
        new ControladorRegistroEstudiante(new VistaRegistroEstudiante(), vista, controlador).iniciar();
    }

    private void abrirRegistroAsignatura() {
        vista.setVisible(false);
        new ControladorRegistroAsignatura(new VistaRegistroAsignatura(), vista, controlador).iniciar();
    }

    private void abrirInscripcion() {
        vista.setVisible(false);
        new ControladorInscripcion(new VistaInscripcion(), vista, controlador).iniciar();
    }

    private void salir() {
        controlador.guardarDatos();
        System.exit(0);
    }
}
