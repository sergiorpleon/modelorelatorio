/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.util.ArrayList;
import java.util.List;
import modelorelatorio.Receitas;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author casa
 */
public class Mes {
    private String rec;
    private String des;
    
    private String uno;
    private String dos;
    private String tres;
    private String cuatro;
    private String cinco;
    
    private String dis;
    private String ant;
    private String act;
    private List resumens = new ArrayList();
    private List receitas = new ArrayList();
    private List despesas = new ArrayList();

    public Mes() {
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getTres() {
        return tres;
    }

    public void setTres(String tres) {
        this.tres = tres;
    }

    public String getCuatro() {
        return cuatro;
    }

    public void setCuatro(String cuatro) {
        this.cuatro = cuatro;
    }

    public String getCinco() {
        return cinco;
    }

    public void setCinco(String cinco) {
        this.cinco = cinco;
    }

    
    
    
    
    
    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getAnt() {
        return ant;
    }

    public void setAnt(String ant) {
        this.ant = ant;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
    
    
    
    

    public List getReceitas() {
        return receitas;
    }

    public void setReceitas(List receitas) {
        this.receitas = receitas;
    }

    public void addMateria(ObjReceita r) {
        this.receitas.add(r);
    }

    public JRDataSource getReceita() {
        return new JRBeanCollectionDataSource(receitas);
    }
    
    
    
    public List getDespesas() {
        return despesas;
    }
    
    public void setDespesas(List despesas) {
        this.despesas = despesas;
    }

    public void addDespesa(ObjDespesa r) {
        this.despesas.add(r);
    }

    public JRDataSource getDespesa() {
        return new JRBeanCollectionDataSource(despesas);
    }
    
    
    
    
    public List getResumens() {
        return resumens;
    }
    
    public void setResumens(List resumen) {
        this.resumens = resumen;
    }

    public void addResumen(ObjResumen r) {
        this.resumens.add(r);
    }

    public JRDataSource getResumen() {
        return new JRBeanCollectionDataSource(resumens);
    }

}
