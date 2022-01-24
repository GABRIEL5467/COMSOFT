package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.conexion.Conexion;
import com.uisrael.comsoft.modelo.entidades.Usuario;
import com.uisrael.comsoft.modelo.entidades.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends Conexion {

    public Usuario Login(Usuario user) throws Exception {
        Usuario usuLog = null;
        ResultSet rs = null;
        String sql = "SELECT U.ID, R.NOMBRE, U.NOMBRES, U.APELLIDOS, U.EMAIL, U.PASSWORD, U.ESTADO "
                + "FROM USUARIO U INNER JOIN ROL R ON U.ROL_ID = R.ID "
                + "WHERE U.EMAIL = '" + user.getEmail() + "' AND "
                + "PASSWORD = '" + user.getPassword() + "' AND ESTADO != 'Eliminado'";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usuLog = new Usuario();
                usuLog.setId(rs.getInt("ID"));
                usuLog.setRol(new Rol());
                usuLog.getRol().setNombre(rs.getString("NOMBRE"));
                usuLog.setNombres(rs.getString("NOMBRES"));
                usuLog.setApellidos(rs.getString("APELLIDOS"));
                usuLog.setEmail(rs.getString("EMAIL"));
                usuLog.setPassword(rs.getString("PASSWORD"));
                usuLog.setEstado(rs.getString("ESTADO"));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        } finally {
            this.cerrar(false);
        }
        return usuLog;
    }

    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios;
        Usuario usu;
        ResultSet rs = null;
        String sql = "SELECT U.ID, R.ID AS ROL_ID,R.NOMBRE, U.NOMBRES, U.APELLIDOS, U.EMAIL, U.PASSWORD, U.ESTADO FROM USUARIO U "
                + "INNER JOIN ROL R ON U.ROL_ID = R.ID WHERE ESTADO != 'Eliminado' ";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuarios = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setId(rs.getInt("ID"));
                usu.setRol(new Rol());
                usu.getRol().setNombre(rs.getString("NOMBRE"));
                usu.getRol().setId(rs.getInt("ROL_ID"));
                usu.setNombres(rs.getString("NOMBRES"));
                usu.setApellidos(rs.getString("APELLIDOS"));
                usu.setEmail(rs.getString("EMAIL"));
                usu.setPassword(rs.getString("PASSWORD"));
                usu.setEstado(rs.getString("ESTADO"));
                usuarios.add(usu);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return usuarios;
    }

    public void registrarUsuario(Usuario usuario) throws Exception {

        String sql;
        int rol = usuario.getRol().getId();
        String nombre = usuario.getNombres();
        String apellidos = usuario.getApellidos();
        String clave = usuario.getPassword();
        String email = usuario.getEmail();
        String estado = usuario.getEstado();
        sql = "INSERT INTO USUARIO(ROL_ID,NOMBRES,APELLIDOS,EMAIL,PASSWORD,ESTADO)"
                + " VALUES(" + rol + ",'" + nombre + "','" + apellidos + "','" + email + "','" + clave + "','" + estado + "')";
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

    public void updateUsuario(Usuario usuUp) throws Exception {
        String sql = "UPDATE USUARIO SET ROL_ID = " + usuUp.getRol().getId() + ", "
                + "NOMBRES = '" + usuUp.getNombres() + "',APELLIDOS = '" + usuUp.getApellidos() + "', "
                + "EMAIL = '" + usuUp.getEmail() + "', PASSWORD = '" + usuUp.getPassword() + "',ESTADO = '" + usuUp.getEstado() + "' WHERE ID = "+usuUp.getId();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(Usuario usuUp) throws Exception {
        String sql = "UPDATE USUARIO SET ESTADO = '" + usuUp.getEstado() + "' WHERE ID = "+usuUp.getId();
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
