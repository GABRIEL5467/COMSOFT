const mod = "Servicio";
const url = "Servicios";

var t = $('#tbData').DataTable({
    "responsive": true,
    "lengthChange": false, "autoWidth": false,
});

const getContent = () => {
    $.ajax({
        url: url,
        type: "POST",
        data: {accion: "listar"},
        dataType: "json",
        success: function (res) {
            let bg = "success";
            let bt = "warning";
            let cm = "'";
            t.clear().draw();
            res.forEach(function (u) {
                if (u.estado === "Inactivo") {
                    bg = "danger"
                } else {
                    bg = "success"
                }
                
                if (u.type === "Servicio") {
                    bt = "info"
                } else {
                    bt = "warning"
                }
                
                t.row.add([
                    '<small>' + u.servicio + '</small>',
                    '<small class="badge bg-teal">' + u.precio + '</small>',
                    '<small class="badge bg-purple">' + u.stock + '</small>',
                    '<small class="border-0 badge badge-' + bt + '">' + u.type + '</small>',
                    '<button onclick="uEstado(' + u.idservicio + ',' + cm + u.estado + cm + ')" class="border-0 badge badge-' + bg + '">' + u.estado + '</button>',
                    '<span onclick="uGeneral(' + u.idservicio + ',' + cm + u.precio + cm + ',' +  u.stock + ',' + cm + u.servicio + cm + ',' + cm + u.type + cm + ',' + cm + u.estado + cm + ')" class="btn btn-info btn-sm"><i class="fas fa-pencil-alt"></i></span><span onclick="uEstado(' + u.idservicio + ',' + cm + 'Eliminado' + cm + ')" class="btn btn-danger btn-sm  d-none"><i class="fas fa-trash"></i></span>'
                ]).draw(false);
            });
        }
    });
}

const dtGen = () => {
    return{
        showModal(modal) {
            this.clear();
            $("#mtitle").text("Nuevo " + mod);
            $("#mtexto").text("Guardar");
            $("#" + modal).modal("show");
        },
        clear() {
            $("#accion").val("registro");
            $("#idservicio").val('');
            $("#servicio").val('');
            $("#precio").val('');
            $("#stock").val('');
            $("#type").val('Servicio');
            $("#estado").val('Activo');
            var vlFrom = $("#frmData").validate();
            vlFrom.resetForm();
        },
    }
}

const Validated = () => {
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
                        getContent();
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
            servicio: {
                required: true,
                minlength: 5,
            },
            precio: {
                required: true,
                number: true
            },
            stock: {
                required: true,
                number: true
            },
            type: {
                required: true
            },
            estado: {
                required: true
            }
        },
        messages: {
            servicio: {
                required: "Servicio requerdios.",
                minlength: "Minimo 5 caracteres."
            },
            precio: {
                required: "Precio requeridos.",
            },
            stock: {
                required: "Stock requeridos.",
            },
            type: {
                required: "Tipo de Servicio requeridos.",
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

const uEstado = (id, estado) => {

    if (estado === "Eliminado") {
        Swal.fire({
            title: 'Estas Seguro de Eliminar?',
            text: "Estas por eliminar un ." + mod,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si, Eliminar',
            cancelButtonText: 'No, Cancelar!',
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: url,
                    type: "POST",
                    data: {accion: "eliminar", id: id, estado: estado},
                    dataType: "json",
                    success: function (res) {
                        if (res.status) {
                            Swal.fire('Eliminado!', 'Eliminado Correctamente.', 'success');
                            getContent();
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
            url: url,
            type: "POST",
            data: {accion: "estado", id: id, estado: st},
            dataType: "json",
            success: function (res) {
                if (res.status) {
                    Toast.fire({icon: 'success', title: res.message})
                    getContent();
                } else {
                    Toast.fire({icon: 'error', title: res.message})
                }
            }
        });
    }

}

const uDetalle = (id, rol, nombres, apellidos, email, password, estado) => {
}


const uGeneral = (id, precio, stock, servicio, type, estado) => {
    console.log(id, precio, stock, servicio, type, estado);
    $("#accion").val("update");
    $("#idservicio").val(id);
    $("#servicio").val(servicio);
    $("#precio").val(precio);
    $("#stock").val(stock);
    $("#type").val(type);
    $("#estado").val(estado);

    $("#mtitle").text("Actualizar " + mod);
    $("#mtexto").text("Actualizar");

    $("#modal").modal("show");
}

$(document).ready(function () {
    Validated();
    getContent();
});