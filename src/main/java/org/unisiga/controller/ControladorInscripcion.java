package org.unisiga.controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.unisiga.view.VistaInscripcion;
import org.unisiga.view.VistaMenu;

public class ControladorInscripcion {
    private VistaInscripcion vista;
    private JFrame ventana;
    private VistaMenu vistaMenu;
    private InscripcionController controlador;

    public ControladorInscripcion(VistaInscripcion vista, VistaMenu vistaMenu, InscripcionController controlador) {
        this.vista = vista;
        this.vistaMenu = vistaMenu;
        this.controlador = controlador;
        this.ventana = new JFrame("UNISIGA - Inscripción de Asignatura");
        this.ventana.setContentPane(vista);
        this.ventana.pack();
        this.vista.btnInscribir.addActionListener(e -> inscribir());
        this.vista.btnVolver.addActionListener(e -> volver());
    }

    public void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void inscribir() {
        String matricula = vista.txtMatricula.getText().trim();
        String codigo = vista.txtCodigoAsignatura.getText().trim();
        String grupoStr = vista.txtGrupo.getText().trim();

        if (matricula.isEmpty() || codigo.isEmpty() || grupoStr.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Error: Todos los campos son obligatorios.");
            return;
        }

        char grupo = grupoStr.charAt(0);
        String resultado = controlador.inscribirSeccionEstudiante(matricula, codigo, grupo);
        JOptionPane.showMessageDialog(ventana, resultado);

        if (!resultado.startsWith("Error")) {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        vista.txtMatricula.setText("");
        vista.txtCodigoAsignatura.setText("");
        vista.txtGrupo.setText("");
    }

    private void volver() {
        ventana.setVisible(false);
        vistaMenu.setVisible(true);
    }
}
