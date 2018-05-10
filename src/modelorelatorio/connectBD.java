/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelorelatorio;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.runtime.StoredScript;
import org.eclipse.persistence.internal.core.helper.CoreClassConstants;
import reportes.ObjDespesa;
import reportes.ObjReceita;
import reportes.Objcategoria;
import reportes.Objgrupo;
import reportes.Objsubgrupo;
import visual.Config;

/**
 *
 * @author casa
 */
public class connectBD {

    private Connection conexion;

    private Statement sentencia;

    private final String controlador;

    private final String nombre_bd;

    private final String usuarioBD;

    private final String passwordBD;

    public connectBD() {

        this.controlador = "sun.jdbc.odbc.JdbcOdbcDriver";

        this.nombre_bd = "modelo_relatorio_actua.accdb";

        this.usuarioBD = "";

        this.passwordBD = "";
    }

    public boolean EstablecerConexion() throws SQLException {
        try {

            conexion = DriverManager.getConnection("jdbc:ucanaccess://" + this.nombre_bd, this.usuarioBD, this.passwordBD);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al realizar la conexion " + e);

            return false;

        }

        try {

            this.sentencia = this.conexion.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al crear el objeto sentencia " + e);

            return false;

        }

        return true;

    }

    public ResultSet EjecutarSentencia(String sql) throws SQLException {
        ResultSet rs;
        rs = this.sentencia.executeQuery(sql);
        //rs = con.sentencia.executeQuery(sql);
        return rs;

    }

    //sentencias de incersion
    public void insertarResumen(Resumen r) throws SQLException {
        boolean sent = this.sentencia.execute("INSERT INTO resumen (anno, mes, receitas, despesas, saldoanterior) VALUES (" + r.getAnno() + ", " + r.getMes() + ", " + r.getSumareceitas() + ", " + r.getSumadespesas() + ", " + r.getSaldoanterior() + " )");
    }

    public void insertarReceita(Receitas r) throws SQLException {
        System.out.println("INSERT INTO receita (numero, categoria, cantidad, valor, idresumen) VALUES (" + r.getNumero() + ", " + r.getCategoria() + ", " + r.getCantidad() + ", " + r.getValor() + ", " + r.getIdresumen() + " )");
        String valorV = r.getValor() + "";
        valorV = valorV.replace("E", "E+");

        boolean sent = this.sentencia.execute("INSERT INTO receita (numero, coordenada, categoria, cantidad, valor, idresumen) VALUES (" + r.getNumero() + ", " + r.getCoordenada() + ", '" + r.getCategoria() + "', " + r.getCantidad() + ", " + valorV + ", " + r.getIdresumen() + " )");
    }

    public void insertarDespensa(Despesas r) throws SQLException {
        System.out.println("INSERT INTO despesas (numero, numerodocumento, coordenada, fecha, categoria, subcategoria, descripcion, valor, idresumen) VALUES (" + r.getNumero() + ", " + r.getNumeroDocumento() + ", " + r.getCoordenada() + ", '" + r.getFecha().toString() + "', '" + r.getCategoria() + "', '" + r.getSubcategoria() + "', '" + r.getDescripcion() + "', " + r.getValor() + ", " + r.getIdresumen() + " )");
        String valorV = r.getValor() + "";
        valorV = valorV.replace("E", "E+");
        boolean sent = this.sentencia.execute("INSERT INTO despesas (numero, numerodocumento, coordenada, fecha, categoria, subcategoria, descripcion, valor, idresumen) VALUES (" + r.getNumero() + ", " + r.getNumeroDocumento() + ", " + r.getCoordenada() + ", #" + r.getFecha() + "#, '" + r.getCategoria() + "', '" + r.getSubcategoria() + "', '" + r.getDescripcion() + "', " + valorV + ", " + r.getIdresumen() + " )");
    }

    //------------------------sentencia de actualizacion---------------------------------------
    public void update(String tabla, String campofiltro, String valorfiltro, String campomodificado, String nuevovalor) throws SQLException {
        boolean sent = this.sentencia.execute("UPDATE " + tabla + " SET " + campomodificado + " = '" + nuevovalor + "' WHERE " + campofiltro + " = '" + valorfiltro + "'");
    }

