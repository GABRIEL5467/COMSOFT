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
        <style>
            .selected{
                color:white;
                background-color: #dc3545;
            }
        </style>
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
                                        <th>Cliente</th>
                                        <th>Tecnico</th>
                                        <th>Equipo</th>
                                        <th>Diag.</th>
                                        <th>Des.</th>
                                        <th>Total</th>
                                        <th>Fecha</th>
                                        <!-- <th>F. Entrega</th> -->
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
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="mtitle">Nuevo Usuario</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="row" id="frmData" autocomplete="off" acction="Mantenimientos" method="POST">
                            <div class="col-12 col-md-6">
                                <div class="row">
                                    <input type="hidden"  name="accion" value="registro" class="form-control" id="accion" readonly>
                                    <input type="hidden" name="idmante" class="form-control" id="idmante" readonly>
                                    <input type="hidden" name="estado" class="form-control" id="estado" value="Taller" >                                    
                                    <div class="form-group col-12 col-md-6">
                                        <label for="cliente" class="col-form-label">Cliente</label>
                                        <select class="form-control" name="cliente" id="cliente">
                                            <option value="" selected>--Seleccione--</option>
                                            <c:forEach items="${clientes}" var="cliente">
                                                <option value="${cliente.idcliente}">${cliente.nombresApellidos}</option>
                                            </c:forEach>                                    
                                        </select>
                                    </div>
                                    <div class="form-group col-12 col-md-6">
                                        <label for="tecnico" class="col-form-label">Tecnico</label>
                                        <c:if test="${usuario.rol.nombre == 'TECNICO'}">
                                            <input type="hidden" name="tecnico" value="${usuario.id}" class="form-control" id="tecnico">
                                            <input class="form-control" value="${usuario.nombres} ${usuario.apellidos}" readonly disabled>
                                        </c:if>
                                        <c:if test="${usuario.rol.nombre == 'ADMINISTRADOR'}">
                                            <select class="form-control" name="tecnico" id="tecnico">
                                                <option value="" selected>--Seleccione--</option>
                                                <c:forEach items="${tecnicos}" var="tecnico">
                                                    <option value="${tecnico.id}">${tecnico.nombres} ${tecnico.apellidos}</option>
                                                </c:forEach>                                    
                                            </select>
                                        </c:if>
                                    </div>
                                    <div class="form-group col-12 col-md-4">
                                        <label for="equipo" class="col-form-label">Equipo</label>
                                        <select class="form-control" name="equipo" id="equipo">
                                            <option value="" selected>--Seleccione--</option>
                                            <option value="Laptop" >Laptop</option>
                                            <option value="Pc" >Pc</option>
                                            <option value="Otros" >Otros</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-12 col-md-4">
                                        <label for="marca" class="col-form-label">Marca</label>
                                        <select class="form-control" name="marca" id="marca" onchange="getModel(this.value)">
                                            <option value="" selected>--Seleccione--</option>
                                            <c:forEach items="${marcas}" var="marca">
                                                <option value="${marca.idmarca}">${marca.marca}</option>
                                            </c:forEach>                                    
                                        </select>
                                    </div>
                                    <div class="form-group col-12 col-md-4">
                                        <label for="modelo" class="col-form-label">Modelo</label>
                                        <select class="form-control" name="modelo" id="modelo" onchange="getSerie(this.value)">
                                            <option value="" selected>--Seleccione--</option>                                  
                                        </select>
                                    </div>
                                    <div class="form-group col-12 col-md-4">
                                        <label for="serie" class="col-form-label">Serie</label>                                
                                        <select class="form-control" name="serie" id="serie">
                                            <option value="" selected>--Seleccione--</option>                                  
                                        </select>                                
                                    </div>

                                    <div class="form-group col-12 col-md-4">
                                        <label for="diagnostico" class="col-form-label">Diagnostico</label>
                                        <select class="form-control" name="diagnostico" id="diagnostico">
                                            <option value="No" selected>No</option>
                                            <option value="Si" >Si</option>
                                        </select>
                                    </div>

                                    <div class="form-group col-12 col-md-4">
                                        <label for="descuento" class="col-form-label">Descuento</label>
                                        <input type="number" any name="descuento" class="form-control" id="descuento" onkeyup="sum();">
                                    </div>

                                    <div class="form-group col-12 col-md-6">
                                        <label for="falla" class="col-form-label">Falla</label>
                                        <textarea name="falla" class="form-control" id="falla" rows="3"></textarea>
                                    </div>

                                    <div class="form-group col-12 col-md-6">
                                        <label for="solucion" class="col-form-label">Solución</label>
                                        <textarea name="solucion" class="form-control" id="solucion" rows="3"></textarea>
                                    </div>
                                </div>  
                            </div>

                            <div class="col-12 col-md-6">
                                <h5 class="text-center text-success">Agregar Servicios o Repuestos</h5>
                                <hr>
                                <div class="row">
                                    <div class="form-group col-12">
                                        <label for="servicio" class="col-form-label">Servicios</label>
                                        <select class="form-control" name="servicio" id="servicio" onchange="gServicio(this)">
                                            <option value="" selected></option>
                                            <c:forEach items="${servicios}" var="servicio">
                                                <option value="${servicio.idservicio}" data-item="${servicio.servicio}" data-pc="${servicio.precio}">${servicio.servicio} Precio:${servicio.precio}</option>
                                            </c:forEach>  
                                        </select>
                                    </div>
                                    <div class="responsive col-12">
                                        <table class="table" id="tbServicio">
                                            <thead>
                                                <tr>
                                                    <th>Servicio/Repuesto</th>
                                                    <th>Precio</th>
                                                    <th>Cant.</th>
                                                    <th>Total</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody></tbody>
                                        </table>
                                    </div>
                                    <div class="col-md-4 offset-md-3">
                                        <label class="text-success">Total de Inversion</label>
                                        <input type="number" any name="total" class="form-control" id="total" readonly>
                                        <hr>
                                    </div>
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
            let tc = '';
        </script>
        <c:if test="${usuario.rol.nombre == 'TECNICO'}">
            <script>
                tc = ${usuario.id}
            </script>
        </c:if>
        <!-- JS Page -->
        <script src="assets/js/mantenimiento.js" type="text/javascript"></script>        

    </body>
</html>
<%
    } else {
        response.sendRedirect("Login");
    }
%>