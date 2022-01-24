package com.uisrael.comsoft.modelo;

import com.uisrael.comsoft.modelo.entidades.Usuario;
import com.uisrael.comsoft.modelo.entidades.Servicio;
import com.uisrael.comsoft.modelo.entidades.Serie;
import com.uisrael.comsoft.modelo.entidades.Modelo;
import com.uisrael.comsoft.modelo.entidades.Marca;
import com.uisrael.comsoft.modelo.entidades.Mantenimiento;
import com.uisrael.comsoft.modelo.entidades.Cliente;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO extends Conexion {

    public List<Cliente> listarClientes() throws Exception {
        List<Cliente> listAll;
        Cliente dtl;
        ResultSet rs;
        String sql = "SELECT * FROM CLIENTES WHERE ESTADO = 'Activo'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Cliente();
                dtl.setIdcliente(rs.getInt("ID"));
                dtl.setDocumento(rs.getString("DOCUMENTO"));
                dtl.setNombresApellidos(rs.getString("NOMBRESAPELLIDOS"));
                listAll.add(dtl);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return listAll;
    }

    public List<Usuario> listarUsurios() throws Exception {
        List<Usuario> listAll;
        Usuario dtl;
        ResultSet rs;
        String sql = "SELECT * FROM USUARIO WHERE ESTADO = 'Activo'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Usuario();
                dtl.setId(rs.getInt("ID"));
                dtl.setNombres(rs.getString("NOMBRES"));
                dtl.setApellidos(rs.getString("APELLIDOS"));
                listAll.add(dtl);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return listAll;
    }

    public List<Servicio> listarServicios() throws Exception {
        List<Servicio> listAll;
        Servicio dtl;
        ResultSet rs;
        String sql = "SELECT * FROM SERVICIO WHERE ESTADO = 'Activo'";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Servicio();
                dtl.setIdservicio(rs.getInt("ID"));
                dtl.setServicio(rs.getString("SERVICIO"));
                dtl.setPrecio(rs.getDouble("PRECIO"));
                dtl.setStock(rs.getInt("stock"));
                dtl.setType(rs.getString("TYPE"));
                listAll.add(dtl);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return listAll;
    }

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

    public List<Modelo> listarModelos(int ml) throws Exception {
        List<Modelo> listAll;
        Modelo dtl;
        ResultSet rs;
        String sql = "SELECT * FROM MODELO WHERE ESTADO = 'Activo' AND MARCA_ID = " + ml;
        System.out.println(sql);
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

    public List<Serie> listarSeries(int mdl) throws Exception {
        List<Serie> listAll;
        Serie dtl;
        ResultSet rs;
        String sql = "SELECT * FROM SERIE WHERE ESTADO = 'Activo' AND MODELO_ID = " + mdl;

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new Serie();
                dtl.setIdserie(rs.getInt("ID"));
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

    public List<DMantenimiento> listarSerDetalle(int mante) throws Exception {
        List<DMantenimiento> listAll;
        DMantenimiento dtl;
        ResultSet rs;
        Servicio se;
        String sql = "SELECT DM.ID,DM.MANTE_ID,SERVICIO_ID,DM.PRECIO,DM.CANTIDAD,DM.TOTAL,DM.ESTADO,SE.SERVICIO FROM DMANTENIMIENTO DM INNER JOIN SERVICIO SE ON SE.ID=DM.SERVICIO_ID WHERE DM.ESTADO = 'Activo' AND DM.MANTE_ID =" + mante;

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            listAll = new ArrayList<>();
            while (rs.next() == true) {
                dtl = new DMantenimiento();
                dtl.setIddmante(rs.getInt("ID"));

                dtl.setServicio(new Servicio());
                dtl.getServicio().setIdservicio(rs.getInt("SERVICIO_ID"));
                dtl.getServicio().setServicio(rs.getString("SERVICIO"));

                dtl.setPrecio(rs.getDouble("PRECIO"));
                dtl.setCantidad(rs.getInt("CANTIDAD"));
                dtl.setTotal(rs.getDouble("TOTAL"));
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

    public List<Mantenimiento> listarAll() throws Exception {
        List<Mantenimiento> listAll;
        Mantenimiento dtl;
        ResultSet rs;
        String sql = "SELECT MT.ID,MT.CLIENTE_ID,MT.USUARIO_ID,MT.EQUIPO,MT.MARCA_ID,MT.MODELO_ID,MT.SERIE_ID,CONCAT(CL.NOMBRESAPELLIDOS,' (',CL.DOCUMENTO,')') AS CLIENTE,CONCAT(US.NOMBRES,' ',US.APELLIDOS) AS TECNICO,MC.MARCA,ML.MODELO,SE.SERIE,MT.DIAGNOSTICO,MT.FALLA,MT.SOLUCION,MT.DESCUENTO,MT.TOTAL,MT.FECHA,MT.F_ENTREGA,MT.ESTADO FROM MANTENIMIENTO MT INNER JOIN CLIENTES CL ON CL.ID=MT.CLIENTE_ID INNER JOIN USUARIO US ON US.ID=MT.USUARIO_ID INNER JOIN MARCA MC ON MC.ID=MT.MARCA_ID INNER JOIN MODELO ML ON ML.ID=MODELO_ID INNER JOIN SERIE SE ON SE.ID=SERIE_ID";

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

    public Mantenimiento listarReporte(int id) throws Exception {
        Mantenimiento dtl;
        ResultSet rs;
        String sql = "SELECT MT.ID,MT.CLIENTE_ID,MT.USUARIO_ID,MT.EQUIPO,MT.MARCA_ID,MT.MODELO_ID,MT.SERIE_ID,CONCAT(CL.NOMBRESAPELLIDOS,' (',CL.DOCUMENTO,')') AS CLIENTE,CL.DOCUMENTO,CL.EMAIL,CL.CEL,CONCAT(US.NOMBRES,' ',US.APELLIDOS) AS TECNICO,MC.MARCA,ML.MODELO,SE.SERIE,MT.DIAGNOSTICO,MT.FALLA,MT.SOLUCION,MT.DESCUENTO,MT.TOTAL,MT.FECHA,MT.F_ENTREGA,MT.ESTADO FROM MANTENIMIENTO MT INNER JOIN CLIENTES CL ON CL.ID=MT.CLIENTE_ID INNER JOIN USUARIO US ON US.ID=MT.USUARIO_ID INNER JOIN MARCA MC ON MC.ID=MT.MARCA_ID INNER JOIN MODELO ML ON ML.ID=MODELO_ID INNER JOIN SERIE SE ON SE.ID=SERIE_ID WHERE MT.ESTADO != 'Cancelar' AND MT.ID = " + id;

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            dtl = new Mantenimiento();
            if (rs.next() == true) {

                dtl.setIdmante(rs.getInt("ID"));

                dtl.setCliente(new Cliente());
                dtl.getCliente().setIdcliente(Integer.parseInt(rs.getString("CLIENTE_ID")));
                dtl.getCliente().setNombresApellidos(rs.getString("CLIENTE"));
                dtl.getCliente().setDocumento(rs.getString("DOCUMENTO"));
                dtl.getCliente().setEmail(rs.getString("EMAIL"));
                dtl.getCliente().setCel(rs.getString("CEL"));

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
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return dtl;
    }

    public Integer registrarAll(Mantenimiento dtr) throws Exception {
        ResultSet rs;
        String sql;
        int ids = 0;

        int cliente = dtr.getCliente().getIdcliente();
        int usuario = dtr.getUsuario().getId();
        String equipo = dtr.getEquipo();
        int marca = dtr.getMarca().getIdmarca();
        int modelo = dtr.getModelo().getIdmodelo();
        int serie = dtr.getSerie().getIdserie();
        String diagnostico = dtr.getDiagnostico();
        String falla = dtr.getFalla();
        String solucion = dtr.getSolucion();
        Double descuento = dtr.getDescuento();
        Double total = dtr.getTotal();
        String fecha = dtr.getFecha();
        String estado = dtr.getEstado();
        sql = "INSERT INTO MANTENIMIENTO(CLIENTE_ID,USUARIO_ID,EQUIPO,MARCA_ID,MODELO_ID,SERIE_ID,DIAGNOSTICO,FALLA,SOLUCION,DESCUENTO,TOTAL,ESTADO)"
                + " VALUES(" + cliente + "," + usuario + ",'" + equipo + "'," + marca + "," + modelo + "," + serie + ",'" + diagnostico + "','" + falla + "','" + solucion + "'," + descuento + "," + total + ",'" + estado + "')";
        try {
            this.conectar(false);
            ids = this.insertQueryGetId(sql);
            System.out.println(ids + " <- Sera el id");
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return ids;
    }

    public void registarDetalles(int mante, int servi, double precio, int cantidad, double total, String estado) throws Exception {
        String sql;
        sql = "INSERT INTO DMANTENIMIENTO(MANTE_ID,SERVICIO_ID,PRECIO,CANTIDAD,TOTAL,ESTADO)"
                + " VALUES(" + mante + "," + servi + "," + precio + "," + cantidad + ",'" + total + "','" + estado + "')";
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

    public void updateAll(Mantenimiento mt) throws Exception {
        String sql = "UPDATE MANTENIMIENTO SET CLIENTE_ID = " + mt.getCliente().getIdcliente() + ", USUARIO_ID = " + mt.getUsuario().getId() + ",EQUIPO = '" + mt.getEquipo() + "',MARCA_ID = " + mt.getMarca().getIdmarca() + ",MODELO_ID = " + mt.getModelo().getIdmodelo() + ",SERIE_ID = " + mt.getSerie().getIdserie() + ",DIAGNOSTICO = '" + mt.getDiagnostico() + "', FALLA = '" + mt.getFalla() + "',SOLUCION = '" + mt.getSolucion() + "',DESCUENTO = " + mt.getDescuento() + ",TOTAL = " + mt.getTotal() + " WHERE ID = " + mt.getIdmante();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void delDetalle(int mante) throws Exception {
        String sql = "DELETE FROM DMANTENIMIENTO WHERE MANTE_ID = " + mante;
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void updateEstado(String estado, int id) throws Exception {
        String sql = "UPDATE MANTENIMIENTO SET ESTADO = '" + estado + "' WHERE ID = " + id;
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
