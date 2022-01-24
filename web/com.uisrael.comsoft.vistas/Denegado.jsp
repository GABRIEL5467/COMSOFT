<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@include file="Layout/Head.jsp" %>
        <title>Soporte COMSOFT ][ ${nav}</title>
    </head>
    <body class="hold-transition sidebar-mini" x-data="go.dtUsuario()">
        <div class="wrapper" >

            <%@include file="Layout/Nav.jsp" %>

            <!-- Main content -->
            <section class="content">
                <div class="error-page">
                    <h2 class="headline text-danger">500</h2>

                    <div class="error-content">
                        <h3><i class="fas fa-exclamation-triangle text-danger"></i> Oops! Lo sentimos Denegado.</h3>

                        <p>
                            No tienes permisos necesarios.
                            para este modulo <br><a href="Admin">volver a Dashboard</a>.
                        </p>
                    </div>
                </div>
                <!-- /.error-page -->

            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <%@include file="Layout/Footer.jsp" %>

        <script>
            var Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000
            });
        </script>
        <!-- JS Page -->
        <script src="assets/js/usuarios.js" type="text/javascript"></script>        

    </body>
</html>
<%
    } else {
        response.sendRedirect("Login");
    }
%>