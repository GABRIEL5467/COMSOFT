package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.modelo.entidades.Servicio;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO extends Conexion {

    public List<Servicio> listarAll() throws Exception {
        List<Servicio> listAll;
        Servicio dtl;
        ResultSet rs;
        String sql = "SELECT * FROM SERVICIO WHERE ESTADO != 'Eliminado' ";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Servicio();
                dtl.setIdservicio(rs.getInt("ID"));
                dtl.setServicio(rs.getString("SERVICIO"));
                dtl.setPrecio(rs.getDouble("PRECIO"));
                dtl.setStock(rs.getInt("STOCK"));
                dtl.setType(rs.getString("TYPE"));
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

    public void registrarAll(Servicio dtr) throws Exception {

        String sql;
        String servicio = dtr.getServicio();
        Double precio = dtr.getPrecio();
        int stock = dtr.getStock();
        String type = dtr.getType();
        String estado = dtr.getEstado();
        sql = "INSERT INTO SERVICIO(SERVICIO,PRECIO,STOCK,TYPE,ESTADO)"
                + " VALUES('" + servicio + "'," + precio + "," + stock + ",'" + type + "','" + estado + "')";
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

    public void updateAll(Servicio dtu) throws Exception {
        String sql = "UPDATE SERVICIO SET SERVICIO = '" + dtu.getServicio()+ "',PRECIO = " + dtu.getPrecio() + ",STOCK = " + dtu.getStock() + ",TYPE = '" + dtu.getType() + "',ESTADO = '" + dtu.getEstado() + "' WHERE ID = " + dtu.getIdservicio();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(Servicio dtd) throws Exception {
        String sql = "UPDATE SERVICIO SET ESTADO = '" + dtd.getEstado() + "' WHERE ID = " + dtd.getIdservicio();
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
