/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelorelatorio;

import java.sql.Date;



/**
 *
 * @author casa
 */
public class Despesas {

    private int id;
    private String numero;
    private String numeroDocumento;
    private String coordenada;
    private String fecha;
    
    private String categoria;
    private String subcategoria;
    private String descripcion;
    private double valor;
    private int idresumen;

    public Despesas() {
    }

    public Despesas(int id, String numero, String numeroDocumento, String fecha, String categoria, String subcategoria, String descripcion, double valor, int idresumen) {
        this.id = id;
        this.numero = numero;
        this.numeroDocumento = numeroDocumento;
        this.fecha = fecha;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.descripcion = descripcion;
        this.valor = valor;
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

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }
  
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdresumen() {
        return idresumen;
    }

    public void setIdresumen(int idresumen) {
        this.idresumen = idresumen;
    }

}
