package Controllers;

import Models.Marca;
import Models.Modelo;
import Models.ModeloDAO;
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

@WebServlet(name = "Modelos", urlPatterns = {"/Modelos"})
public class ModelosController extends HttpServlet {

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
            out.println("<title>Servlet ModelosController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModelosController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ModeloDAO dao = new ModeloDAO();
            List<Marca> listMarca;
            listMarca = dao.listarMarcas();
            request.setAttribute("nav", "Modelos");
            request.setAttribute("drop", true);
            request.setAttribute("marcas", listMarca);
            request.getRequestDispatcher("/Views/Admin/Modelos.jsp").forward(request, response);
        } catch (Exception eg) {

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

    public void jsonResList(HttpServletResponse response, List<Modelo> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModeloDAO dao = new ModeloDAO();
        List<Modelo> listData;
        try {
            listData = dao.listarAll();
            this.jsonResList(response, listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModeloDAO daor;
        Modelo dtr;
        Marca marca;

        dtr = new Modelo();
        marca = new Marca();

        marca.setIdmarca(Integer.parseInt(request.getParameter("marca")));
        dtr.setMarca(marca);
        dtr.setModelo(request.getParameter("modelo"));
        dtr.setEstado(request.getParameter("estado"));

        daor = new ModeloDAO();
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
        ModeloDAO daou;
        Modelo dtu;
        Marca marca;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        dtu = new Modelo();
        dtu.setIdmodelo(Integer.parseInt(request.getParameter("idmodelo")));

        marca = new Marca();
        marca.setIdmarca(Integer.parseInt(request.getParameter("marca")));
        dtu.setMarca(marca);

        dtu.setModelo(request.getParameter("modelo"));
        dtu.setEstado(request.getParameter("estado"));

        daou = new ModeloDAO();
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
        ModeloDAO daoe;
        Modelo dte;
        dte = new Modelo();
        dte.setIdmodelo(Integer.parseInt(request.getParameter("id")));
        dte.setEstado(request.getParameter("estado"));
        daoe = new ModeloDAO();
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
