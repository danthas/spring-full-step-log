// function eliminar(id) {
//     swal({
//         title: "Esta seguro de eliminar?",
//         text: "Una vez eliminado, no será capaz de recuperar el departamento eliminado",
//         icon: "warning",
//         buttons: true,
//         dangerMode: true,
//     })
//         .then((OK) => {
//             if (OK) {
//                 $.ajax({
//                     url: "/departamentos/eliminarDep/" + id,
//                     success: function (res) {
//                         console.log(res)
//                     }
//                 });
//                 swal("El departamento HA SIDO eliminado", {
//                     icon: "success",
//                 }).then((OK) => {
//                     location.href = "/departamentos/listarDep"
//                 });
//             } else {
//                 swal("El departamento NO ha sido eliminado");
//             }
//         });
// }

function llenarSelect(input) {
    select = Array.from(input.options);
    for (let i = 0; i < select.length; i++) {
        console.log((select[i].selected = true));
    }
}

function vaciarSelect(input) {
    select = Array.from(input.options);
    for (let i = 0; i < select.length; i++) {
        console.log((select[i].selected = false));
    }
}

var era;

function deseleccionarRadio(rbutton) {
    if (rbutton.checked == true && era == true) {
        rbutton.checked = false;
    }
    era = rbutton.checked;
}

function limpiarFormulario() {
    document.getElementById("alta").reset();
}

function mostrarClave() {
    var tipo1 = document.getElementById("clave");
    var tipo2 = document.getElementById("confirmaClave");
    if (tipo1.type == "password" || tipo2.type == "password") {
        tipo1.type = "text";
        tipo2.type = "text";
    } else {
        tipo1.type = "password";
        tipo2.type = "password";
    }
}

function seleccionarCheckboxes(input) {
    input.forEach((element) => {
        element.checked = true;
    });
}

function deseleccionarCheckboxes(input) {
    input.forEach((element) => {
        element.checked = false;
    });
}

//function encrypt(){
//    var pass=document.getElementById('password').value;
//    var hide=document.getElementById('hide').value;
//    if(pass=="") {
//        document.getElementById('err').innerHTML='Error:Password is missing';
//        return false;
//    } else {
//        document.getElementById("hide").value = document.getElementById("password").value;
//        var hash = CryptoJS.MD5(pass);
//        document.getElementById('password').value=hash;
//        return true;
//    }
//}