    //------------------------sentencia de eliminacion---------------------------------
    public void delete(String tabla, String campoeliminacion, String valoreliminacion) throws SQLException {
        boolean sent = this.sentencia.execute("DELETE FROM " + tabla + " WHERE " + campoeliminacion + " = '" + valoreliminacion + "'");
    }

    //-------------------------sentencias de consulta------------------------------
    public String[][] getResumen() throws SQLException {
        ResultSet sent = EjecutarSentencia("SELECT * FROM resumen ORDER BY resumen.anno DESC , resumen.mes DESC");

        int total = 0;
        while (sent.next()) {
            total++;
        }
        sent.first();

        //System.out.println("filas " + total);
        //String[][] resum = new String[sent.getRow()][8];
        String[][] resum = new String[total][8];
        Config.indiceResumen = new String[total];

        ArrayList<Resumen> resumens = new ArrayList<Resumen>();

        int indice = 0;
        //if (sent.getRow() > 0) {
        do {
            Resumen r = new Resumen();
            r.setId(sent.getInt("Id"));
            //resum[indice][0] = sent.getInt("Id") + "";
            Config.indiceResumen[indice] = sent.getInt("Id") + "";

            r.setAnno(sent.getInt("anno"));
            resum[indice][0] = sent.getString("anno") + "";

            r.setMes(sent.getInt("mes"));
            resum[indice][1] = Config.meses[Integer.parseInt(sent.getString("mes"))];

            resum[indice][2] = sent.getString("receitas") + "";
            resum[indice][3] = sent.getString("despesas") + "";
            resum[indice][4] = sent.getString("saldomensual") + "";
            resum[indice][5] = sent.getString("saldoanterior") + "";
            resum[indice][6] = sent.getString("saldoactual") + "";

            resumens.add(r);

            indice++;
        } while (sent.next());
        //}
        return resum;

    }

