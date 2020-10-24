// Guardamos los botones en variables
let botonListarClientes = document.getElementById("lista");
let botonMostrarCliente = document.getElementById("cliente");
let botonAñadirCliente = document.getElementById("aniade");
// Guardamos el div donde vamos a mostrar los resultados en una variable
let divResultados = document.getElementById("resultado");

// Funcionamiento del botón para mostrar la lista de los clientes
botonListarClientes.addEventListener('click', (e) => {
    e.preventDefault();
    eliminaTabla();
    resetFormulario()
    fetch('http://127.0.0.1:8080/api/customer')
        .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
        .then(res => res.json())
        .then(res => {
            let tabla = document.createElement("table");
            tabla.id = "clientes";
            let pos = 1;
            aniadePrimeraFila(tabla);
            res.forEach(customer => {
                aniadeFila(customer, pos, tabla);
                pos++;
            });
            divResultados.appendChild(tabla);
        })
        .catch(error => {
            if (error.status == 404) {
                alert("No existen clientes");
            }
            else {
                alert("Se ha producido un error inesperado");
            }
        })
});

botonMostrarCliente.addEventListener('click', (e) => {
    e.preventDefault();
    eliminaTabla();
    let id = document.getElementById("id").value;
    if (id == "") {
        alert("Debe introducir un id");
    }
    else {
        fetch(`http://127.0.0.1:8080/api/customer/${id}`)
            .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
            .then(res => res.json())
            .then(res => {
                document.getElementById("fullName").value = res.fullName;
                document.getElementById("address").value = res.address;
                document.getElementById("phoneNumber").value = res.phoneNumber;
                document.getElementById("birthDate").value = res.birthDate;
                document.getElementById("dni").value = res.dni;
                document.getElementById("id").value = "";
            })
            .catch(res => {
                alert(`No existe ningún cliente con el id ${id}`);
                document.getElementById("id").value = "";
            })
    }
});

botonAñadirCliente.addEventListener('click', (e) => {
    e.preventDefault();
    eliminaTabla();
    let dni = document.getElementById("dni").value;
    if (dni == "") {
        alert("El campo dni no puede estar vacío");
    }
    else {
        let fullName = document.getElementById("fullName").value;
        let address = document.getElementById("address").value;
        let phoneNumber = document.getElementById("phoneNumber").value;
        let birthDate = document.getElementById("birthDate").value;
        fetch('http://127.0.0.1:8080/api/customer', {
            method: 'POST',
            body: JSON.stringify({
                fullName: fullName,
                address: address,
                phoneNumber: phoneNumber,
                birthDate: birthDate,
                dni: dni
            }),
            headers: {
                "Content-type": "application/json"
            }
        })
            .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
            .then(res => {
                alert("El cliente se ha añadido de forma correcta");
                resetFormulario();
            })
            .catch(error => {
                console.log(error)
                if (error.status == 409) {
                    alert('Ya existe un cliente con ese dni');
                }
                else {
                    alert('Se ha producido un error inesperado.');
                }
            })
    }
})

const eliminaTabla = () => {
    let tabla = document.getElementById("clientes");
    if (tabla != null) {
        tabla.parentNode.removeChild(tabla);
    }
}

const aniadePrimeraFila = (tabla) => {
    // Creamos las celdas de la cabecera y le damos su valor
    let id = document.createElement("th");
    id.appendChild(document.createTextNode("Id"));
    let fullName = document.createElement("th");
    fullName.appendChild(document.createTextNode("Nombre completo"));
    let address = document.createElement("th");
    address.appendChild(document.createTextNode("Dirección"));
    let phoneNumber = document.createElement("th");
    phoneNumber.appendChild(document.createTextNode("Teléfono"));
    let birthDate = document.createElement("th");
    birthDate.appendChild(document.createTextNode("Fecha de nacimiento"));
    let dni = document.createElement("th");
    dni.appendChild(document.createTextNode("DNI"));
    // Creamos la fila y le añadimmos las celdas
    let fila = document.createElement("tr");
    fila.appendChild(id);
    fila.appendChild(fullName);
    fila.appendChild(address);
    fila.appendChild(phoneNumber);
    fila.appendChild(birthDate);
    fila.appendChild(dni);
    // Añadimos la fila a la tabla
    tabla.appendChild(fila);
}

const aniadeFila = (customer, pos, tabla) => {
    // Añadimos la fila a la tabla
    let fila = tabla.insertRow(pos);
    // Le añadimos las celdas a la fila
    let id = fila.insertCell(0);
    let fullName = fila.insertCell(1);
    let address = fila.insertCell(2);
    let phoneNumber = fila.insertCell(3);
    let birthDate = fila.insertCell(4);
    let dni = fila.insertCell(5);
    // Le añadimos el contenido a las celdas
    id.innerHTML = customer.id;
    fullName.innerHTML = customer.fullName;
    address.innerHTML = customer.address;
    phoneNumber.innerHTML = customer.phoneNumber;
    birthDate.innerHTML = customer.birthDate;
    dni.innerHTML = customer.dni;
}

const resetFormulario = () => {
    document.getElementById("fullName").value = "";
    document.getElementById("address").value = "";
    document.getElementById("phoneNumber").value = "";
    document.getElementById("birthDate").value = "";
    document.getElementById("dni").value = "";
}