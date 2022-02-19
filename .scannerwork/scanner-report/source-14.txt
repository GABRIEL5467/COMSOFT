package com.uisrael.comsoft.controlador;

import com.uisrael.comsoft.modelo.entidades.Serie;
import com.uisrael.comsoft.modelo.entidades.Servicio;
import com.uisrael.comsoft.modelo.ServicioDAO;
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

@WebServlet(name = "Servicios", urlPatterns = {"/Servicios"})
public class ServiciosController extends HttpServlet {

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
            out.println("<title>Servlet ServiciosController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiciosController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nav", "Servicios");
        request.getRequestDispatcher("/Views/Admin/Servicios.jsp").forward(request, response);
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
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/Views/Denegado.jsp").forward(request, response);

            } catch (Exception ex) {
                System.out.println("Error" + e.getMessage());
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

    public void jsonResList(HttpServletResponse response, List<Servicio> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServicioDAO dao = new ServicioDAO();
        List<Servicio> listData;
        try {
            listData = dao.listarAll();
            this.jsonResList(response, listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServicioDAO daor;
        Servicio dtr;

        dtr = new Servicio();
        dtr.setServicio(request.getParameter("servicio"));
        dtr.setPrecio(Double.parseDouble(request.getParameter("precio")));
        dtr.setStock(Integer.parseInt(request.getParameter("stock")));
        dtr.setType(request.getParameter("type"));
        dtr.setEstado(request.getParameter("estado"));

        daor = new ServicioDAO();
        try {
            daor.registrarAll(dtr);
            resJson = "{\"status\":true,\"message\":\"Registrado Correctamente.\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            request.setAttribute("usuario", daor);
            resJson = "{\"status\":false,\"message\":\"Nose puedo registar " + e.getMessage() + ".\"}";
            this.jsonResponse(response, resJson);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServicioDAO daou;
        Servicio dtu;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        dtu = new Servicio();
        dtu.setIdservicio(Integer.parseInt(request.getParameter("idservicio")));
        dtu.setServicio(request.getParameter("servicio"));
        dtu.setPrecio(Double.parseDouble(request.getParameter("precio")));
        dtu.setStock(Integer.parseInt(request.getParameter("stock")));
        dtu.setType(request.getParameter("type"));
        dtu.setEstado(request.getParameter("estado"));

        daou = new ServicioDAO();
        try {
            daou.updateAll(dtu);
            resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\",\"data\":\"" + request.getParameter("clave") + "\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            resJson = "{\"status\":false,\"message\":\"No se pudo actualizar" + e.getMessage() + ".\"}";
            this.jsonResponse(response, resJson);
        }

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServicioDAO daoe;
        Servicio dte;
        dte = new Servicio();
        dte.setIdservicio(Integer.parseInt(request.getParameter("id")));
        dte.setEstado(request.getParameter("estado"));
        daoe = new ServicioDAO();
        try {
            daoe.updateEstado(dte);
            resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo actualizar." + e.getMessage() + "\"}";
            this.jsonResponse(response, resJson);
        }
    }

}
