/* global Toast, parseFloat, Swal */

const mod = "Mantenimieno";
const url = "Mantenimientos";

var t = $('#tbData').DataTable({
    "responsive": true,
    "lengthChange": false, "autoWidth": false
});

var ts = $('#tbServicio').DataTable({
    "responsive": true,
    "lengthChange": false, "autoWidth": false, select: {
        items: 'cell',
        info: true
    }
});


$('tr').on("click", function (e) {
    index = $(this).closest('tr').index();
    table.row($(this)).remove().draw();
});

const getContent = () => {
    $.ajax({
        url: url,
        type: "POST",
        data: {accion: "listar"},
        dataType: "json",
        success: function (res) {
            let bg = "success";
            let ac = "", ce = "";
            let cm = "'";
            t.clear().draw();
            res.forEach(function (u) {
                if (u.estado === "Taller") {
                    bg = "success";
                } else if (u.estado === "Cancelar") {
                    bg = "danger";
                    ac = "d-none";
                    ce = "disabled"
                } else {
                    bg = "primary";
                }
                t.row.add([
                    '<small>' + u.cliente.nombresApellidos + '</small>',
                    '<small class="badge bg-info">' + u.usuario.nombres + '</small>',
                    '<small>' + u.equipo + ' [' + u.marca.marca + ' - ' + u.modelo.modelo + ' - ' + u.serie.serie + ']</small>',
                    '<small class="badge bg-teal">' + u.diagnostico + '</small>',
                    '<small>' + parseFloat(u.descuento).toFixed(2) + '</small>',
                    '<small>' + parseFloat(u.total).toFixed(2) + '</small>',
                    '<small>' + u.fecha + '</small>',
                    //'<small>' + u.f_entrega + '</small>',
                    '<button onclick="uEstado(' + u.idmante + ',' + cm + u.estado + cm + ')" class="border-0 badge badge-' + bg + '" ' + ce + '>' + u.estado + '</button>',
                    '<a href="Orden?id='+u.idmante+'" target="_blnak" class="btn btn-dark btn-sm"><i class="fas fa-print"></i></a><span onclick="uGeneral(' + u.idmante + ', ' + u.cliente.idcliente + ', ' + u.usuario.id + ', ' + cm + u.equipo + cm + ', ' + u.marca.idmarca + ', ' + u.modelo.idmodelo + ', ' + u.serie.idserie + ', ' + cm + u.diagnostico + cm + ', ' + cm + u.falla + cm + ', ' + cm + u.solucion + cm + ', ' + u.descuento + ', ' + u.total + ', ' + cm + u.estado + cm + ')" class="btn btn-info btn-sm ' + ac + '"><i class="fas fa-pencil-alt"></i></span><span onclick="uEstado(' + u.idmante + ', ' + cm + 'Eliminado' + cm + ')" class="btn btn-danger btn-sm ' + ac + '"><i class="fas fa-trash"></i></span>'
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
            $("#idmante").val('');
            $("#cliente").val('');
            $("#tecnico").val(tc);
            $("#equipo").val('');
            $("#marca").val('');
            $("#modelo").html('<option value="" selected>--Seleccione Marca--</option>');
            $("#serie").html('<option value="" selected>--Seleccione Modelo--</option>');
            $("#diagnostico").val('No');
            $("#descuento").val('0.00');
            $("#falla").val('');
            $("#solucion").val('');
            $("#total").val('0.00');
            $("#estado").val('Taller');
            var vlFrom = $("#frmData").validate();
            vlFrom.resetForm();
            ts.clear().draw();
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
                        window.open("Orden?id=" + res.data, "_blank");
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
            cliente: {
                required: true
            },
            tecnico: {
                required: true
            },
            equipo: {
                required: true
            },
            marca: {
                required: true
            },
            modelo: {
                required: true
            },
            serie: {
                required: true
            },
            estado: {
                required: true
            }
        },
        messages: {
            cliente: {
                required: "Cliente requerdio.",
            },
            tecnico: {
                required: "Tecnico requerdio.",
            },
            equipo: {
                required: "Equipo requerdio.",
            },
            marca: {
                required: "Marca requerdio.",
            },
            modelo: {
                required: "Modelo requerido."
            },
            serie: {
                required: "Serie requerdio.",
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
    let st = "Cancelar";
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
                    data: {accion: "eliminar", id: id, estado: st},
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
        let st = "Taller";
        if (estado === 'Taller') {
            st = "Entregado";
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


const uGeneral = (id, cliente, tecnico, equipo, marca, modelo, serie, diagnostico, falla, solucion, descuento, total, estado) => {
    ts.clear().draw();
    $("#accion").val("update");
    $("#idmante").val(id);
    $("#cliente").val(cliente);
    $("#tecnico").val(tecnico);
    $("#equipo").val(equipo);
    $("#marca").val(marca);

    getModel(marca);
    getSerie(modelo);

    $("#diagnostico").val(diagnostico);
    $("#descuento").val(parseFloat(descuento).toFixed(2));
    $("#falla").val(falla);
    $("#solucion").val(solucion);
    $("#total").val(parseFloat(total).toFixed(2));
    $("#estado").val(estado);

    $.ajax({
        url: url,
        type: "POST",
        data: {accion: "gSerDetalle", data: id},
        dataType: "json",
        success: function (res) {
            if (res.length > 0) {
                res.forEach(function (s) {
                    console.log(s);
                    ts.row.add([
                        '<small>' + s.servicio.servicio + '</small><input type="hidden" name="servicio[]" value="' + s.servicio.idservicio + '">',
                        '<small>' + parseFloat(s.precio).toFixed(2) + '</small><input type="hidden" name="precio[]" value="' + parseFloat(s.precio).toFixed(2) + '">',
                        '<small>' + s.cantidad + '</small><input type="hidden" name="cantidad[]" value="' + s.cantidad + '">',
                        '<small>' + parseFloat(s.total).toFixed(2) + '</small><input type="hidden" class="ttl" name="total[]" value="' + parseFloat(s.total).toFixed(2) + '">',
                        '<span onclick="remove(this)" class="btn btn-danger btn-sm delete"><i class="fas fa-trash"></i></span>'
                    ]).draw(false);
                    sum();
                });
            } else {
                Toast.fire({icon: 'error', title: 'No tiene Servicios / Repuestos'})
            }

        }
    });
    setTimeout(function () {
        $("#modelo").val(modelo);
        $("#serie").val(serie);
    }, 600)

    $("#mtitle").text("Actualizar " + mod);
    $("#mtexto").text("Actualizar");

    $("#modal").modal("show");
};

const getModel = (req) => {
    $.ajax({
        url: url,
        type: "POST",
        data: {accion: "gModelo", data: req},
        dataType: "json",
        success: function (res) {
            if (res.length > 0) {
                let html = '<option value="" selected>--Seleccione--</option>';
                res.forEach(function (m) {
                    html += '<option value="' + m.idmodelo + '">' + m.modelo + '</option>';
                });

                $("#modelo").html(html);
                $("#serie").html("<option value''>--Seleccione Modelo --</option>");
                Toast.fire({icon: 'success', title: 'Modelos cargados'})
            } else {
                $("#modelo").html("<option value''>--Seleccione Marca --</option>");
                $("#serie").html("<option value''>--Seleccione Modelo --</option>");
                Toast.fire({icon: 'error', title: 'No hay modelos para la marca, seleccione otro'})
            }

        }
    });
}

const getSerie = (req) => {
    $.ajax({
        url: url,
        type: "POST",
        data: {accion: "gSerie", data: req},
        dataType: "json",
        success: function (res) {
            if (res.length > 0) {
                Toast.fire({icon: 'success', title: 'Series cargados'})
                let html = '<option value="" selected>--Seleccione--</option>';
                res.forEach(function (m) {
                    html += '<option value="' + m.idserie + '">' + m.serie + '</option>';
                });
                $("#serie").html(html);
            } else {
                $("#serie").html("<option value''>--Seleccione Modelo --</option>");
                Toast.fire({icon: 'error', title: 'No existe series para el modelo selecionado'})
            }

        }
    });
}

const gServicio = (req) => {
    var serv = $('option:selected', req).attr('data-item');
    var pc = $('option:selected', req).attr('data-pc');

    Swal.fire({
        title: 'Ingrese Cantidad para el ' + serv,
        input: 'number',
        inputAttributes: {
            autocapitalize: 'off',
            min: 1
        },
        showCancelButton: true,
        confirmButtonText: 'Agregar Servicio',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            if (result.value === "") {
                result.value = "1";
            }
            ts.row.add([
                '<small>' + serv + '</small><input type="hidden" name="servicio[]" value="' + req.value + '">',
                '<small>' + parseFloat(pc).toFixed(2) + '</small><input type="hidden" name="precio[]" value="' + parseFloat(pc).toFixed(2) + '">',
                '<small>' + result.value + '</small><input type="hidden" name="cantidad[]" value="' + result.value + '">',
                '<small>' + parseFloat(pc * parseInt(result.value)).toFixed(2) + '</small><input type="hidden" class="ttl" name="total[]" value="' + parseFloat(pc * parseInt(result.value)).toFixed(2) + '">',
                '<span onclick="remove(this)" class="btn btn-danger btn-sm delete"><i class="fas fa-trash"></i></span>'
            ]).draw(false);

            $("#servicio").val('');
            sum();
        }

    });
};

$(document).ready(function () {
    Validated();
    getContent();

    $('#tbServicio tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            ts.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

});

const remove = (req) => {
    ts.row('.selected').remove().draw(false);
    sum();
};

const sum = () => {
    let sub = 0;
    let tc = $(".ttl");
    for (var i = 0, max = tc.length; i < max; i++) {
        sub += parseFloat(tc[i].value);
    }
    let sb = $("#descuento").val();
    $("#total").val(parseFloat(parseFloat(sub) - parseFloat(sb)).toFixed(2));
}