function cerrarSesion() {
    sessionStorage.removeItem('session');
    sessionStorage.removeItem('email');
    window.location.href = 'Login.html';
}
function darAlta() {
    window.location.href = 'Gestion.html';
}
function getTable() {
    var mail = sessionStorage.getItem('email');
    var session = sessionStorage.getItem('session');
    console.log(session)
    var ahttp = new XMLHttpRequest();

    ahttp.open("GET", "http://localhost:6900/FarmaciaEntornos/ServeXips?mail="+mail+"&session="+session, true);
    console.log(mail)
    console.log(session)
    ahttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    ahttp.send();
    ahttp.onreadystatechange = function () {
        if (ahttp.readyState == 4 && ahttp.status == 200) {
            var sesion = ahttp.response
            if (sesion == "null") {
                window.location.href = 'Login.html';
            } else {
                console.log(sesion)
                document.getElementById("tabla").innerHTML = sesion;
            }
        }
    }
}

  