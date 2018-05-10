/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

/**
 *
 * @author casa
 */
public class Config {

    public static String[] indiceResumen;
    public static String[] indiceReceita;
    public static String[] indiceDespesa;

    public static String[] meses = new String[]{"", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    static int obtenerIndiceMes(String mesomodelo) {
        int res = 0;
        while (!mesomodelo.equals(meses[res])) {
            res++;
        }
        return res;
    }

}
