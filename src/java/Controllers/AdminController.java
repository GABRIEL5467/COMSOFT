package Controllers;

import Models.AdminDAO;
import Models.Cliente;
import Models.Mantenimiento;
import Models.MantenimientoDAO;
import Models.Usuario;
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

@WebServlet(name = "Admin", urlPatterns = {"/Admin"})
public class AdminController extends HttpServlet {

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
            out.println("<title>Servlet AdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminDAO dao = new AdminDAO();
            MantenimientoDAO daos = new MantenimientoDAO();
            int tCliente = 0;
            tCliente = dao.listarClientes();
            request.setAttribute("cliente", tCliente);

            int tMTaller = 0;
            tMTaller = dao.listarMTaller();
            request.setAttribute("mtaller", tMTaller);

            int tMante = 0;
            tMante = dao.listarMante();
            request.setAttribute("mante", tMante);

            int servi = 0;
            servi = dao.listarServi();
            request.setAttribute("servi", servi);

            List<Usuario> listUs;
            listUs = daos.listarUsurios();
            request.setAttribute("tecnicos", listUs);

            request.setAttribute("nav", "Dashboard");
            request.getRequestDispatcher("/Views/Admin/Admin.jsp").forward(request, response);
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
                    case "rtecnico":
                        rTecnico(request, response);
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

    private void rTecnico(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int mdl = Integer.parseInt(request.getParameter("data"));
        AdminDAO daol = new AdminDAO();
        List<Mantenimiento> listData;
        try {
            listData = daol.listarMante(mdl);
            this.jsonResListServDetalle(response, listData);
            System.out.println(listData);

        } catch (Exception e) {
            resJson = "{\"status\":true,\"message\":\"No se pudo listar." + e.getMessage() + "\",}";
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

    public void jsonResListServDetalle(HttpServletResponse response, List<Mantenimiento> listDatos) throws Exception {
        PrintWriter pwl = response.getWriter();
        pwl.println(gson.toJson(listDatos));
        pwl.close();
    }

}
