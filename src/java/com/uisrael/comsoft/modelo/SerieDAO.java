package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.conexion.Conexion;
import com.uisrael.comsoft.modelo.entidades.Serie;
import com.uisrael.comsoft.modelo.entidades.Modelo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO extends Conexion {

    public List<Modelo> listarModelos() throws Exception {
        List<Modelo> listAll;
        Modelo dtl;
        ResultSet rs;
        String sql = "SELECT * FROM MODELO WHERE ESTADO = 'Activo'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Modelo();
                dtl.setIdmodelo(rs.getInt("ID"));
                dtl.setModelo(rs.getString("MODELO"));
                dtl.setEstado(rs.getString("ESTADO"));
                listAll.add(dtl);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return listAll;
    }

    public List<Serie> listarAll() throws Exception {
        List<Serie> listAll;
        Serie dtl;
        ResultSet rs;
        String sql = "SELECT SE.ID,ML.ID AS MODELOID,ML.MODELO,SE.SERIE,SE.ESTADO FROM SERIE SE INNER JOIN  MODELO ML ON SE.MODELO_ID=ML.ID WHERE SE.ESTADO != 'Eliminado'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Serie();
                dtl.setIdserie(rs.getInt("ID"));
                dtl.setModelo(new Modelo());
                dtl.getModelo().setIdmodelo(Integer.parseInt(rs.getString("MODELOID")));
                dtl.getModelo().setModelo(rs.getString("MODELO"));
                dtl.setSerie(rs.getString("SERIE"));
                dtl.setEstado(rs.getString("ESTADO"));
                listAll.add(dtl);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return listAll;
    }

    public void registrarAll(Serie dtr) throws Exception {

        String sql;
        int modelo = dtr.getModelo().getIdmodelo();
        String serie = dtr.getSerie();
        String estado = dtr.getEstado();
        sql = "INSERT INTO SERIE(MODELO_ID,SERIE,ESTADO)"
                + " VALUES(" + modelo + ",'" + serie + "','" + estado + "')";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
    }

    public void updateAll(Serie dtu) throws Exception {
        String sql = "UPDATE SERIE SET MODELO_ID = " + dtu.getModelo().getIdmodelo()+ ",SERIE = '" + dtu.getSerie()+ "',ESTADO = '" + dtu.getEstado() + "' WHERE ID = " + dtu.getIdserie();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(Serie dtd) throws Exception {
        String sql = "UPDATE SERIE SET ESTADO = '" + dtd.getEstado() + "' WHERE ID = " + dtd.getIdserie();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }
}
