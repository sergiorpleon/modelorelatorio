/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author casa
 */
public class VentanaDespesa extends JFrame {

    JPanel superior;
    JLabel lanno;
    JTextField tanno;
    JLabel lmes;
    JComboBox<String> cmes;
    
    JPanel inferior;

    public VentanaDespesa() {
        super();
        
        

        superior = new JPanel();
        inferior = new JPanel();
        Box b = Box.createVerticalBox();
        b.add(superior);
        //b.add(b.createVerticalGlue());
        b.add(inferior);
        this.getContentPane().add(b);
        //panel superior
        GridLayout lsuperior = new GridLayout(1, 4, 10, 20);
        superior.setLayout(lsuperior);
        lanno = new JLabel("Año: ");
        tanno = new JTextField("");
        lmes = new JLabel("Mes: ");
        cmes = new JComboBox<>(new String[]{"Enero", "Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diceimbre"});
        
        superior.add(lanno);
        superior.add(tanno);
        superior.add(lmes);
        superior.add(cmes);
        
        setSize(400, 200);
        setAlwaysOnTop(true);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        pack();
    }
}
