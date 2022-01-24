package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.modelo.entidades.Parametro;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Conexion {

    private Connection conexion;
    private boolean transaccionIniciada;

    protected Connection getConexion() {
        return conexion;
    }

    protected void conectar(boolean wTransaccion) throws Exception {
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setServerName("localhost");
        ds.setPort(3306);
        ds.setDatabaseName("JLTE");
        conexion = ds.getConnection("root", "");

        if (wTransaccion == true) {
            this.conexion.setAutoCommit(false);
            this.transaccionIniciada = true;
        } else {
            this.conexion.setAutoCommit(true);
            this.transaccionIniciada = false;
        }
    }

    protected void cerrar(boolean wEstado) throws Exception {
        if (this.conexion != null) {
            if (this.transaccionIniciada == true) {
                try {
                    if (wEstado == true) {
                        this.conexion.commit();
                    } else {
                        this.conexion.rollback();
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                this.conexion.close();
            } catch (Exception e) {
            }
        }
        this.conexion = null;
    }

    protected void ejecutarOrden(String wSQL) throws Exception {
        Statement st;

        if (this.conexion != null) {
            st = this.conexion.createStatement();
            st.executeUpdate(wSQL);
        }
    }

    public Integer insertQueryGetId(String query) {
        Integer numero = 0;
        Integer risultato = -1;
        try {
            Statement stmt = this.conexion.createStatement();
            numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                risultato = rs.getInt(1);
            }
            rs.close();

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            risultato = -1;
        }
        return risultato;
    }

    protected ResultSet ejecutarOrdenDatos(String wSQL) throws Exception {
        Statement st;
        ResultSet rs = null;

        if (this.conexion != null) {
            st = this.conexion.createStatement();
            rs = st.executeQuery(wSQL);
        }

        return rs;
    }

    protected Object ejecutarProcedimiento(String wProcedimiento,
            List<Parametro> wParametros) throws Exception {
        CallableStatement cs;
        Object valor = null;
        String parNombre = "";

        try {
            cs = this.getConexion().prepareCall(wProcedimiento);
            if (wParametros != null) {
                for (Parametro par : wParametros) {
                    if (par.isEntrada() == true) {
                        cs.setObject(par.getNombre(), par.getValor());
                    } else {
                        parNombre = par.getNombre();
                        cs.registerOutParameter(par.getNombre(), par.getTipo());
                    }
                }
            }
            cs.executeUpdate();
            if (parNombre.length() > 0) {
                valor = cs.getObject(parNombre);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            cs = null;
        }

        return valor;
    }

    protected ResultSet ejecutarProcedimientoDatos(String wProcedimiento,
            List<Parametro> wParametros) throws Exception {
        CallableStatement cs;
        ResultSet rs = null;

        try {
            cs = this.getConexion().prepareCall(wProcedimiento);
            if (wParametros != null) {
                for (Parametro par : wParametros) {
                    if (par.isEntrada() == true) {
                        cs.setObject(par.getNombre(), par.getValor());
                    } else {
                        // parametro de salida
                    }
                }
            }
            rs = cs.executeQuery();
        } catch (Exception e) {
            throw e;
        } finally {
            cs = null;
        }

        return rs;
    }
}
