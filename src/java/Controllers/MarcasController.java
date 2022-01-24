/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.Marca;
import Models.MarcaDAO;
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

/**
 *
 * @author RESISTEMASPERU
 */
@WebServlet(name = "Marcas", urlPatterns = {"/Marcas"})
public class MarcasController extends HttpServlet {

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
            out.println("<title>Servlet MarcasController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarcasController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nav", "Marcas");
        request.setAttribute("drop", true);
        request.getRequestDispatcher("/Views/Admin/Marcas.jsp").forward(request, response);
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

    public void jsonResList(HttpServletResponse response, List<Marca> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MarcaDAO dao = new MarcaDAO();
        List<Marca> listData;
        try {
            listData = dao.listarAll();
            this.jsonResList(response, listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MarcaDAO daor;
        Marca dtr;

        dtr = new Marca();
        dtr.setMarca(request.getParameter("marca"));
        dtr.setEstado(request.getParameter("estado"));

        daor = new MarcaDAO();
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
        MarcaDAO daou;
        Marca dtu;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        dtu = new Marca();
        dtu.setIdmarca(Integer.parseInt(request.getParameter("idmarca")));
        dtu.setMarca(request.getParameter("marca"));
        dtu.setEstado(request.getParameter("estado"));

        daou = new MarcaDAO();
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
        MarcaDAO daoe;
        Marca dte;
        dte = new Marca();
        dte.setIdmarca(Integer.parseInt(request.getParameter("id")));
        dte.setEstado(request.getParameter("estado"));
        daoe = new MarcaDAO();
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
