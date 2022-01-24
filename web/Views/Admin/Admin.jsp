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
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">

            <%@include file="../Layout/Nav.jsp" %>            

            <!-- Main content -->
            <div class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-3 col-sm-6 col-12">
                            <div class="info-box shadow-lg">
                                <span class="info-box-icon bg-success"><i class="fas fa-paste"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Mantenimientos</span>
                                    <span class="info-box-number">${mante}</span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>

                        <div class="col-md-3 col-sm-6 col-12">
                            <div class="info-box shadow-lg">
                                <span class="info-box-icon bg-teal"><i class="fas fa-tools"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Mant. Taller</span>
                                    <span class="info-box-number">${mtaller}</span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>

                        <div class="col-md-3 col-sm-6 col-12">
                            <div class="info-box shadow-lg">
                                <span class="info-box-icon bg-purple"><i class="fas fa-users"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Clientes</span>
                                    <span class="info-box-number">${cliente}</span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>

                        <div class="col-md-3 col-sm-6 col-12">
                            <div class="info-box shadow-lg">
                                <span class="info-box-icon bg-warning"><i class="far fa-star"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Servicios</span>
                                    <span class="info-box-number">${servi}</span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <div class="col-12">
                            <hr>
                        </div>
                        <div class="col-12 col-md-4">
                            Informe Tecnico:
                            <div class="input-group input-group-sm">
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
                                <span class="input-group-append">
                                    <span onclick="rTecnico()" class="btn btn-info btn-flat">Buscar</span>
                                </span>
                            </div>
                        </div>
                        <div class="col-12 col-md-8 text-center">
                            <span class="btn btn-danger mb-2" onclick="rPDF()" id="pdf"><i class="fas fa-file-pdf"></i> Descargar PDF</span>
                            <div class="row bg-white rounded shadow p-3" id="rt">
                                <div class="col-12">
                                    <table class="table">
                                        <h6>Mantenimientos Pendientes</h6>
                                        <thead>
                                            <tr>
                                                <th># Orden</th>
                                                <th>Cliente</th>
                                                <th>Fecha</th>
                                                <th>Estado</th>
                                            </tr>
                                        </thead>
                                        <tbody id="pd">
                                            <tr>
                                                <td>1</td>
                                                <td>Cliente</td>
                                                <td>Fecha</td>
                                                <td>Estado</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="col-12">
                                    <table class="table">
                                        <h6>Mantenimientos Atendidos</h6>
                                        <thead>
                                            <tr>
                                                <th># Orden</th>
                                                <th>Cliente</th>
                                                <th>Fecha</th>
                                                <th>Estado</th>
                                            </tr>
                                        </thead>
                                        <tbody id="at">
                                            <tr>
                                                <td>1</td>
                                                <td>Cliente</td>
                                                <td>Fecha</td>
                                                <td>Estado</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                </div><!-- /.container-fluid -->
            </div>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <%@include file="../Layout/Footer.jsp" %>
        <script src="assets/jspdf.umd.min.js" type="text/javascript"></script>
        <script src="assets/html2pdf.bundle.min.js" type="text/javascript"></script>

        <script>
                                var Toast = Swal.mixin({
                                    toast: true,
                                    position: 'top-end',
                                    showConfirmButton: false,
                                    timer: 3000
                                });
                                $("#pdf").hide();
                                const rTecnico = () => {
                                    if ($("#tecnico").val() === "") {
                                        Toast.fire({icon: 'error', title: 'Seleccione un Tecnico.'})
                                        $("#at").html('<tr><td colspan="$">No tiene Registros</td></tr>');
                                        $("#pd").html('<tr><td colspan="$">No tiene Registros</td></tr>');
                                        $("#pdf").hide();
                                    } else {
                                        $.ajax({
                                            url: "Admin",
                                            type: "POST",
                                            data: {accion: "rtecnico", data: $("#tecnico").val()},
                                            dataType: "json",
                                            success: function (res) {
                                                let hat = '';
                                                let hpd = '';
                                                if (res.length > 0) {
                                                    res.forEach(function (m) {
                                                        if (m.estado === 'Entregado') {
                                                            hat += "<tr><td>000" + m.idmante + "</td><td>" + m.cliente.nombresApellidos + "</td><td>" + m.fecha + "</td><td>" + m.estado + "</td></tr>";
                                                        } else {
                                                            hpd += "<tr><td>000" + m.idmante + "</td><td>" + m.cliente.nombresApellidos + "</td><td>" + m.fecha + "</td><td>" + m.estado + "</td></tr>"
                                                        }
                                                    });
                                                    $("#at").html(hat);
                                                    $("#pd").html(hpd);
                                                    Toast.fire({icon: 'success', title: 'Mantenimientos cargados'})
                                                    $("#pdf").show();
                                                } else {
                                                    Toast.fire({icon: 'error', title: 'No tiene Mantenimientos asignados'})
                                                    $("#pdf").hide();
                                                }
                                            }
                                        });
                                    }

                                };

                                const rPDF = () => {
                                    var selectedText = $( "#tecnico option:selected" ).text();
                                    const $elementoParaConvertir = document.getElementById("rt"); // <-- Aquí puedes elegir cualquier elemento del DOM
                                    html2pdf()
                                            .set({
                                                margin: 2,
                                                filename: 'TECNICO - ' + selectedText + ' - <%= (new java.util.Date()).toLocaleString()%>.pdf',
                                                image: {
                                                    type: 'jpeg',
                                                    quality: 0.98
                                                },
                                                html2canvas: {
                                                    scale: 2, // A mayor escala, mejores gráficos, pero más peso
                                                    letterRendering: true,
                                                },
                                                jsPDF: {
                                                    unit: "cm",
                                                    format: "a4",
                                                    orientation: 'portrait' // landscape o portrait
                                                }
                                            })
                                            .from($elementoParaConvertir)
                                            .save()
                                            .catch(err => console.log(err));
                                };
        </script>

    </body>
</html>
<%
    } else {
        response.sendRedirect("Login");
    }
%>