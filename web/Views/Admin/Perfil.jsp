<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") != null || session.getAttribute("tecnico") != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@include file="../Layout/Head.jsp" %>
        <title>Soporte COMSOFT ][ Dashboard</title>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">

            <%@include file="../Layout/Nav.jsp" %>

                <!-- Main content -->
                <section class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-3">

                                <!-- Profile Image -->
                                <div class="card card-primary card-outline">
                                    <div class="card-body box-profile">
                                        <div class="text-center">
                                            <img class="profile-user-img img-fluid img-circle"
                                                 src="assets/dist/img/user2-160x160.jpg"
                                                 alt="User profile picture">
                                        </div>

                                        <h3 class="profile-username text-center">${usuario.nombres} ${usuario.apellidos}</h3>

                                        <p class="text-muted text-center">[${usuario.rol.nombre}]</p>

                                        <ul class="list-group list-group-unbordered mb-3">
                                            <li class="list-group-item">
                                                <b>Total de Servicios</b> <a class="float-right">0</a>
                                            </li>
                                            <li class="list-group-item">
                                                <b>Ser. Pendientes</b> <a class="float-right">0</a>
                                            </li>
                                            <li class="list-group-item">
                                                <b>Ser. Atendidos</b> <a class="float-right">0</a>
                                            </li>
                                        </ul>

                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->


                            </div>
                            <!-- /.col -->
                            <div class="col-md-9">
                                <div class="card">
                                    <div class="card-header p-2">
                                        <ul class="nav nav-pills">
                                            <li class="nav-item"><a class="nav-link active" href="#datos" data-toggle="tab">Datos Personales</a></li>
                                        </ul>
                                    </div><!-- /.card-header -->
                                    <div class="card-body">
                                        <div class="tab-content">                                        

                                            <div class="active tab-pane" id="datos">
                                                <form class="form-horizontal" id="frmPerfil" acction="Perfil" method="POST">
                                                    <input type="hidden" name="idusuario" value="${usuario.id}" class="form-control" id="idusuario" readonly>
                                                    <input type="hidden" name="estado" value="${usuario.estado}" class="form-control" id="estado" readonly>
                                                    <div class="form-group row">
                                                        <label for="nombres" class="col-sm-2 col-form-label">Nombres</label>
                                                        <div class="col-sm-10">
                                                            <input type="text" name="nombres" value="${usuario.nombres}" class="form-control" id="nombres" placeholder="Nombres">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label for="apellidos" class="col-sm-2 col-form-label">Apellidos</label>
                                                        <div class="col-sm-10">
                                                            <input type="text" name="apellidos" value="${usuario.apellidos}" class="form-control" id="apellidos" placeholder="Apellidos">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label for="email" class="col-sm-2 col-form-label">Email</label>
                                                        <div class="col-sm-10">
                                                            <input type="email" name="email" value="${usuario.email}" readonly class="form-control" id="email" placeholder="Email">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label for="clave" class="col-sm-2 col-form-label">Contraseña Nueva</label>
                                                        <div class="col-sm-10">
                                                            <input type="hidden" class="form-control" id="clave1" name="clave1" value="${usuario.password}" readonly>
                                                            <input type="password" class="form-control" id="clave" name="clave" placeholder="Contraseña Nueva">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label for="rol" class="col-sm-2 col-form-label">Rol</label>
                                                        <div class="col-sm-10">
                                                            <input type="text" name="rol" value="${usuario.rol.nombre}" readonly="" class="form-control" id="rol" placeholder="Rol">
                                                        </div>
                                                    </div> 
                                                    <div class="form-group row d-none">
                                                        <label for="rol_id" class="col-sm-2 col-form-label">Rol</label>
                                                        <div class="col-sm-10">
                                                            <select class="form-control" name="rol_id" id="rol_id">
                                                                <option value="1"<c:if test="${usuario.rol.nombre == 'ADMINISTRADOR'}">selected</c:if>>ADMINISTRADOR</option>
                                                                <option value="3" <c:if test="${usuario.rol.nombre == 'TECNICO'}">selected</c:if>>TECNICO</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <div class="offset-sm-2 col-sm-10">
                                                            <button type="submit" class="btn btn-primary">Actualizar</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- /.tab-pane -->
                                        </div>
                                        <!-- /.tab-content -->
                                    </div><!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </div><!-- /.container-fluid -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <%@include file="../Layout/Footer.jsp" %>
            <script>
                var Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000
                });
            </script>
            <!-- JS Page -->
            <script src="assets/js/perfil.js" type="text/javascript"></script>
            

    </body>
</html>
<%
    } else {
        response.sendRedirect("Login");
    }
%>