    //-------------------Obtener la lista de annos y meses--------------------------
    public String[] getAnnoMes() {
        String[] resum = new String[3];
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen");

            int total = 0;
            while (sent.next()) {
                total++;
            }
            sent.first();

            //System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {
                resum[0] = sent.getInt("anno") + "";

                resum[1] = sent.getInt("mes") + "";

                resum[2] = Config.meses[Integer.parseInt(resum[1])];

            }

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resum;
    }

    //-------------------Obtener la lista de meses para un annp dado------------------
    public String[] getAnnoMes(String anno) {
        String[] resum = new String[3];
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "'");

            int total = 0;
            while (sent.next()) {
                total++;
            }
            sent.first();

            //System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {
                resum[0] = sent.getInt("anno") + "";

                resum[1] = sent.getInt("mes") + "";

                resum[2] = Config.meses[Integer.parseInt(resum[1])];
            }

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resum;
    }

    //-----------------------Obtener una el anno y mes-----------------------------
    public String[] getAnnoMes(String anno, String mes) {
        String[] resum = new String[3];
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "' AND mes = '" + mes + "'");

            int total = 0;
            while (sent.next()) {
                total++;
            }
            sent.first();

            //System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {
                resum[0] = sent.getInt("anno") + "";

                resum[1] = sent.getInt("mes") + "";

                resum[2] = Config.meses[Integer.parseInt(resum[1])];

            }

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resum;
    }

    //------------------------------Obtener elñ listado de annos-------------------
    public String[] getListaAnnos() {

        try {
            String[] resum;
            TreeSet<String> s = new TreeSet<String>();

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen");
            sent.first();

            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            if (sent.getRow() > 0) {
                do {
                    s.add(sent.getInt("anno") + "");

                } while (sent.next());
            }

            resum = new String[s.size()];
            int total = s.size();
            for (int i = 0; i < total; i++) {
                resum[i] = s.first();
                s.remove(resum[i]);
            }

            return resum;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //--------------Obtener el listado de meses pa un anno determinado-----------
    public String[] getListaMeses(String anno) {
        try {
            String[] resum;

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "'");

            int total = 0;
            while (sent.next()) {
                total++;
            }
            sent.first();

            resum = new String[total];

            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {
                do {
                    resum[indice] = sent.getInt("mes") + "";

                    //resum[1] = sent.getString("mes") + "";
                    indice++;
                } while (sent.next());
            }

            return resum;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //------------------------------Obtener elñ listado de instituciones-------------------
    public String[] getListaInstituciones() {

        try {
            String[] resum;

            ResultSet sent = EjecutarSentencia("SELECT * FROM institucion");
            sent.first();

            //ArrayList<Resumen> resumens = new ArrayList<Resumen>();
            int total = 0;
            if (sent.getRow() > 0) {
                do {
                    total++;
                } while (sent.next());
            }

            resum = new String[total];
            sent.first();
            for (int i = 0; i < total; i++) {
                resum[i] = sent.getString("nombre");
                sent.next();
                //s.remove(resum[i]);
            }

            return resum;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //--------------------muestra una tabla resumen del mes-----------------
    public String[][] getResumenMes(String anno, String mes) {
        try {
            String[][] valores = new String[5][2];
            valores[0][0] = "Receitas";
            valores[1][0] = "Despesas";
            valores[2][0] = "Saldo Mensal";
            valores[3][0] = "Saldo Anterior";
            valores[4][0] = "Saldo Total";

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "' AND mes = '" + mes + "'");

            sent.first();

            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {

                valores[0][1] = sent.getDouble("receitas") + "";
                valores[1][1] = sent.getDouble("despesas") + "";
                valores[2][1] = sent.getDouble("saldomensual") + "";
                valores[3][1] = sent.getDouble("saldoanterior") + "";
                valores[4][1] = sent.getDouble("saldoactual") + "";

                //resum[1] = sent.getString("mes") + "";
            }

            return valores;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //---------------obtener las receitas para un anno y mes determinado----------------
    public String[][] getReceitaMes(String anno, String mes) {
        try {
            String[][] valores;

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "' AND mes = '" + mes + "'");
            sent.first();

            if (sent.getRow() > 0) {
                int id = sent.getInt("Id");
                //System.out.println("id "+id);
                ResultSet sent1 = EjecutarSentencia("SELECT * FROM receita WHERE idresumen = '" + id + "'");
                sent1.first();

                int total = 1;
                while (sent1.next()) {
                    total++;
                }
                //System.out.println("total "+total);
                sent1.first();

                valores = new String[total][6];
                Config.indiceReceita = new String[total];
                //System.out.println("filas " + total);
                //String[][] resum = new String[sent.getRow()][8];
                ArrayList<Resumen> resumens = new ArrayList<Resumen>();

                int indice = 0;
                if (sent1.getRow() > 0) {
                    do {
                        Config.indiceReceita[indice] = sent1.getInt("Id") + "";
                        valores[indice][0] = sent1.getString("numero") + "";
                        valores[indice][1] = sent1.getString("coordenada") + "";
                        valores[indice][2] = sent1.getString("categoria") + "";
                        valores[indice][3] = sent1.getInt("cantidad") + "";
                        valores[indice][4] = sent1.getDouble("valor") + "";
                        valores[indice][5] = sent1.getDouble("total") + "";
                        indice++;
                    } while (sent1.next());
                }
                return valores;
            }
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //-----------------------obtener las receitas para un anno y mes determinado--------------------
    public String[][] getDespesaMes(String anno, String mes) {
        try {
            String[][] valores;

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "' AND mes = '" + mes + "'");
            sent.first();

            if (sent.getRow() > 0) {
                int id = sent.getInt("Id");

                sent = EjecutarSentencia("SELECT * FROM despesas WHERE idresumen = '" + id + "'");
                sent.first();

                int total = 1;
                while (sent.next()) {
                    total++;
                }
                sent.first();

                valores = new String[total][8];
                Config.indiceDespesa = new String[total];
                //System.out.println("filas " + total);
                //String[][] resum = new String[sent.getRow()][8];
                ArrayList<Resumen> resumens = new ArrayList<Resumen>();

                int indice = 0;
                if (sent.getRow() > 0) {
                    do {
                        Config.indiceDespesa[indice] = sent.getInt("Id") + "";
                        valores[indice][0] = sent.getString("numero") + "";
                        valores[indice][1] = sent.getString("numerodocumento") + "";
                        valores[indice][2] = sent.getString("coordenada") + "";
                        valores[indice][3] = sent.getDate("fecha").toString() + "";
                        valores[indice][4] = sent.getString("categoria") + "";
                        valores[indice][5] = sent.getString("subcategoria") + "";
                        valores[indice][6] = sent.getString("descripcion") + "";
                        valores[indice][7] = sent.getDouble("valor") + "";

                        indice++;
                    } while (sent.next());
                }
                return valores;
            }
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //----------Obtener la asociacion de valores posibles de categoria-valor de receita-----
    public String[][] getCatValor() {
        try {
            String[][] valores;
            ResultSet sent = EjecutarSentencia("SELECT * FROM descricao");
            sent.first();

            int total = 1;
            while (sent.next()) {
                total++;
            }
            sent.first();

            valores = new String[2][total];

            //System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {
                do {
                    valores[0][indice] = sent.getString("categoria") + "";
                    valores[1][indice] = sent.getDouble("valor") + "";

                    indice++;
                } while (sent.next());
            }
            return valores;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //--------------Selecciona la categoria de despesa-----------------
    public String[] getCatDesGrupo() {
        try {
            String[] valores;
            TreeSet<String> s = new TreeSet<String>();

            ResultSet sent = EjecutarSentencia("SELECT * FROM categoriades");
            sent.first();

            //ArrayList<Resumen> resumens = new ArrayList<Resumen>();
            if (sent.getRow() > 0) {
                do {
                    s.add(sent.getString("grupo") + "");
                } while (sent.next());
            }

            valores = new String[s.size()];
            int total = s.size();
            for (int i = 0; i < total; i++) {
                valores[i] = s.first();
                s.remove(valores[i]);
            }

            return valores;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //----------------Selecciona las subcategira de una categoria de despesa----------------
    public String[] getCatDesValores(String grupo) {
        try {
            String[] valores;
            ResultSet sent = EjecutarSentencia("SELECT * FROM categoriades WHERE grupo = '" + grupo + "'");
            sent.first();

            int total = 1;
            while (sent.next()) {
                total++;
            }
            sent.first();

            valores = new String[total];

            //System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            if (sent.getRow() > 0) {
                do {
                    valores[indice] = sent.getString("categoria") + "";

                    indice++;
                } while (sent.next());
            }
            return valores;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //----------------------devuelve si exise el registro-------------------------
    public int cantResumen(String anno, String mes) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "' AND mes = '" + mes + "'");
            sent.first();
            return sent.getRow();
        } catch (SQLException ex) {

            //Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    //-----------------Obtener Id de un anno y mesa determinado---------------------
    public String getIdResumen(String anno, String mes) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = '" + anno + "' AND mes = '" + mes + "'");
            sent.first();
            return sent.getInt("Id") + "";
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    //--------------------------------suma de despesa segun id--------------------------------
    public double sumaTotalDespesa(int idResumen) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT Sum(despesas.valor) AS SumaDevalor FROM despesas WHERE ((despesas.idresumen)=" + idResumen + ")");
            sent.first();
            return sent.getDouble("SumaDevalor");
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //--------------------------------suma de receita segun id--------------------------------
    public double sumaTotalReceita(int idResumen) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT Sum(receita.total) AS SumaDevalor FROM receita WHERE ((receita.idresumen)=" + idResumen + ")");
            sent.first();
            return sent.getDouble("SumaDevalor");
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //-------------------Devuelve el saldo dado un id----------------------
    public double getSaldoAnterior(String id) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE Id = " + id);
            sent.first();
            System.out.println("sado: " + sent.getDouble("saldoactual"));
            return sent.getDouble("saldoactual");
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //--------------------Devuelve los valores de una receita dada-------------------------
    public Receitas getUnaReceita(String indrec) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM receita WHERE Id = " + indrec);
            sent.first();

            Receitas re = new Receitas();

            re.setId(sent.getInt("Id"));
            re.setNumero((sent.getString("numero")));
            re.setCoordenada((sent.getString("coordenada")));
            re.setCategoria(sent.getString("categoria"));
            re.setCantidad(sent.getInt("cantidad"));
            re.setValor(sent.getFloat("valor"));
            re.setTotal(sent.getFloat("total"));
            re.setIdresumen(sent.getInt("idresumen"));

            return re;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Receitas();
    }

    //--------------------Devuelve los valores de una despesa dada-------------------------
    public Despesas getUnaDespesas(String indrec) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM despesas WHERE Id = " + indrec);
            sent.first();

            Despesas de = new Despesas();

            de.setId(sent.getInt("Id"));
            de.setNumero((sent.getString("numero")));
            de.setNumeroDocumento((sent.getString("numerodocumento")));
            de.setCoordenada((sent.getString("coordenada")));
            de.setFecha(sent.getString("fecha"));
            de.setCategoria(sent.getString("categoria"));
            de.setSubcategoria(sent.getString("subcategoria"));
            de.setDescripcion(sent.getString("descripcion"));
            de.setValor(sent.getDouble("valor"));
            de.setIdresumen(sent.getInt("idresumen"));

            return de;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Despesas();
    }

    public String[] getAnnoMesporID(int idresumen) {
        String[] res = new String[2];
        try {

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE Id = " + idresumen);
            sent.first();

            Despesas de = new Despesas();

            res[0] = sent.getInt("anno") + "";
            res[1] = sent.getInt("mes") + "";
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public void actualizarReceita(Receitas r) {
        try {
            String valorV = r.getValor() + "";
            valorV = valorV.replace("E", "E+");

            String sentSQL = "UPDATE receita SET ";
            sentSQL += "numero = " + r.getNumero() + ", ";
            sentSQL += "coordenada = " + r.getCoordenada() + ", ";
            sentSQL += "categoria = '" + r.getCategoria() + "', ";
            sentSQL += "cantidad = " + r.getCantidad() + ", ";
            sentSQL += "valor = " + valorV + " ";
            sentSQL += "WHERE Id = " + r.getId() + "";
            boolean sent = this.sentencia.execute(sentSQL);
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarDespesa(String anno, String mes, Despesas d) {
        try {
            String valorV = d.getValor() + "";
            valorV = valorV.replace("E", "E+");

            String sentSQL = "UPDATE despesas SET ";
            sentSQL += "numero = " + d.getNumero() + ", ";
            sentSQL += "numerodocumento = " + d.getNumeroDocumento() + ", ";
            sentSQL += "coordenada = " + d.getCoordenada() + ", ";
            sentSQL += "fecha = #" + d.getFecha() + "#, ";
            sentSQL += "categoria = '" + d.getCategoria() + "', ";
            sentSQL += "subcategoria = '" + d.getSubcategoria() + "', ";
            sentSQL += "descripcion = '" + d.getDescripcion() + "', ";
            sentSQL += "valor = " + valorV + " ";
            sentSQL += "WHERE Id = " + d.getId() + "";
            boolean sent = this.sentencia.execute(sentSQL);
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //----------------conbsultas para reportes
    public LinkedList<ObjReceita> getListaReceita(String anno, String mes) {
        try {
            LinkedList<ObjReceita> listaReceita = new LinkedList<ObjReceita>();
            //String[] resum;

            String idR = getIdResumen(anno, mes);

            ResultSet sent = EjecutarSentencia("SELECT * FROM receita WHERE idresumen = '" + idR + "'");

            //int total = 0;
            //while (sent.next()) {
            //    total++;
            //}
            sent.first();

            //resum = new String[total];
            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            ObjReceita r;
            if (sent.getRow() > 0) {
                do {
                    r = new ObjReceita();
                    r.setNumero(sent.getString("numero") + "");
                    r.setCoordenada(sent.getString("coordenada") + "");
                    r.setCategoria(sent.getString("categoria") + "");
                    r.setCantidad(sent.getDouble("cantidad") + "");
                    r.setValor(sent.getDouble("valor") + "");
                    r.setTotal(sent.getDouble("total"));

                    listaReceita.add(r);
                    //resum[1] = sent.getString("mes") + "";
                    indice++;

                } while (sent.next());
            }

            if (listaReceita.size() == 0) {
                r = new ObjReceita();
                r.setNumero("-");
                r.setCoordenada("-");
                r.setCategoria("-");
                r.setCantidad("0");
                r.setValor("0");
                r.setTotal(0);

                listaReceita.add(r);
            }

            return listaReceita;

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LinkedList<ObjDespesa> getListaDespesa(String anno, String mes) {
        try {
            LinkedList<ObjDespesa> listaDespesa = new LinkedList<ObjDespesa>();
            //String[] resum;

            String idR = getIdResumen(anno, mes);

            ResultSet sent = EjecutarSentencia("SELECT * FROM despesas WHERE idresumen = '" + idR + "' ORDER BY categoria");

            //int total = 0;
            //while (sent.next()) {
            //    total++;
            //}
            sent.first();

            //resum = new String[total];
            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            int indice = 0;
            ObjDespesa d;
            if (sent.getRow() > 0) {
                do {
                    d = new ObjDespesa();
                    d.setNumero(sent.getString("numero") + "");
                    d.setNumerodocumento(sent.getString("numerodocumento") + "");
                    d.setCoordenada(sent.getString("coordenada") + "");
                    d.setFecha(sent.getDate("fecha").toString() + "");
                    d.setSubcategoria(sent.getString("subcategoria") + "");
                    d.setDescripcion(sent.getString("descripcion") + "");
                    d.setTotal(sent.getDouble("valor"));

                    listaDespesa.add(d);
                    //resum[1] = sent.getString("mes") + "";
                    indice++;

                } while (sent.next());
            }

            if (listaDespesa.size() == 0) {
                d = new ObjDespesa();
                d.setNumero("-");
                d.setNumerodocumento("-");
                d.setCoordenada("");
                d.setFecha("-");
                d.setSubcategoria("-");
                d.setDescripcion("-");
                d.setTotal(0);

                listaDespesa.add(d);
            }
            return listaDespesa;

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[][] getGrupoCategoriaSum(String id) {
        try {
            String[][] categorias = new String[5][2];
            ResultSet sent = EjecutarSentencia("SELECT despesas.categoria, despesas.idresumen, Sum(despesas.valor) AS SumaDevalor FROM despesas GROUP BY despesas.categoria, despesas.idresumen HAVING (((despesas.idresumen)=" + id + ")) ORDER BY despesas.categoria, despesas.idresumen");

            sent.first();
            System.out.println("ind " + sent.getRow());

            //resum = new String[total];
            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<Resumen> resumens = new ArrayList<Resumen>();

            //categorias[0][0] = (("Pessoal") + "");
            //categorias[1][0] = (("Despesas de Bens e Servicos") + "");
            //categorias[2][0] = (("Subsidios e transferencias correntes") + "");
            //categorias[3][0] = (("Despesas de Capital financeiro") + "");
            //categorias[4][0] = (("Juros") + "");
            //categorias[0][1] = "0";
            //categorias[1][1] = "0";
            //categorias[2][1] = "0";
            //categorias[3][1] = "0";
            //categorias[4][1] = "0";
            int indice = 0;
            String s;
            ObjDespesa d;
            if (sent.getRow() > 0) {
                sent.first();
                do {
                    System.out.println("ind " + indice);
                    d = new ObjDespesa();
                    s = (sent.getString("categoria") + "");
                    //if (s.equals(categorias[0][0])) {
                    //    indice = 0;
                    //}
                    //if (s.equals(categorias[1][0])) {
                    //    indice = 1;
                    //}
                    //if (s.equals(categorias[2][0])) {
                    //    indice = 2;
                    //}
                    //if (s.equals(categorias[3][0])) {
                    //    indice = 3;
                    //}
                    // if (s.equals(categorias[4][0])) {
                    //     indice = 4;
                    // }
                    categorias[indice][0] = (sent.getString("categoria") + "");

                    categorias[indice][1] = (sent.getDouble("sumaDevalor") + "");

                    indice++;
                    //resum[1] = sent.getString("mes") + "";
                } while (sent.next());

            }

            String[][] cat = new String[5][2];
            ResultSet sen = EjecutarSentencia("SELECT categoriades.grupo FROM categoriades GROUP BY categoriades.grupo ORDER BY categoriades.grupo");

            sen.first();
            int indice1 = 0;
            if (sen.getRow() > 0) {
                sen.first();
                do {
                    //System.out.println("ind " + indice);
                    //d = new ObjDespesa();
                    //s = (sent.getString("categoria") + "");
                    //if (s.equals(categorias[0][0])) {
                    //    indice = 0;
                    //}
                    //if (s.equals(categorias[1][0])) {
                    //    indice = 1;
                    //}
                    //if (s.equals(categorias[2][0])) {
                    //    indice = 2;
                    //}
                    //if (s.equals(categorias[3][0])) {
                    //    indice = 3;
                    //}
                    // if (s.equals(categorias[4][0])) {
                    //     indice = 4;
                    // }
                    cat[indice1][0] = (sen.getString("grupo") + "");

                    cat[indice1][1] = "0";

                    indice1++;
                    //resum[1] = sent.getString("mes") + "";
                } while (sen.next());

            }

            for (int i = 0; i < cat.length; i++) {
                for (int j = 0; j < categorias.length; j++) {
                    if (cat[i][0].equals(categorias[j][0])) {
                        cat[i][1] = categorias[j][1];
                    }

                }

            }

            return cat;

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LinkedList<ObjMes> getListaResumenes(String string) {
        try {
            LinkedList<ObjMes> listaResumen = new LinkedList<ObjMes>();
            //String[] resum;

            ResultSet sent = EjecutarSentencia("SELECT * FROM resumen WHERE anno = " + string + "");

            //int total = 0;
            //while (sent.next()) {
            //    total++;
            //}
            sent.first();

            //resum = new String[total];
            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            ArrayList<ObjMes> resumens = new ArrayList<ObjMes>();

            int indice = 0;
            ObjMes d;
            String[][] b = new String[12][5];
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < 5; j++) {
                    if (j >= 3) {
                        b[i][j] = "";
                    } else {
                        b[i][j] = "0";
                    }
                }
            }
            int ind = 0;
            if (sent.getRow() > 0) {

                do {
                    d = new ObjMes();
                    //d.setId(sent.getInt("Id"));
                    //d.setAnno(sent.getInt("anno"));
                    ind = sent.getInt("mes");
                    //d.setMes(Config.meses[sent.getInt("mes")]+"");
                    //d.setReceita(sent.getDouble("receitas")+"" );
                    //d.setDespesa(sent.getDouble("despesas")+"" );
                    //d.setDiferencia(sent.getDouble("saldomensual") +"");
                    //d.setAnterior(sent.getDouble("saldoanterior")+"");
                    //d.setActual(sent.getDouble("saldoactual")+"");
                    //b[ind][0] =(Config.meses[sent.getInt("mes")] + "");
                    b[ind][0] = (sent.getDouble("receitas") + "");
                    b[ind][1] = (sent.getDouble("despesas") + "");
                    b[ind][2] = (sent.getDouble("saldomensual") + "");
                    b[ind][3] = (sent.getDouble("saldoanterior") + "");
                    b[ind][4] = (sent.getDouble("saldoactual") + "");
                    //listaResumen.add(d);
                    //resum[1] = sent.getString("mes") + "";
                    indice++;

                } while (sent.next());
            }

            if (b[0][3].equals("")) {
                ind = 0;
                do {
                    ind++;
                } while (b[ind][3].equals(""));
            }
            ind = ind - 1;
            for (int i = ind; i >= 0; i--) {
                b[i][3] = b[i + 1][3];
                b[i][4] = b[i][3];
            }

            for (int i = 0; i < b.length; i++) {
                d = new ObjMes();
                d.setMes(Config.meses[i + 1] + "");
                d.setReceita(b[i][0]);
                d.setDespesa(b[i][1]);
                d.setDiferencia(b[i][2]);
                if (b[i][3].equals("")) {
                    if (i > 0) {
                        d.setAnterior(b[i - 1][4]);
                        d.setActual(b[i - 1][4]);
                        b[i][3] = b[i - 1][4];
                        b[i][4] = b[i - 1][4];
                    } else {
                        d.setAnterior("0");
                        d.setActual("0");
                    }
                } else {
                    d.setAnterior(b[i][3]);
                    d.setActual(b[i][4]);
                }

                //d.setActual(b[i][4]);
                //b[ind][0] =(Config.meses[sent.getInt("mes")] + "");
                listaResumen.add(d);

            }

            return listaResumen;

        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getIdReceita(String indrec) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM receita WHERE Id = " + indrec + "");
            sent.first();
            return sent.getInt("idresumen") + "";
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String getIdDespesa(String indrec) {
        try {
            ResultSet sent = EjecutarSentencia("SELECT * FROM despesas WHERE Id = " + indrec + "");
            sent.first();
            return sent.getInt("idresumen") + "";
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public LinkedList<Objgrupo> getListaCategoria(String anno, String mes) {
        try {
            LinkedList<Objcategoria> listaDespesa = new LinkedList<Objcategoria>();
            LinkedList<Objsubgrupo> listaSubgrupo = new LinkedList<Objsubgrupo>();
            LinkedList<Objgrupo> listaGrupo = new LinkedList<Objgrupo>();
            //String[] resum;

            //String idR = getIdResumen(anno, mes);
            ResultSet sent1 = EjecutarSentencia("SELECT categoriades.grupo, categoriades.subgrupo, categoriades.categoria FROM categoriades, despesas WHERE (((categoriades.categoria)=[despesas].[subcategoria]) AND ((categoriades.grupo)=[despesas].[categoria])) ORDER BY categoriades.grupo, categoriades.subgrupo, categoriades.categoria");
            ResultSet sent2 = EjecutarSentencia("SELECT despesas.* FROM categoriades, despesas WHERE (((categoriades.categoria)=[despesas].[subcategoria]) AND ((categoriades.grupo)=[despesas].[categoria])) ORDER BY categoriades.grupo, categoriades.subgrupo, categoriades.categoria;");

            //int total = 0;
            //while (sent.next()) {
            //    total++;
            //}
            sent1.first();
            sent2.first();

            //resum = new String[total];
            // System.out.println("filas " + total);
            //String[][] resum = new String[sent.getRow()][8];
            //ArrayList<Resumen> resumens = new ArrayList<Resumen>();
            int indice = 0;
            Objcategoria d;
            Objsubgrupo s;
            Objgrupo g;
            if (sent1.getRow() > 0) {
                String subgrupoanterior = sent1.getString("subgrupo");
                String subgrupoactual = sent1.getString("subgrupo");

                String grupoanterior = sent1.getString("grupo");
                String grupoactual = sent1.getString("grupo");
                do {
                    subgrupoactual = sent1.getString("subgrupo");
                    grupoactual = sent1.getString("grupo");
                    if (!grupoactual.equals(grupoanterior)) {
                        Objsubgrupo sub = new Objsubgrupo();
                        sub.setSubgrupo(subgrupoanterior);
                        sub.setCategorias(listaDespesa);
                        listaSubgrupo.add(sub);

                        Objgrupo grup = new Objgrupo();
                        grup.setGrupo(grupoanterior);
                        grup.setSubgrupo(listaSubgrupo);
                        listaGrupo.add(grup);

                        listaSubgrupo = new LinkedList<>();
                        listaDespesa = new LinkedList<>();

                    } else if (!subgrupoactual.equals(subgrupoanterior)) {
                        Objsubgrupo sub = new Objsubgrupo();
                        sub.setSubgrupo(subgrupoanterior);
                        sub.setCategorias(listaDespesa);
                        listaSubgrupo.add(sub);

                        listaDespesa = new LinkedList<>();
                    }

                    d = new Objcategoria();
                    d.setNumero(sent2.getString("numero") + "");
                    d.setNumerodocumento(sent2.getString("numerodocumento") + "");
                    //d.setCoordenada(sent2.getString("coordenada") + "");
                    d.setFecha(sent2.getDate("fecha").toString() + "");
                    d.setSubcategoria(sent2.getString("subcategoria") + "");
                    d.setDescripcion(sent2.getString("descripcion") + "");
                    d.setTotal(sent2.getDouble("valor"));

                    listaDespesa.add(d);
                    //resum[1] = sent.getString("mes") + "";
                    subgrupoanterior = subgrupoactual;
                    grupoanterior = grupoactual;
                    indice++;

                    sent2.next();

                } while (sent1.next());
                Objsubgrupo sub = new Objsubgrupo();
                sub.setSubgrupo(subgrupoanterior);
                sub.setCategorias(listaDespesa);
                listaSubgrupo.add(sub);

                Objgrupo grup = new Objgrupo();
                grup.setGrupo(grupoanterior);
                grup.setSubgrupo(listaSubgrupo);
                listaGrupo.add(grup);
            }

            if (false) {
                if (listaDespesa.size() == 0) {
                    d = new Objcategoria();
                    d.setNumero("-");
                    d.setNumerodocumento("-");
                    d.setCoordenada("");
                    d.setFecha("-");
                    d.setSubcategoria("-");
                    d.setDescripcion("-");
                    d.setTotal(0);

                    listaDespesa.add(d);
                }
            }
            return listaGrupo;
        } catch (SQLException ex) {
            Logger.getLogger(connectBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
