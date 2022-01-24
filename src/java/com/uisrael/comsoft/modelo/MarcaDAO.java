package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.modelo.entidades.Marca;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO extends Conexion {

    public List<Marca> listarAll() throws Exception {
        List<Marca> listAll;
        Marca dtl;
        ResultSet rs;
        String sql = "SELECT * FROM MARCA WHERE ESTADO != 'Eliminado' ";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Marca();
                dtl.setIdmarca(rs.getInt("ID"));
                dtl.setMarca(rs.getString("MARCA"));
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

    public void registrarAll(Marca dtr) throws Exception {

        String sql;
        String marca = dtr.getMarca();
        String estado = dtr.getEstado();
        sql = "INSERT INTO MARCA(MARCA,ESTADO)"
                + " VALUES('" + marca + "','" + estado + "')";
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

    public void updateAll(Marca dtu) throws Exception {
        String sql = "UPDATE MARCA SET MARCA = '" + dtu.getMarca() + "',ESTADO = '" + dtu.getEstado() + "' WHERE ID = " + dtu.getIdmarca();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(Marca dtd) throws Exception {
        String sql = "UPDATE MARCA SET ESTADO = '" + dtd.getEstado() + "' WHERE ID = " + dtd.getIdmarca();
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
