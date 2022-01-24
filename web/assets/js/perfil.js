class Perfil {

    Validated() {
        $.validator.setDefaults({
            submitHandler: function (form) {
                $.ajax({
                    url: form.action,
                    type: form.method,
                    data: $(form).serialize(),
                    dataType: "json",
                    success: function (res) {
                        /*const res = JSON.parse(response)*/
                        console.log(res.status);
                        if (res.status) {
                            Toast.fire({
                                icon: 'success',
                                title: res.message
                            })
                            setTimeout(()=>{
                                parent.location = "Perfil";
                            },1000);
                        } else {
                            Toast.fire({
                                icon: 'error',
                                title: res.memessage
                            })
                        }
                    }
                });

            }
        });
        $('#frmPerfil').validate({
            rules: {
                nombres: {
                    required: true,
                    minlength: 3,
                },
                apellidos: {
                    required: true,
                    minlength: 3,
                },
            },
            messages: {
                nombres: {
                    required: "Nombres requerdios.",
                    minlength: "Minimo 3 caracteres."
                },
                apellidos: {
                    required: "Apellidos requeridos.",
                    minlength: "Minimo 3 caracteres."
                }
            },
            errorElement: 'span',
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
}
const go = new Perfil();
go.Validated();
