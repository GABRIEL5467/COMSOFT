package com.uisrael.comsoft.controlador;

import com.uisrael.comsoft.modelo.entidades.Cliente;
import com.uisrael.comsoft.modelo.entidades.DMantenimiento;
import com.uisrael.comsoft.modelo.entidades.Mantenimiento;
import com.uisrael.comsoft.modelo.MantenimientoDAO;
import com.uisrael.comsoft.modelo.entidades.Marca;
import com.uisrael.comsoft.modelo.entidades.Modelo;
import com.uisrael.comsoft.modelo.entidades.Serie;
import com.uisrael.comsoft.modelo.entidades.Servicio;
import com.uisrael.comsoft.modelo.entidades.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Mantenimientos", urlPatterns = {"/Mantenimientos"})
public class MantenimientosController extends HttpServlet {

    Gson gson = new Gson();
    String resJson;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MantenimientosController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MantenimientosController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MantenimientoDAO dao = new MantenimientoDAO();
            List<Cliente> listCl;
            listCl = dao.listarClientes();
            request.setAttribute("clientes", listCl);

            List<Usuario> listUs;
            listUs = dao.listarUsurios();
            request.setAttribute("tecnicos", listUs);

            List<Marca> listMc;
            listMc = dao.listarMarcas();
            request.setAttribute("marcas", listMc);

            List<Servicio> listSv;
            listSv = dao.listarServicios();
            request.setAttribute("servicios", listSv);

