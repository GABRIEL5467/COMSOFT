package com.uisrael.comsoft.controlador;

import com.uisrael.comsoft.modelo.entidades.Rol;
import com.uisrael.comsoft.modelo.entidades.Usuario;
import com.uisrael.comsoft.modelo.UsuarioDAO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Perfil", urlPatterns = {"/Perfil"})
public class PerfilController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Perfil</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Perfil at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nav", "Perfil");
        request.getRequestDispatcher("/Views/Admin/Perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson gson = new Gson();
        UsuarioDAO daoUsu;
        Usuario usus = null;
        Rol rol;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if (request.getParameter("idusuario") != null) {

            usus = new Usuario();
            usus.setId(Integer.parseInt(request.getParameter("idusuario")));
            rol = new Rol();
            rol.setId(Integer.parseInt(request.getParameter("rol_id")));
            usus.setRol(rol);
            usus.setNombres(request.getParameter("nombres"));
            usus.setApellidos(request.getParameter("apellidos"));
            usus.setEmail(request.getParameter("email"));
            if ("".equals(request.getParameter("clave"))) {
                usus.setPassword(request.getParameter("clave1"));
            } else {
                usus.setPassword(request.getParameter("clave"));
            }
            usus.setEstado(request.getParameter("estado"));

            daoUsu = new UsuarioDAO();
            try {
                HttpSession sesion = request.getSession();
                daoUsu.updateUsuario(usus);
                usus = daoUsu.Login(usus);
                sesion.setAttribute("usuario", usus);
                String resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\",\"data\":\"" + request.getParameter("clave") + "\"}";
                JsonElement element = gson.fromJson(resJson, JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();
                PrintWriter pw = response.getWriter();
                pw.println(gson.toJson(jsonObj));
                pw.close();
            } catch (Exception e) {
                String resJson = "{\"status\":false,\"message\":\"No se pudo actualizar el usuario" + e.getMessage() + ".\"}";

                JsonElement element = gson.fromJson(resJson, JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();
                PrintWriter pw = response.getWriter();
                pw.println(gson.toJson(jsonObj));
                pw.close();

            }

        } else {
            String resJson = "{\"status\":false,\"message\":\"Algo salio mal, vuelva a intentarlo.\"}";
            JsonElement element = gson.fromJson(resJson, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            PrintWriter pw = response.getWriter();
            pw.println(gson.toJson(jsonObj));
            pw.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
