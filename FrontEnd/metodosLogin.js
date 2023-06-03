/*Enviar email y sesion en cada pagian*/

function aestudiar() {
    alert("Haber Estudiado")
}
function enviar() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;


    let http = new XMLHttpRequest();

    http.open("GET", "http://localhost:6900/FarmaciaEntornos/Login?mail="+email+"&pass="+password, true);
    console.log(email)
    console.log(password)
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send();
    /*if response is null borrar sesion storage, mandar al login*/
    
    http.onreadystatechange = function(){
        if(this.readyState==4 && this.status==200){
            var session = this.responseText
            document.getElementById("email").value = "";
            document.getElementById("password").value = "";
            console.log(session)
            if(session == "null"){
                console.log("NO")
                alert("Login Incorrecto")
            }else{
                window.location.href = 'Xips.html';
                console.log("Iniciado Sesion");
                console.log(session);
                console.log(email);
                sessionStorage.setItem("session",session);
                sessionStorage.setItem("email",email);
            }
        }
    }
}