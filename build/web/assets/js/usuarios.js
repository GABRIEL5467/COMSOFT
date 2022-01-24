class Usuarios {
    dtUsuario() {
        return{
            open: false,
            idusuario: '',
            nombres: '',
            apellidos: '',
            email: '',
            clave1: '',
            clave: '',
            rol: 3,
            estado: 'Activo',
            showModal(modal) {
                $("#mtitle").text("Nuevo Usuario");
                $("#mtexto").text("Guardar");
                $("#" + modal).modal("show");
                $("#clave").hide();
                $("#clave1").show();
            },
            clear() {

                this.idusuario = ''
                this.nombres = ''
                this.apellidos = ''
                this.email = ''
                this.clave1 = ''
                this.clave = ''
                this.rol = '3'
                this.estado = 'Activo'
                var validator = $("#frmData").validate();
                validator.resetForm();
            },
        }
    }
    showModal(modal) {
        $("#" + modal).modal("show");
    }
    hideModal(modal) {
        $("#" + modal).modal("hide");
    }
    Validated() {
        $.validator.setDefaults({
            submitHandler: function (form) {
                $.ajax({
                    url: form.action,
                    type: form.method,
                    data: $(form).serialize(),
                    dataType: "json",
                    success: function (res) {
                        if (res.status) {
                            Toast.fire({
                                icon: 'success',
                                title: res.message
                            })
                            $("#modal").modal("hide");
                            t.clear().draw();
                            go.getContent();
                        } else {
                            Toast.fire({
                                icon: 'error',
                                title: res.message
                            })
                        }
                    }
                });

            }
        });
        $('#frmData').validate({
            rules: {
                nombres: {
                    required: true,
                    minlength: 3,
                },
                apellidos: {
                    required: true,
                    minlength: 3,
                },
                email: {
                    required: true,
                    email: true,
                },
                clave1: {
                    required: true,
                },
                rol_id: {
                    required: true
                },
                estado: {
                    required: true
                }
            },
            messages: {
                nombres: {
                    required: "Nombres requerdios.",
                    minlength: "Minimo 3 caracteres."
                },
                apellidos: {
                    required: "Apellidos requeridos.",
                    minlength: "Minimo 3 caracteres."
                },
                email: {
                    required: "Email requerdios.",
                    email: "Email invalido."
                },
                clave1: {
                    required: "Contraseña requeridos.",
                },
                rol_id: {
                    required: "Rol requerdios.",
                },
                estado: {
                    required: "Estado requeridos.",
                }
            },
            errorElement: 'div',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
            }
        });
    }
    getContent() {
        $.ajax({
            url: "Usuarios",
            type: "POST",
            data: {accion: "listar", listar: 'listar'},
            dataType: "json",
            success: function (res) {
                let bg = "success";
                let cm = "'";
                t.clear().draw();
                res.forEach(function (u) {
                    if (u.estado === "Inactivo") {
                        bg = "danger"
                    } else {
                        bg = "success"
                    }
                    t.row.add([
                        '<small>' + u.nombres + ' ' + u.apellidos + '</small>',
                        '<small>' + u.email + '</small>',
                        '<span class="badge badge-info">' + u.rol.nombre + '</span>',
                        '<button onclick="uEstado(' + u.id + ',' + cm + u.estado + cm + ')" class="border-0 badge badge-' + bg + '">' + u.estado + '</button>',
                        '<span onclick="uGeneral(' + u.id + ',' + u.rol.id + ',' + cm + u.nombres + cm + ',' + cm + u.apellidos + cm + ',' + cm + u.email + cm + ',' + cm + u.password + cm + ',' + cm + u.estado + cm + ')" class="btn btn-info btn-sm"><i class="fas fa-pencil-alt"></i></span><span onclick="uEstado(' + u.id + ',' + cm + 'Eliminado' + cm + ')" class="btn btn-danger btn-sm"><i class="fas fa-trash"></i></span>'
                    ]).draw(false);
                });
            }
        });
    }
}
const go = new Usuarios();

var t = $('#tbData').DataTable({
    "responsive": true,
    "lengthChange": false, "autoWidth": false,   
});


$(document).ready(function () {
    go.Validated();
    go.getContent();
});


const uEstado = (id, estado) => {

    if (estado === "Eliminado") {
        Swal.fire({
            title: 'Estas Seguro de Eliminar?',
            text: "Estas por eliminar un usuario.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si, Eliminar',
            cancelButtonText: 'No, Cancelar!',
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "Usuarios",
                    type: "POST",
                    data: {accion: "eliminar", usuarioid: id, estado: estado},
                    dataType: "json",
                    success: function (res) {
                        if (res.status) {
                            Swal.fire('Eliminado!', 'Eliminado Correctamente.', 'success');
                            go.getContent();
                        } else {
                            Toast.fire({icon: 'error', title: res.message})
                        }
                    }
                });

            }
        });
    } else {
        let st = "Inactivo";
        if (estado === 'Inactivo') {
            st = "Activo";
        }
        $.ajax({
            url: "Usuarios",
            type: "POST",
            data: {accion: "estado", usuarioid: id, estado: st},
            dataType: "json",
            success: function (res) {
                if (res.status) {
                    Toast.fire({icon: 'success', title: res.message})
                    go.getContent();
                } else {
                    Toast.fire({icon: 'error', title: res.message})
                }
            }
        });
    }

}

const uDetalle = (id, rol, nombres, apellidos, email, password, estado) => {
}


const uGeneral = (id, rol, nombres, apellidos, email, password, estado) => {
    $("#accion").val("update");
    $("#idusuario").val(id);
    $("#rol_id").val(rol);
    $("#nombres").val(nombres);
    $("#apellidos").val(apellidos);
    $("#email").val(email);
    $("#clave1").val(password);
    $("#estado").val(estado);

    $("#mtitle").text("Actualizar Usuario");
    $("#mtexto").text("Actualizar");

    go.showModal("modal");
    $("#clave1").hide();
    $("#clave").show();
}