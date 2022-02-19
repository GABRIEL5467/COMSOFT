package com.uisrael.comsoft.controlador;

import com.uisrael.comsoft.modelo.entidades.Cliente;
import com.uisrael.comsoft.modelo.ClienteDAO;
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

@WebServlet(name = "Clientes", urlPatterns = {"/Clientes"})
public class ClientesController extends HttpServlet {

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
            out.println("<title>Servlet ClientesController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientesController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nav", "Clientes");
        request.getRequestDispatcher("/Views/Admin/Clientes.jsp").forward(request, response);
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

    public void jsonResList(HttpServletResponse response, List<Cliente> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> listData;
        try {
            listData = dao.listarAll();
            this.jsonResList(response, listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",\"data\":\"" + request.getParameter("idusuario") + "\"}";
            this.jsonResponse(response, resJson);
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClienteDAO daor;
        Cliente dtr;

        dtr = new Cliente();
        dtr.setDocumento(request.getParameter("documento"));
        dtr.setNombresApellidos(request.getParameter("nombresapellidos"));
        dtr.setEmail(request.getParameter("email"));
        dtr.setCel(request.getParameter("cel"));
        dtr.setEstado(request.getParameter("estado"));

        daor = new ClienteDAO();
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
        ClienteDAO daou;
        Cliente dtu;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        dtu = new Cliente();
        dtu.setIdcliente(Integer.parseInt(request.getParameter("idcliente")));
        dtu.setDocumento(request.getParameter("documento"));
        dtu.setNombresApellidos(request.getParameter("nombresapellidos"));
        dtu.setEmail(request.getParameter("email"));
        dtu.setCel(request.getParameter("cel"));
        dtu.setEstado(request.getParameter("estado"));

        daou = new ClienteDAO();
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
        ClienteDAO daoe;
        Cliente dte;
        dte = new Cliente();
        dte.setIdcliente(Integer.parseInt(request.getParameter("id")));
        dte.setEstado(request.getParameter("estado"));
        daoe = new ClienteDAO();
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
