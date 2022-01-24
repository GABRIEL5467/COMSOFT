const mod = "Serie";
const url = "Series";

var t = $('#tbData').DataTable({
    "responsive": true,
    "lengthChange": false, "autoWidth": false
});

const getContent = () => {
    $.ajax({
        url: url,
        type: "POST",
        data: {accion: "listar"},
        dataType: "json",
        success: function (res) {
            let bg = "success";
            let cm = "'";
            t.clear().draw();
            res.forEach(function (u) {
                if (u.estado === "Inactivo") {
                    bg = "danger";
                } else {
                    bg = "success";
                }
                t.row.add([
                    '<small class="badge bg-info">' + u.modelo.modelo + '</small>',
                    '<small class="badge bg-primary">' + u.serie + '</small>',
                    '<button onclick="uEstado(' + u.idserie + ',' + cm + u.estado + cm + ')" class="border-0 badge badge-' + bg + '">' + u.estado + '</button>',
                    '<span onclick="uGeneral(' + u.idserie + ',' + u.modelo.idmodelo + ',' + cm + u.serie + cm + ',' + cm + u.estado + cm + ')" class="btn btn-info btn-sm"><i class="fas fa-pencil-alt"></i></span><span onclick="uEstado(' + u.idserie + ',' + cm + 'Eliminado' + cm + ')" class="btn btn-danger btn-sm  d-none"><i class="fas fa-trash"></i></span>'
                ]).draw(false);
            });
        }
    });
};

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
            $("#idserie").val('');
            $("#serie").val('');
            $("#modelo").val('');
            $("#estado").val('Activo');
            var vlFrom = $("#frmData").validate();
            vlFrom.resetForm();
        }
    };
};

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
                        });
                        $("#modal").modal("hide");
                        t.clear().draw();
                        getContent();
                    } else {
                        Toast.fire({
                            icon: 'error',
                            title: res.message
                        });
                    }
                }
            });

        }
    });
    $('#frmData').validate({
        rules: {
            serie: {
                required: true,
                minlength: 2
            },
            modelo: {
                required: true
            },
            estado: {
                required: true
            }
        },
        messages: {
            serie: {
                required: "Serie requerdio.",
                minlength: "Minimo 3 caracteres."
            },
            modelo: {
                required: "Modelo requerido."
            },
            estado: {
                required: "Estado requerido."
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
};

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
            cancelButtonText: 'No, Cancelar!'
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

};

const uDetalle = (id, rol, nombres, apellidos, email, password, estado) => {
};


const uGeneral = (id, modelo, serie, estado) => {
    console.log(id, modelo, serie, estado);
    $("#accion").val("update");
    $("#idserie").val(id);
    $("#serie").val(serie);
    $("#modelo").val(modelo);
    $("#estado").val(estado);

    $("#mtitle").text("Actualizar " + mod);
    $("#mtexto").text("Actualizar");

    $("#modal").modal("show");
};

$(document).ready(function () {
    Validated();
    getContent();
});