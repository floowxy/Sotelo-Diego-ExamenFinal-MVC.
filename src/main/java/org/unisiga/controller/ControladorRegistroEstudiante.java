package org.unisiga.controller;

import javax.swing.JOptionPane;
import org.unisiga.view.VistaMenu;
import org.unisiga.view.VistaRegistroEstudiante;

public class ControladorRegistroEstudiante {
    private VistaRegistroEstudiante vista;
    private VistaMenu vistaMenu;
    private InscripcionController controlador;

    public ControladorRegistroEstudiante(VistaRegistroEstudiante vista, VistaMenu vistaMenu, InscripcionController controlador) {
        this.vista = vista;
        this.vistaMenu = vistaMenu;
        this.controlador = controlador;
        this.vista.btnRegistrar.addActionListener(e -> registrar());
        this.vista.btnVolver.addActionListener(e -> volver());
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void registrar() {
        try {
            int anio = Integer.parseInt(vista.txtAnioIngreso.getText().trim());
            String mensaje = controlador.registrarEstudiante(
                    vista.txtRut.getText().trim(),
                    vista.txtNombre.getText().trim(),
                    vista.txtCorreo.getText().trim(),
                    vista.txtMatricula.getText().trim(),
                    anio);
            JOptionPane.showMessageDialog(vista, mensaje);
            if (!mensaje.startsWith("Error")) {
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El anio de ingreso debe ser un numero.");
        }
    }

    private void limpiarCampos() {
        vista.txtRut.setText("");
        vista.txtNombre.setText("");
        vista.txtCorreo.setText("");
        vista.txtMatricula.setText("");
        vista.txtAnioIngreso.setText("");
    }

    private void volver() {
        vista.setVisible(false);
        vistaMenu.setVisible(true);
    }
}
