/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelorelatorio.connectBD;

/**
 *
 * @author casa
 */
public class VentanaPrincipal extends JFrame implements ActionListener {

    BorderLayout bl;
    JPanel este;
    JPanel oeste;
    JPanel norte;
    JPanel sur;
    JPanel centro;

    JTable tabla;
    DefaultTableModel modelo;

    public VentanaPrincipal() {

        super();

        try {
            //---------------definicion de las propiedades de la ventana--------

            //--------------------conexion a la base de datos----------------
            connectBD con = new connectBD();
            try {
                con.EstablecerConexion();
            } catch (SQLException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            //--------------------definicion del layout--------------------
            BorderLayout bl = new BorderLayout();
            setLayout(bl);

            //>>>>>>>>>>>Panel central
            String[] nombresColumnas = new String[]{"Id", "Año", "Mes", "Suma Despesas", "Suma Receita", "Saldo Mensual", "Saldo Anterior", "Saldo Actual"};
            modelo = new DefaultTableModel(con.getResumen(), nombresColumnas);
            tabla = new JTable(modelo);
            //tabla.setPreferredSize(new Dimension(10000, 10000));
            //tabla.setPreferredSize(new Dimension(10000, 10000));
            tabla.setPreferredScrollableViewportSize(new Dimension(1000, 1000));
            //JPanel panelT = new JPanel();
            //panelT.setPreferredSize(new Dimension(500, 500));
            //JScrollPane jp1 = new JScrollPane();
            // jp1.setViewportView(tabla);
            //panelT.add(jp1);

            JScrollPane s = new JScrollPane(tabla);
            s.setPreferredSize(new Dimension(500, 500));
            //<<<<<<<<<<<<<Panel central

            // s.setHorizontalScrollBar(b);
            // s.set
            //JScrollPane jp = new JScrollPane();
            // jp.setViewportView(panelT);
            // jp.setWheelScrollingEnabled(true);
            //this.add(sur, BorderLayout.SOUTH);
            //this.add(oeste, BorderLayout.WEST);
            //this.add(centro, BorderLayout.CENTER);
            JMenuBar mb = new JMenuBar(); //barra

            JMenu m1 = new JMenu("Archivo"); //menu
            JMenu m2 = new JMenu("Edición");
            JMenu m3 = new JMenu("Informe");

            JMenuItem mi1 = new JMenuItem("Crear"); //item
            JMenuItem mi2 = new JMenuItem("Guardar");
            JMenuItem mi3 = new JMenuItem("Eliminar");

            JMenuItem mi4 = new JMenuItem("Insertar Receita");
            JMenuItem mi5 = new JMenuItem("Insertar Despesa");
            JMenuItem mi6 = new JMenuItem("Modificar Receita");
            JMenuItem mi7 = new JMenuItem("Modificar Despesa");
            JMenuItem mi8 = new JMenuItem("Eliminar Receita");
            JMenuItem mi9 = new JMenuItem("Eliminar Despesa");
            JMenuItem mi10 = new JMenuItem("Eliminar Mes");

            JMenuItem mi11 = new JMenuItem("Informe Despesa");
            JMenuItem mi12 = new JMenuItem("Informe Receita");
            JMenuItem mi13 = new JMenuItem("Informe Mes");
            JMenuItem mi14 = new JMenuItem("Informe Año");

            m1.add(mi1);
            m1.add(mi2);
            m1.add(mi3);

            m2.add(mi4);
            m2.add(mi5);
            m2.addSeparator();
            m2.add(mi6);
            m2.add(mi7);
            m2.addSeparator();
            m2.add(mi8);
            m2.add(mi9);
            m2.add(mi10);

            m3.add(mi11);
            m3.add(mi12);
            m3.add(mi13);
            m3.add(mi14);

            mb.add(m1);
            mb.add(m2);
            mb.add(m3);

            add("North", mb);
            add("Center", s);

            //eventos
            mi4.addActionListener(this);
            mi5.addActionListener(this);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            pack();
            setLocationRelativeTo(getParent());
            setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("" + e.getActionCommand());
        if (e.getActionCommand() == ("Insertar Receita")) {
            VentanaReceita vr = new VentanaReceita();
            vr.show();
        } else if (e.getActionCommand() == ("Insertar Despesa")) {
            VentanaDespesa vd = new VentanaDespesa();
            vd.show();
        }
    }

}
