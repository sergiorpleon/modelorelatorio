/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sergio
 */
public class Objgrupo {
    private String grupo;
    private List subgrupo = new ArrayList();

    public Objgrupo() {
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(List subgrupo) {
        this.subgrupo = subgrupo;
    }
    
    public void addSubgrupo(Objsubgrupo r) {
        this.subgrupo.add(r);
    }
    
    public JRDataSource getSubgruposr() {
        return new JRBeanCollectionDataSource(subgrupo);
    }
    
}
