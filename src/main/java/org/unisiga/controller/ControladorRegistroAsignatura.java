package org.unisiga.controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.unisiga.view.VistaMenu;
import org.unisiga.view.VistaRegistroAsignatura;

public class ControladorRegistroAsignatura {
    private VistaRegistroAsignatura vista;
    private JFrame ventana;
    private VistaMenu vistaMenu;
    private InscripcionController controlador;

    public ControladorRegistroAsignatura(VistaRegistroAsignatura vista, VistaMenu vistaMenu, InscripcionController controlador) {
        this.vista = vista;
        this.vistaMenu = vistaMenu;
        this.controlador = controlador;
        this.ventana = new JFrame("UNISIGA - Registro de Asignatura");
        this.ventana.setContentPane(vista);
        this.ventana.pack();
        this.vista.btnRegistrar.addActionListener(e -> registrar());
        this.vista.btnCrearSeccion.addActionListener(e -> crearSeccion());
        this.vista.btnVolver.addActionListener(e -> volver());
    }

    public void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void registrar() {
        try {
            int creditos = Integer.parseInt(vista.txtCreditos.getText().trim());
            String codigo = vista.txtCodigo.getText().trim();
            String mensaje = controlador.registrarAsignatura(codigo, vista.txtNombre.getText().trim(), creditos);
            JOptionPane.showMessageDialog(vista, mensaje);

            String prerrequisito = vista.txtPrerrequisito.getText().trim();
            if (!mensaje.startsWith("Error") && !prerrequisito.isEmpty()) {
                String msjPrer = controlador.agregarPrerrequisito(codigo, prerrequisito);
                JOptionPane.showMessageDialog(vista, msjPrer);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los creditos deben ser un numero.");
        }
    }

    private void crearSeccion() {
        try {
            String grupo = vista.txtGrupo.getText().trim();
            if (grupo.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingresa la letra del grupo.");
                return;
            }
            int cupo = Integer.parseInt(vista.txtCupo.getText().trim());
            String mensaje = controlador.crearSeccionEnAsignatura(
                    vista.txtCodigo.getText().trim(),
                    grupo.charAt(0),
                    cupo,
                    vista.txtHorario.getText().trim());
            JOptionPane.showMessageDialog(vista, mensaje);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El cupo debe ser un numero.");
        }
    }

    private void volver() {
        ventana.setVisible(false);
        vistaMenu.setVisible(true);
    }
}
