package Controllers;

import Models.Marca;
import Models.Modelo;
import Models.ModeloDAO;
import Models.Serie;
import Models.SerieDAO;
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

@WebServlet(name = "Series", urlPatterns = {"/Series"})
public class SeriesController extends HttpServlet {

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
            out.println("<title>Servlet SeriesController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SeriesController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SerieDAO dao = new SerieDAO();
            List<Modelo> listModelo;
            listModelo = dao.listarModelos();
            request.setAttribute("nav", "Series");
            request.setAttribute("drop", true);
            request.setAttribute("modelos", listModelo);
            request.getRequestDispatcher("/Views/Admin/Series.jsp").forward(request, response);
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

    public void jsonResList(HttpServletResponse response, List<Serie> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SerieDAO dao = new SerieDAO();
        List<Serie> listData;
        try {
            listData = dao.listarAll();
            this.jsonResList(response, listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
            this.jsonResponse(response, resJson);
        }
    }

    private void registro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SerieDAO daor;
        Serie dtr;
        Modelo modelo;

        dtr = new Serie();
        modelo = new Modelo();

        modelo.setIdmodelo(Integer.parseInt(request.getParameter("modelo")));
        dtr.setModelo(modelo);
        dtr.setSerie(request.getParameter("serie"));
        dtr.setEstado(request.getParameter("estado"));

        daor = new SerieDAO();
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
        SerieDAO daou;
        Serie dtu;
        Modelo modelo;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        dtu = new Serie();
        dtu.setIdserie(Integer.parseInt(request.getParameter("idserie")));

        modelo = new Modelo();
        modelo.setIdmodelo(Integer.parseInt(request.getParameter("modelo")));
        dtu.setModelo(modelo);

        dtu.setSerie(request.getParameter("serie"));
        dtu.setEstado(request.getParameter("estado"));

        daou = new SerieDAO();
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
        SerieDAO daoe;
        Serie dte;
        dte = new Serie();
        dte.setIdserie(Integer.parseInt(request.getParameter("id")));
        dte.setEstado(request.getParameter("estado"));
        daoe = new SerieDAO();
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
