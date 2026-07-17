package org.unisiga.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class VistaInscripcion extends JPanel {
    public JTextField txtMatricula;
    public JTextField txtCodigoAsignatura;
    public JTextField txtGrupo;
    public JButton btnInscribir;
    public JButton btnVolver;

    public VistaInscripcion() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("INCRIPCION DE ASIGNATURA");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Matricula
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Matrícula Estudiante:"), gbc);

        txtMatricula = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtMatricula, gbc);

        // Codigo Asignatura
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Código Asignatura:"), gbc);

        txtCodigoAsignatura = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtCodigoAsignatura, gbc);

        // Grupo
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Grupo (Letra):"), gbc);

        txtGrupo = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(txtGrupo, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        btnInscribir = new JButton("Inscribir");
        btnVolver = new JButton("Volver");
        panelBotones.add(btnInscribir);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelBotones, gbc);
    }
}
