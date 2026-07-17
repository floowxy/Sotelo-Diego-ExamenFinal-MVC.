package org.unisiga.main;

import org.unisiga.controller.ControladorMenu;
import org.unisiga.controller.InscripcionController;
import org.unisiga.view.VistaMenu;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            InscripcionController controlador = new InscripcionController();
            ControladorMenu menu = new ControladorMenu(new VistaMenu(), controlador);
            menu.iniciar();
        });
    }
}
