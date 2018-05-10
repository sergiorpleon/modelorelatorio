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
public class Receitas {
    private int id;
    private String numero;
    private String coordenada;
    private String categoria;
    private int cantidad;
    private double valor;
    private double total;
    private int idresumen;

    public Receitas() {
    }

    public Receitas(int id, String numero, String categoria, int cantidad, double valor, double total, int idresumen) {
        this.id = id;
        this.numero = numero;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.valor = valor;
        this.total = total;
        this.idresumen = idresumen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdresumen() {
        return idresumen;
    }

    public void setIdresumen(int idresumen) {
        this.idresumen = idresumen;
    }
    
    
}
