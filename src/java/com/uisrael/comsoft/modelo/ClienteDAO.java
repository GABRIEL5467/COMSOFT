package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.modelo.entidades.Cliente;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends Conexion {

    public List<Cliente> listarAll() throws Exception {
        List<Cliente> listAll;
        Cliente dtl;
        ResultSet rs;
        String sql = "SELECT * FROM CLIENTES WHERE ESTADO != 'Eliminado' ";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Cliente();
                dtl.setIdcliente(rs.getInt("ID"));
                dtl.setDocumento(rs.getString("DOCUMENTO"));
                dtl.setNombresApellidos(rs.getString("NOMBRESAPELLIDOS"));
                dtl.setEmail(rs.getString("EMAIL"));
                dtl.setCel(rs.getString("CEL"));
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

    public void registrarAll(Cliente dtr) throws Exception {

        String sql;
        String doc = dtr.getDocumento();
        String np = dtr.getNombresApellidos();
        String email = dtr.getEmail();
        String cel = dtr.getCel();
        String estado = dtr.getEstado();
        sql = "INSERT INTO CLIENTES(DOCUMENTO,NOMBRESAPELLIDOS,EMAIL,CEL,ESTADO)"
                + " VALUES('" + doc + "','" + np + "','" + email + "','" + cel + "','" + estado + "')";
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

    public void updateAll(Cliente dtu) throws Exception {
        String sql = "UPDATE CLIENTES SET DOCUMENTO = '" + dtu.getDocumento() + "', "
                + "NOMBRESAPELLIDOS = '" + dtu.getNombresApellidos() + "',EMAIL = '" + dtu.getEmail() + "', "
                + "CEL = '" + dtu.getCel()+ "',ESTADO = '" + dtu.getEstado() + "' WHERE ID = " + dtu.getIdcliente();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(Cliente dtd) throws Exception {
        String sql = "UPDATE CLIENTES SET ESTADO = '" + dtd.getEstado() + "' WHERE ID = " + dtd.getIdcliente();
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
