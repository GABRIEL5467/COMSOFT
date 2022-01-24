<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Soporte COMSOFT ][ Inicio </title>

        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="assets/plugins/fontawesome-free/css/all.min.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="assets/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
        <!-- Toastr -->
        <link rel="stylesheet" href="assets/plugins/toastr/toastr.min.css">
        <!-- icheck bootstrap -->
        <link rel="stylesheet" href="assets/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="assets/dist/css/adminlte.min.css">
    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href="Inicio"><b>Soporte</b>COMSOFT</a>
            </div>
            <!-- /.login-logo -->
            <div class="card">
                <div class="card-body login-card-body">
                    <p class="login-box-msg">Consultar Servició</p>

                    <form action="Orden" method="get">
                        <div class="row">          
                            <!-- /.col -->
                            <div class="input-group mb-3 col-9">
                                <input type="number" name="id" class="form-control" required placeholder="CODIGO">
                                <div class="input-group-append">
                                    <div class="input-group-text">
                                        <span class="fas fa-qrcode"></span>
                                    </div>
                                </div>
                            </div>

                            <div class="col-3">
                                <button type="submit" class="btn btn-outline-success btn-block"><i class="fab fa-searchengin"></i></button>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>

                    <div class="social-auth-links text-center mb-3">
                        <p>- O -</p>
                        <a href="Login" class="btn btn-block btn-primary">
                            <i class="fas fa-home mr-2"></i> Administración
                        </a>
                    </div>      
                </div>
                <!-- /.login-card-body -->
            </div>
        </div>
        <!-- /.login-box -->

        <!-- jQuery -->
        <script src="assets/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- SweetAlert2 -->
        <script src="assets/plugins/sweetalert2/sweetalert2.min.js"></script>
        <!-- Toastr -->
        <script src="assets/plugins/toastr/toastr.min.js"></script>
        <!-- AdminLTE App -->
        <script src="assets/dist/js/adminlte.min.js"></script>
        <script>

        </script>

        <c:if test="${nav != null}">
            <script>
                var Toast = Swal.mixin({
                    toast: true,
                    position: 'top-center',
                    showConfirmButton: false,
                    timer: 4000
                });


                Toast.fire({
                    icon: 'warning',
                    title: '${nav}'
                })
            </script>
        </c:if>
    </body>
</html>

