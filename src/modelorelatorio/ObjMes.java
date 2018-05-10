/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelorelatorio;

/**
 *
 * @author casa
 */
public class ObjMes {
    //private int id;
    //private int Anno;
    private String mes;
    private String receita;
    private String despesa;
    private String diferencia;
    private String anterior;
    private String actual;

    public ObjMes() {
    }

    public ObjMes(String mes, String receitas, String despesas, String diferencia, String anterior, String actual) {
        this.mes = mes;
        this.receita = receitas;
        this.despesa = despesas;
        this.diferencia = diferencia;
        this.anterior = anterior;
        this.actual = actual;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receitas) {
        this.receita = receitas;
    }

    public String getDespesa() {
        return despesa;
    }

    public void setDespesa(String despesas) {
        this.despesa = despesas;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    

    

    
}
