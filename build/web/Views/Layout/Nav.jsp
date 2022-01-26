<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Preloader -->
<div class="preloader flex-column justify-content-center align-items-center">
    <img class="animation__shake" src="assets/dist/img/AdminLTELogo.png" alt="AdminLTELogo" height="60" width="60">
</div>

<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="Admin" class="nav-link">Dashboard</a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="Mantenimientos" class="nav-link">Generar Orden</a>
        </li>
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
        <!-- Navbar Search -->
        <li class="nav-item">
            <a class="nav-link" data-widget="navbar-search" href="#" role="button">
                <i class="fas fa-search"></i>
            </a>
            <div class="navbar-search-block">
                <form class="form-inline">
                    <div class="input-group input-group-sm">
                        <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
                        <div class="input-group-append">
                            <button class="btn btn-navbar" type="submit">
                                <i class="fas fa-search"></i>
                            </button>
                            <button class="btn btn-navbar" type="button" data-widget="navbar-search">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </li>

        <!-- Messages Dropdown Menu -->
        <li class="nav-item dropdown user-menu" >
            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">
                <img src="assets/dist/img/user2-160x160.jpg" class="user-image img-circle elevation-2" alt="User Image">
                <span class="d-none d-md-inline">${usuario.nombres} ${usuario.apellidos}</span>
            </a>
            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-right border-0" style="border-radius: 10px;">
                <!-- User image -->
                <li class="user-header bg-default">
                    <img src="assets/dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">

                    <p>
                        ${usuario.nombres} ${usuario.apellidos} 
                        <small class="badge bg-success d-inline">[${usuario.rol.nombre}]</small>
                    </p>
                </li>                
                <!-- Menu Footer-->
                <li class="user-footer">
                    <a href="Perfil" class="btn btn-success btn-flat">Perfil</a>
                    <form action="Login?accion=cerrar" method="POST" class="d-inline">
                        <button type="submit" class="btn btn-danger btn-flat float-right">Cerrar Sesión</button>
                    </form>
                </li>
            </ul>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-widget="fullscreen" href="#" role="button">
                <i class="fas fa-expand-arrows-alt"></i>
            </a>
        </li>
    </ul>
</nav>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="Admin" class="brand-link">
        <img src="assets/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">Soporte COMSOFT </span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="assets/dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">${usuario.nombres} ${usuario.apellidos}</a>
            </div>
        </div>

        <!-- SidebarSearch Form -->
        <div class="form-inline">
            <div class="input-group" data-widget="sidebar-search">
                <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
                <div class="input-group-append">
                    <button class="btn btn-sidebar">
                        <i class="fas fa-search fa-fw"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                    <li class="nav-item">
                         <a href="Admin" class="nav-link <c:if test="${nav == 'Dashboard'}"> active</c:if>" >
                            <i class="nav-icon fas fa-desktop"></i><p>Dashboard</p>
                        </a>
                    </li>
                    <li class="nav-item">
                         <a href="Mantenimientos" class="nav-link <c:if test="${nav == 'Mantenimientos'}"> active</c:if>" >
                            <i class="nav-icon fas fa-paste"></i><p>Generar Orden</p>
                        </a>
                    </li>
                    <li class="nav-item">
                         <a href="Servicios" class="nav-link <c:if test="${nav == 'Servicios'}"> active</c:if>" >
                            <i class="nav-icon fas fa-tools"></i><p>Servicio</p>
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${drop != null}"> menu-open </c:if>">
                    <a href="#" class="nav-link <c:if test="${drop != null}"> active </c:if>">
                            <i class="nav-icon fas fa-cogs"></i>
                            <p>Gestión<i class="right fas fa-angle-left"></i></p>
                        </a>
                        <ul class="nav nav-treeview">
                            <li class="nav-item">
                                <a href="Marcas" class="nav-link <c:if test="${nav == 'Marcas'}"> active </c:if>">
                                    <i class="fas fa-tag nav-icon"></i>
                                    <p>Marca</p>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="Modelos" class="nav-link <c:if test="${nav == 'Modelos'}"> active </c:if>">
                                    <i class="fas fa-tags nav-icon"></i>
                                    <p>Modelo</p>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="Series" class="nav-link <c:if test="${nav == 'Series'}"> active </c:if>">
                                    <i class="fas fa-tags nav-icon"></i>
                                    <p>Serie</p>
                                </a>
                            </li>
                        </ul>
                    </li>
                                        
                    <li class="nav-item">
                        <a href="Clientes" class="nav-link <c:if test="${nav == 'Clientes'}"> active</c:if>" >
                            <i class="nav-icon fas fa-address-book"></i><p>Clientes</p>
                        </a>
                    </li>

                <c:if test="${usuario.rol.nombre == 'ADMINISTRADOR'}">
                    <li class="nav-item">
                        <a href="Usuarios" class="nav-link <c:if test="${nav == 'Usuarios'}"> active</c:if>" >
                                <i class="nav-icon fas fa-users"></i><p>Usuarios</p>
                            </a>
                        </li>
                </c:if>

            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">${nav}</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="Admin">Dashboard</a></li>
                        <li class="breadcrumb-item active">${nav}</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
