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
    </head>
    <body class="hold-transition sidebar-mini" x-data="dtGen">
        <div class="wrapper" >

            <%@include file="../Layout/Nav.jsp" %>

            <div  class="content text-right">
                <button @click="clear(),showModal('modal')" class="btn btn-success mb-2"><i class="fas fa-plus"></i> Nuevo</button>
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
                                        <th>Marca</th>
                                        <th>Modelo</th>
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
                        <form class="row" id="frmData" autocomplete="off" acction="Modelos" method="POST">
                            <input type="hidden"  name="accion" value="registro" class="form-control" id="accion" readonly>
                            <input type="hidden" name="idmodelo" class="form-control" id="idmodelo" readonly>
                            <div class="form-group col-12 col-md-6">
                                <label for="modelo" class="col-form-label">Modelo</label>                                
                                <input type="text" name="modelo" class="form-control" id="modelo">                                
                            </div>
                            <div class="form-group col-12 col-md-6">
                                <label for="marca" class="col-form-label">Marca</label>
                                <select class="form-control" name="marca" id="marca">
                                    <option value="" selected>--Seleccione--</option>
                                    <c:forEach items="${marcas}" var="marca">
                                        <option value="${marca.idmarca}">${marca.marca}</option>
                                    </c:forEach>                                    
                                </select>
                            </div>
                            <div class="form-group col-12 col-md-6">
                                <label for="estado" class="col-form-label">Estado</label>
                                <select class="form-control" name="estado" id="estado">
                                    <option value="Activo" selected>Activo</option>
                                    <option value="Inactivo">Inactivo</option>
                                </select>
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
        <script src="assets/js/modelo.js" type="text/javascript"></script>        

    </body>
</html>
<%
    } else {
        response.sendRedirect("Login");
    }
%>