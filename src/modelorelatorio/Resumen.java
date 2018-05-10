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
public class Resumen {
    private int id;
    private int Anno;
    private int Mes;
    private double sumadespesas;
    private double sumareceitas;
    private double saldomensual;
    private double saldoanterior;
    private double saldoactual;

    public Resumen() {
    }

    public Resumen(int id, int Anno, int Mes, double sumadespesas, double sumareceitas, double saldomensual, double saldoanterior, double saldoactual) {
        this.id = id;
        this.Anno = Anno;
        this.Mes = Mes;
        this.sumadespesas = sumadespesas;
        this.sumareceitas = sumareceitas;
        this.saldomensual = saldomensual;
        this.saldoanterior = saldoanterior;
        this.saldoactual = saldoactual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnno() {
        return Anno;
    }

    public void setAnno(int Anno) {
        this.Anno = Anno;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int Mes) {
        this.Mes = Mes;
    }

    public double getSumadespesas() {
        return sumadespesas;
    }

    public void setSumadespesas(double sumadespesas) {
        this.sumadespesas = sumadespesas;
    }

    public double getSumareceitas() {
        return sumareceitas;
    }

    public void setSumareceitas(double sumareceitas) {
        this.sumareceitas = sumareceitas;
    }

    public double getSaldomensual() {
        return saldomensual;
    }

    public void setSaldomensual(double saldomensual) {
        this.saldomensual = saldomensual;
    }

    public double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    public double getSaldoactual() {
        return saldoactual;
    }

    public void setSaldoactual(double saldoactual) {
        this.saldoactual = saldoactual;
    }
    
    
}
