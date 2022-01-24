package com.uisrael.comsoft.controlador;

import com.uisrael.comsoft.modelo.DMantenimiento;
import com.uisrael.comsoft.modelo.entidades.Mantenimiento;
import com.uisrael.comsoft.modelo.MantenimientoDAO;
import com.itextpdf.html2pdf.HtmlConverter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Orden", urlPatterns = {"/Orden"})
public class OrdenController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrdenController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrdenController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("id") != null) {
            int ids = Integer.parseInt(request.getParameter("id"));
            try {
                MantenimientoDAO dao = new MantenimientoDAO();
                Mantenimiento mante;
                mante = new Mantenimiento();
                /*listCl = dao.listarReporte(Integer.parseInt(request.getParameter("id")));*/
                mante = dao.listarReporte(ids);
                request.setAttribute("orden", mante);

                MantenimientoDAO daol = new MantenimientoDAO();
                List<DMantenimiento> listData;
                try {
                    listData = daol.listarSerDetalle(ids);
                    request.setAttribute("servicios", listData);
                    System.out.println(listData);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if ("Entregado".equalsIgnoreCase(mante.getEstado())) {
                    request.setAttribute("nav", "La orden que busca ya esta Entregado");
                    request.getRequestDispatcher("Inicio").forward(request, response);
                } else {
                    request.setAttribute("nav", "Orden -" + mante.getCliente().getNombresApellidos());
                    request.getRequestDispatcher("/Views/Admin/Orden.jsp").forward(request, response);                    
                }

            } catch (Exception e) {
                request.setAttribute("nav", "La orden no existe");
                request.getRequestDispatcher("Inicio").forward(request, response);
            }
        } else {
            request.setAttribute("nav", "La orden no existe");
            request.getRequestDispatcher("Inicio").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
