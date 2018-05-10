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
public class Objsubgrupo {
    private String subgrupo;
    private List categorias = new ArrayList();

    public Objsubgrupo() {
        
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(String subgrupo) {
        this.subgrupo = subgrupo;
    }

    public List getCategorias() {
        return categorias;
    }

    public void setCategorias(List categorias) {
        this.categorias = categorias;
    }
   
    public void addCategorias(Objcategoria r) {
        this.categorias.add(r);
    }
    
    public JRDataSource getCategoriar() {
        return new JRBeanCollectionDataSource(categorias);
    }
}
