package com.uisrael.comsoft.controlador;

import com.uisrael.comsoft.modelo.entidades.Rol;
import com.uisrael.comsoft.modelo.entidades.Usuario;
import com.uisrael.comsoft.modelo.UsuarioDAO;
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

@WebServlet(name = "Usuarios", urlPatterns = {"/Usuarios"})
public class UsuariosController extends HttpServlet {

    Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UsuariosController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsuariosController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nav", "Usuarios");
        request.getRequestDispatcher("/Views/Admin/Usuarios.jsp").forward(request, response);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usus = null;
        try {
            usus = dao.listarUsuarios();
            this.jsonResList(response, usus);

        } catch (Exception e) {
            String resJson = "{\"status\":true,\"message\":\"No se pudo listar los usuarios." + e.getMessage() + "\",\"data\":\"" + request.getParameter("idusuario") + "\"}";
            this.jsonResponse(response, resJson);
        } finally {
            dao = null;
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UsuarioDAO daoUsuario;
        Usuario usu = null;
        Rol rol;

        usu = new Usuario();
        usu.setNombres(request.getParameter("nombres"));
        usu.setApellidos(request.getParameter("apellidos"));
        usu.setEmail(request.getParameter("email"));
        usu.setPassword(request.getParameter("clave1"));
        rol = new Rol();
        rol.setId(Integer.parseInt(request.getParameter("rol_id")));
        usu.setRol(rol);
        usu.setEstado(request.getParameter("estado"));
        daoUsuario = new UsuarioDAO();
        try {
            daoUsuario.registrarUsuario(usu);
            String resJson = "{\"status\":true,\"message\":\"Registrado Correctamente.\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            request.setAttribute("usuario", usu);
            String resJson = "{\"status\":false,\"message\":\"Nose puedo registar el usuario " + e.getMessage() + ".\"}";
            this.jsonResponse(response, resJson);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                daoUsu.updateUsuario(usus);
                String resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\",\"data\":\"" + request.getParameter("clave") + "\"}";
                this.jsonResponse(response, resJson);
            } catch (Exception e) {
                String resJson = "{\"status\":false,\"message\":\"No se pudo actualizar el usuario" + e.getMessage() + ".\"}";
                this.jsonResponse(response, resJson);
            }

        } else {
            String resJson = "{\"status\":false,\"message\":\"Algo salio mal, vuelva a intentarlo.\"}";
            this.jsonResponse(response, resJson);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDAO daoUsu;
        Usuario usus = null;
        usus = new Usuario();
        usus.setId(Integer.parseInt(request.getParameter("usuarioid")));
        usus.setEstado(request.getParameter("estado"));
        daoUsu = new UsuarioDAO();
        try {
            daoUsu.updateEstado(usus);
            String resJson = "{\"status\":true,\"message\":\"Actualizado Correctamente.\"}";
            this.jsonResponse(response, resJson);
        } catch (Exception e) {
            String resJson = "{\"status\":true,\"message\":\"No se pudo actualizar el usuario." + e.getMessage() + "\"}";
            this.jsonResponse(response, resJson);
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/actualizarUsuario.jsp").forward(request, response);
        } catch (Exception exs) {
            String resJson = "{\"status\":true,\"message\":\"No se pudo actualizar el usuario." + exs.getMessage() + "\"}";
            this.jsonResponse(response, resJson);
        }
    }

    public void jsonResponse(HttpServletResponse response, String json) throws Exception {

        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        PrintWriter pwj = response.getWriter();
        pwj.println(gson.toJson(jsonObj));
        pwj.close();

    }

    public void jsonResList(HttpServletResponse response, List<Usuario> listUsuario) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listUsuario));
        pwl.close();
    }
}
