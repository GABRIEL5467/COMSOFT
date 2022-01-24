package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO extends Conexion {

    public Integer listarClientes() throws Exception {
        ResultSet rs;
        int tCliente = 0;
        String sql = "SELECT COUNT(*) AS TOTAL FROM CLIENTES WHERE ESTADO = 'Activo'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                tCliente = rs.getInt("TOTAL");
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return tCliente;
    }

    public Integer listarMTaller() throws Exception {
        ResultSet rs;
        int tCantidad = 0;
        String sql = "SELECT COUNT(*) AS TOTAL FROM MANTENIMIENTO WHERE ESTADO = 'Taller'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                tCantidad = rs.getInt("TOTAL");
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return tCantidad;
    }

    public Integer listarMante() throws Exception {
        ResultSet rs;
        int tCantidad = 0;
        String sql = "SELECT COUNT(*) AS TOTAL FROM MANTENIMIENTO WHERE ESTADO != 'Cancelar'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                tCantidad = rs.getInt("TOTAL");
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return tCantidad;
    }

    public Integer listarServi() throws Exception {
        ResultSet rs;
        int tCantidad = 0;
        String sql = "SELECT COUNT(*) AS TOTAL FROM SERVICIO WHERE ESTADO != 'Eliminado'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                tCantidad = rs.getInt("TOTAL");
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return tCantidad;
    }

    public List<Mantenimiento> listarMante(int id) throws Exception {
        List<Mantenimiento> listAll;
        Mantenimiento dtl;
        ResultSet rs;
        String sql = "SELECT MT.ID,MT.CLIENTE_ID,MT.USUARIO_ID,MT.EQUIPO,MT.MARCA_ID,MT.MODELO_ID,MT.SERIE_ID,CONCAT(CL.NOMBRESAPELLIDOS,' (',CL.DOCUMENTO,')') AS CLIENTE,CONCAT(US.NOMBRES,' ',US.APELLIDOS) AS TECNICO,MC.MARCA,ML.MODELO,SE.SERIE,MT.DIAGNOSTICO,MT.FALLA,MT.SOLUCION,MT.DESCUENTO,MT.TOTAL,MT.FECHA,MT.F_ENTREGA,MT.ESTADO FROM MANTENIMIENTO MT INNER JOIN CLIENTES CL ON CL.ID=MT.CLIENTE_ID INNER JOIN USUARIO US ON US.ID=MT.USUARIO_ID INNER JOIN MARCA MC ON MC.ID=MT.MARCA_ID INNER JOIN MODELO ML ON ML.ID=MODELO_ID INNER JOIN SERIE SE ON SE.ID=SERIE_ID WHERE MT.ESTADO != 'Cancelar' AND MT.USUARIO_ID = " + id;

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {

                dtl = new Mantenimiento();
                dtl.setIdmante(rs.getInt("ID"));

                dtl.setCliente(new Cliente());
                dtl.getCliente().setIdcliente(Integer.parseInt(rs.getString("CLIENTE_ID")));
                dtl.getCliente().setNombresApellidos(rs.getString("CLIENTE"));

                dtl.setUsuario(new Usuario());
                dtl.getUsuario().setId(Integer.parseInt(rs.getString("USUARIO_ID")));
                dtl.getUsuario().setNombres(rs.getString("TECNICO"));

                dtl.setEquipo(rs.getString("EQUIPO"));

                dtl.setMarca(new Marca());
                dtl.getMarca().setIdmarca(Integer.parseInt(rs.getString("MARCA_ID")));
                dtl.getMarca().setMarca(rs.getString("MARCA"));

                dtl.setModelo(new Modelo());
                dtl.getModelo().setIdmodelo(Integer.parseInt(rs.getString("MODELO_ID")));
                dtl.getModelo().setModelo(rs.getString("MODELO"));

                dtl.setSerie(new Serie());
                dtl.getSerie().setIdserie(Integer.parseInt(rs.getString("SERIE_ID")));
                dtl.getSerie().setSerie(rs.getString("SERIE"));

                dtl.setDiagnostico(rs.getString("DIAGNOSTICO"));
                dtl.setFalla(rs.getString("FALLA"));
                dtl.setSolucion(rs.getString("SOLUCION"));
                dtl.setDescuento(rs.getDouble("DESCUENTO"));
                dtl.setTotal(rs.getDouble("TOTAL"));
                dtl.setFecha(rs.getString("FECHA"));
                dtl.setF_entrega(rs.getString("F_ENTREGA"));
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
   
}
