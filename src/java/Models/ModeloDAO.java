package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeloDAO extends Conexion {

    public List<Marca> listarMarcas() throws Exception {
        List<Marca> listAll;
        Marca dtl;
        ResultSet rs;
        String sql = "SELECT * FROM MARCA WHERE ESTADO = 'Activo'";

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

    public List<Modelo> listarAll() throws Exception {
        List<Modelo> listAll;
        Modelo dtl;
        ResultSet rs;
        String sql = "SELECT ML.ID,MC.ID AS MARCAID,MC.MARCA,ML.MODELO,ML.ESTADO FROM MODELO ML INNER JOIN  MARCA MC ON ML.MARCA_ID=MC.ID WHERE ML.ESTADO != 'Eliminado'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Modelo();
                dtl.setIdmodelo(rs.getInt("ID"));
                dtl.setMarca(new Marca());
                dtl.getMarca().setIdmarca(Integer.parseInt(rs.getString("MARCAID")));
                dtl.getMarca().setMarca(rs.getString("MARCA"));
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

    public void registrarAll(Modelo dtr) throws Exception {

        String sql;
        int marca = dtr.getMarca().getIdmarca();
        String modelo = dtr.getModelo();
        String estado = dtr.getEstado();
        sql = "INSERT INTO MODELO(MARCA_ID,MODELO,ESTADO)"
                + " VALUES(" + marca + ",'" + modelo + "','" + estado + "')";
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

    public void updateAll(Modelo dtu) throws Exception {
        String sql = "UPDATE MODELO SET MARCA_ID = " + dtu.getMarca().getIdmarca() + ",MODELO = '" + dtu.getModelo() + "',ESTADO = '" + dtu.getEstado() + "' WHERE ID = " + dtu.getIdmodelo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(Modelo dtd) throws Exception {
        String sql = "UPDATE MODELO SET ESTADO = '" + dtd.getEstado() + "' WHERE ID = " + dtd.getIdmodelo();
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
