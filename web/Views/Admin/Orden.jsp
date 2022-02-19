<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Soporte COMSOFT ][ ${nav}</title>

        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="assets/plugins/fontawesome-free/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="assets/dist/css/adminlte.min.css">

        <!-- jQuery -->
        <script src="assets/plugins/jquery/jquery.min.js"></script>
        <script src="assets/jspdf.umd.min.js" type="text/javascript"></script>
        <script src="assets/html2pdf.bundle.min.js" type="text/javascript"></script>
    </head>    
    <body> 
        <!-- Preloader -->
        <div class="preloader flex-column justify-content-center align-items-center">
            <img class="animation__shake" src="assets/dist/img/AdminLTELogo.png" alt="AdminLTELogo" height="60" width="60">
        </div>
        <div class="wrapper" style="font-size: 14px;" id="content">
            <!-- Main content -->
            <section class="invoice">
                <!-- title row -->
                <div class="row">
                    <div class="col-12">
                        <h2 class="page-header">
                            <i class="fas fa-globe"></i> Soporte COMSOFT.
                            <small class="float-center ml-4" style="font-size:18px;">F. Impresion: <%= (new java.util.Date()).toLocaleString()%></small>
                        </h2>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- info row -->
                <div class="row invoice-info">
                    <div class="col-sm-4 invoice-col">
                        DETALLES DE CLIENTE 
                        <address>
                            <strong>NOMBRES Y APELLIDOS:</strong> ${orden.cliente.nombresApellidos}<br>
                            <strong>DOCUMENTO:</strong> ${orden.cliente.documento}<br>
                            <strong>EMAIL:</strong> ${orden.cliente.email}<br>
                            <strong>CELULAR:</strong> ${orden.cliente.cel}<br>
                        </address>
                    </div>
                    <!-- /.col -->
                    <div class="col-sm-4 invoice-col">
                        DETALLES DEL EQUIPO
                        <address>
                            <strong>EQUIPO:</strong> ${orden.equipo}<br>
                            <strong>MARCA:</strong> ${orden.marca.marca}<br>
                            <strong>MODELO:</strong> ${orden.modelo.modelo}<br>
                            <strong>SERIE:</strong> ${orden.serie.serie}<br>
                            <strong>FALLA:</strong> ${orden.falla}<br>
                            <strong>SOLUCIÓN</strong> ${orden.solucion}<br>
                        </address>
                    </div>
                    <!-- /.col -->
                    <div class="col-sm-4 invoice-col">
                        <b>ORDEN: 000${orden.idmante}</b><br>
                        <br>
                        <b>TECNICO:</b> ${orden.usuario.nombres}<br>
                        <b>Order ID:</b> 000${orden.idmante}<br>
                        <b>Total:</b> ${orden.total}<br>
                        <!--<b>Account:</b> 968-34567-->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->

                <!-- Table row -->
                <div class="row">
                    <div class="col-12">
                        <h3>Estado del Servicio: <span class="badge bg-primary">${orden.estado}</span></h3>
                    </div>
                    <div class="col-12 table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Servcio</th>
                                    <th>Precio</th>
                                    <th>Cantidad</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${servicios}" var="servicio">
                                    <c:if test="${servicio.iddmante != null}">
                                        <tr>
                                            <td>${servicio.servicio.servicio}</td>
                                            <td>${servicio.precio}</td>
                                            <td>${servicio.cantidad}</td>
                                            <td>${servicio.total}</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${servicio.iddmante == null}">
                                        <tr>
                                            <td colspan="4">No hay Servicios adicionales</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->

                <div class="row">
                    <!-- accepted payments column -->
                    <div class="col-6">
                        <p class="lead">Metodos de pago:</p>
                        <img src="assets/dist/img/credit/visa.png" alt="Visa">
                        <img src="assets/dist/img/credit/mastercard.png" alt="Mastercard">
                        <img src="assets/dist/img/credit/american-express.png" alt="American Express">
                        <img src="assets/dist/img/credit/paypal2.png" alt="Paypal">

                        <p class="text-muted well well-sm shadow-none" style="margin-top: 10px;">
                            Puede realizar los pagos en toda estas entidades con las que estamos afiliados para recibir sus  abonos de pagos.
                        </p>
                    </div>
                    <!-- /.col -->
                    <div class="col-6">
                       <!-- <p class="lead">Monto a pagar: <%= (new java.util.Date()).toLocaleString()%></p>-->
                        <p class="lead">Monto a pagar:</p>
                        <div class="table-responsive">
                            <table class="table">
                                <tr>
                                    <th style="width:50%">Subtotal:</th>
                                    <td>$${orden.total + orden.descuento}</td>
                                </tr>
                                <tr>
                                    <th>Descuento</th>
                                    <td>$${orden.descuento}</td>
                                </tr>
                                <tr>
                                    <th>Total:</th>
                                    <td>$${orden.total}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                
                    <!-- /.col -->
                </div>
                <div class="row">
                       <div class="col-6">
                        <p class="lead">Términos y Condiciones</p>
                        <p class="text-muted well well-sm shadow-none" style="margin-top: 10px;">
                            1.-Retirar equipos antes de 30 dias.<br> 
                            2.-El Cliente puede retirar el equipo una vez notificado.<br>  
                            3.-Si el cliente no retira el artículo dejado a reparación despues 
                            de los 30 días el departamento de servicio técnico dispondra 
                               del mismo.
                        </p>
                    </div>
                    <div class="col-6">
                        <p class="lead" align="center">Acepto Condiciones</p>
                        <p class="text-muted well well-sm shadow-none" style="margin-top: 10px;">
                        <br>
                        <p align="center"> ___________________________</p>
                        <p align="center">Firma.</p>
                        </br>
                        </p>
                    </div>
                </div>
                <div class="row">
                
                    <div class="col-12">
                         <p class="text-muted well well-sm shadow-none" style="margin-top: 10px;" align="center">
                             <font face="arial">*Dirección:Huancavilca N28-208 y Duchicela (Frente al Complejo, Deportivo de Liga Atahualpa).*<font><br> 
                             *Telf.:22338-929  0984463249 * E-mail: comercialfranklin@gmail.com*<br>
                         </p>
                    </div>
                   </row>
                <!-- /.row -->
            </section>
            <!-- /.content -->
        </div>
        <!-- ./wrapper -->

        <div id="elementH"></div>
        <!-- Page specific script -->
        <script>
            window.jsPDF = window.jspdf.jsPDF;
            /*$(document).ready(function () {
             var doc = new jsPDF({
             orientation: 'p',
             unit: 'mm',
             format: 'a4',
             scalewidth: 1,
             size:14,
             width: 170
             });
             
             doc.html(document.body, {
             callback: function (doc) {
             doc.save("doc.pdf");
             },
             x: 10,
             y: 10
             });
             });*/

            document.addEventListener("DOMContentLoaded", () => {
                // Escuchamos el click del botón
                pdf();



            });
            const pdf = () => {
                const $elementoParaConvertir = document.getElementById("content"); // <-- Aquí puedes elegir cualquier elemento del DOM
                html2pdf()
                        .set({
                            margin: 1,
                            border: 0,
                            filename: '${orden.cliente.nombresApellidos} <%= (new java.util.Date()).toLocaleString()%>.pdf',
                            image: {
                                type: 'jpeg',
                                quality: 0.98
                            },
                            html2canvas: {
                                scale: 3, // A mayor escala, mejores gráficos, pero más peso
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
                setTimeout(() => {
                    window.location.href = "Inicio";
                }, 450);

            }
        </script>
    </body>
</html>

