<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@include file="../Layout/Head.jsp" %>
        <title>Soporte COMSOFT ][ ${nav}</title>
        <script>
            if ("${usuario.rol.nombre}" === "TECNICO") {
                window.location.href = "Denegado";
            }
        </script>
    </head>
    <body class="hold-transition sidebar-mini" x-data="go.dtUsuario()">
        <div class="wrapper" >

            <%@include file="../Layout/Nav.jsp" %>

            <div  class="content text-right">
                <button @click="showModal('modal'),clear()" class="btn btn-success mb-2"><i class="fas fa-plus"></i> Nuevo</button>
            </div>

            <!-- Main content -->
            <div class="content">
                <!-- Default box -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Lista de ${nav}</h3>

                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse" title="Collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-tool" data-card-widget="remove" title="Remove">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                    </div>
                    <div class="card-body p-2">
                        <div class="responsive">
                            <table class="table table-striped projects" id="tbData">
                                <thead>
                                    <tr>
                                        <th>Nombres y Apellidos</th>
                                        <th>Email</th>
                                        <th>Rol</th>
                                        <th class="text-center">Estado</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <!-- /.card-body -->
                </div>
                <!-- /.card -->
            </div>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <%@include file="../Layout/Footer.jsp" %>

        <div class="modal fade" id="modal" data-backdrop="static">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="mtitle">Nuevo Usuario</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="frmData" autocomplete="off" acction="Usuarios" method="POST">
                            <input type="hidden"  name="accion" value="registro" class="form-control" id="accion" readonly>
                            <input type="hidden" x-model="idusuario" name="idusuario" class="form-control" id="idusuario" readonly>
                            <div class="form-group row">
                                <label for="nombres" class="col-sm-2 col-form-label">Nombres</label>
                                <div class="col-sm-10">
                                    <input type="text" x-model="nombres" name="nombres" class="form-control" id="nombres" placeholder="Nombres">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="apellidos" class="col-sm-2 col-form-label">Apellidos</label>
                                <div class="col-sm-10">
                                    <input type="text" x-model="apellidos" name="apellidos" class="form-control" id="apellidos" placeholder="Apellidos">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="email" class="col-sm-2 col-form-label">Email</label>
                                <div class="col-sm-10">
                                    <input type="email" x-model="email" name="email" class="form-control" id="email" placeholder="Email">
                                </div>
                            </div>
                            <div class="form-group row" id="ps">
                                <label for="clave" class="col-sm-2 col-form-label">Contraseña Nueva</label>
                                <div class="col-sm-10">
                                    <input type="password" x-model="clave1" class="form-control" id="clave1" name="clave1"  placeholder="Contraseña">
                                    <input type="password" x-model="clave" class="form-control" id="clave" name="clave" placeholder="Contraseña Nueva">
                                </div>
                            </div>                            
                            <div class="form-group row">
                                <label for="rol_id" class="col-sm-2 col-form-label">Rol</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="rol_id" id="rol_id" x-model="rol">
                                        <option value="1">ADMINISTRADOR</option>
                                        <option value="3" selected="">TECNICO</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="estado" class="col-sm-2 col-form-label">Estado</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="estado" id="estado" x-model="estado">
                                        <option value="Activo" selected>Activo</option>
                                        <option value="Inactivo">Inactivo</option>
                                    </select>
                                </div>
                            </div>  

                    </div>
                    <div class="modal-footer justify-content-between">
                        <button type="button" @click="clear()" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-success" id="mtexto">Guardar</button>
                    </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->


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