            request.setAttribute("nav", "Mantenimientos");
            request.getRequestDispatcher("/Views/Admin/Mantenimientos.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "listar":
                        listar(request, response);
                        break;
                    case "registro":
                        registro(request, response);
                        break;
                    case "gModelo":
                        gModelo(request, response);
                        break;
                    case "gSerie":
                        gSerie(request, response);
                        break;
                    case "gSerDetalle":
                        ServDetalle(request, response);
                        break;
                    case "update":
                        update(request, response);
                        break;
                    case "eliminar":
                        delete(request, response);
                        break;
                    case "estado":
                        delete(request, response);
                        break;
                }
            } else {
                response.sendRedirect("/Admin");
            }
        } catch (Exception e) {
            System.out.println("Error1: " + e.getMessage());
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/Views/Denegado.jsp").forward(request, response);

            } catch (Exception ex) {
                System.out.println("Error2: " + ex.getMessage());
            }
        }
    }

    public void jsonResponse(HttpServletResponse response, String json) throws Exception {

        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        PrintWriter pwj = response.getWriter();
        pwj.println(gson.toJson(jsonObj));
        pwj.close();

    }

    public void jsonResList(HttpServletResponse response, List<Mantenimiento> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    public void jsonResListModelo(HttpServletResponse response, List<Modelo> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    public void jsonResListSerie(HttpServletResponse response, List<Serie> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    public void jsonResListServDetalle(HttpServletResponse response, List<DMantenimiento> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MantenimientoDAO daol = new MantenimientoDAO();
        List<Mantenimiento> listData;
        try {
            listData = daol.listarAll();
            this.jsonResList(response, listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] servicio = request.getParameterValues("servicio[]");
        String[] precio = request.getParameterValues("precio[]");
        String[] cantidad = request.getParameterValues("cantidad[]");
        String[] total = request.getParameterValues("total[]");
        String estado = "Activo";

        int mante = 0;

        MantenimientoDAO daor;
        Mantenimiento dtr;

        Cliente cl;
        Usuario us;
        Marca mc;
        Modelo ml;
        Serie se;

        dtr = new Mantenimiento();

        cl = new Cliente();
        cl.setIdcliente(Integer.parseInt(request.getParameter("cliente")));
        dtr.setCliente(cl);

        us = new Usuario();
        us.setId(Integer.parseInt(request.getParameter("tecnico")));
        dtr.setUsuario(us);

        dtr.setEquipo(request.getParameter("equipo"));

        mc = new Marca();
        mc.setIdmarca(Integer.parseInt(request.getParameter("marca")));
        dtr.setMarca(mc);

        ml = new Modelo();
        ml.setIdmodelo(Integer.parseInt(request.getParameter("modelo")));
        dtr.setModelo(ml);

        se = new Serie();
        se.setIdserie(Integer.parseInt(request.getParameter("serie")));
        dtr.setSerie(se);

        dtr.setDiagnostico(request.getParameter("diagnostico"));
        dtr.setFalla(request.getParameter("falla"));
        dtr.setSolucion(request.getParameter("solucion"));
        dtr.setDescuento(Double.parseDouble(request.getParameter("descuento")));
        dtr.setTotal(Double.parseDouble(request.getParameter("total")));
        dtr.setEstado(request.getParameter("estado"));

        daor = new MantenimientoDAO();
        try {
            mante = daor.registrarAll(dtr);
            if (servicio != null) {
                for (int i = 0; i < servicio.length; i++) {
                    System.out.println(servicio[i] + "<- Servicios");
                    daor.registarDetalles(mante, Integer.parseInt(servicio[i]), Double.parseDouble(precio[i]), Integer.parseInt(cantidad[i]), Double.parseDouble(total[i]), estado);
                }
            }
            /*daor.registarDetalles();*/
            resJson = "{\"status\":true,\"message\":\"Registrado Correctamente.\",\"data\":\"" + mante + "\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            request.setAttribute("usuario", daor);
            resJson = "{\"status\":false,\"message\":\"No se puedo registar " + e.getMessage() + ".\"}";
            this.jsonResponse(response, resJson);
        }
    }

    private void gModelo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int mdl = Integer.parseInt(request.getParameter("data"));
        MantenimientoDAO daol = new MantenimientoDAO();
        List<Modelo> listData;
        try {
            listData = daol.listarModelos(mdl);
            this.jsonResListModelo(response, listData);
            System.out.println(listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void gSerie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int mdl = Integer.parseInt(request.getParameter("data"));
        MantenimientoDAO daol = new MantenimientoDAO();
        List<Serie> listData;
        try {
            listData = daol.listarSeries(mdl);
            this.jsonResListSerie(response, listData);
            System.out.println(listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void ServDetalle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int mdl = Integer.parseInt(request.getParameter("data"));
        MantenimientoDAO daol = new MantenimientoDAO();
        List<DMantenimiento> listData;
        try {
            listData = daol.listarSerDetalle(mdl);
            this.jsonResListServDetalle(response, listData);
            System.out.println(listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String[] servicio = request.getParameterValues("servicio[]");
        String[] precio = request.getParameterValues("precio[]");
        String[] cantidad = request.getParameterValues("cantidad[]");
        String[] total = request.getParameterValues("total[]");
        String estado = "Activo";

        MantenimientoDAO daou;
        Mantenimiento dtu;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Cliente cl;
        Usuario us;
        Marca mc;
        Modelo ml;
        Serie se;

        dtu = new Mantenimiento();
        dtu.setIdmante(Integer.parseInt(request.getParameter("idmante")));

        cl = new Cliente();
        cl.setIdcliente(Integer.parseInt(request.getParameter("cliente")));
        dtu.setCliente(cl);

        us = new Usuario();
        us.setId(Integer.parseInt(request.getParameter("tecnico")));
        dtu.setUsuario(us);

        dtu.setEquipo(request.getParameter("equipo"));

        mc = new Marca();
        mc.setIdmarca(Integer.parseInt(request.getParameter("marca")));
        dtu.setMarca(mc);

        ml = new Modelo();
        ml.setIdmodelo(Integer.parseInt(request.getParameter("modelo")));
        dtu.setModelo(ml);

        se = new Serie();
        se.setIdserie(Integer.parseInt(request.getParameter("serie")));
        dtu.setSerie(se);

        dtu.setDiagnostico(request.getParameter("diagnostico"));
        dtu.setFalla(request.getParameter("falla"));
        dtu.setSolucion(request.getParameter("solucion"));
        dtu.setDescuento(Double.parseDouble(request.getParameter("descuento")));
        dtu.setTotal(Double.parseDouble(request.getParameter("total")));
        dtu.setEstado(request.getParameter("estado"));

        daou = new MantenimientoDAO();
        try {
            daou.updateAll(dtu);
            daou.delDetalle(Integer.parseInt(request.getParameter("idmante")));
            if (servicio != null) {
                for (int i = 0; i < servicio.length; i++) {
                    System.out.println(servicio[i] + "<- Servicios");
                    daou.registarDetalles(Integer.parseInt(request.getParameter("idmante")), Integer.parseInt(servicio[i]), Double.parseDouble(precio[i]), Integer.parseInt(cantidad[i]), Double.parseDouble(total[i]), estado);
                }
            }
            resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\",\"data\":\"" + request.getParameter("clave") + "\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            resJson = "{\"status\":false,\"message\":\"No se pudo actualizar" + e.getMessage() + ".\"}";
            this.jsonResponse(response, resJson);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MantenimientoDAO daoe;
        Mantenimiento dte;
        /*dte = new Servicio();
        dte.setIdservicio(Integer.parseInt(request.getParameter("id")));
        dte.setEstado(request.getParameter("estado"));*/
        daoe = new MantenimientoDAO();
        try {
            daoe.updateEstado(request.getParameter("estado"),Integer.parseInt(request.getParameter("id")));
            resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo actualizar." + e.getMessage() + "\"}";
            this.jsonResponse(response, resJson);
        }
    }

}
