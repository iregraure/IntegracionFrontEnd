// Guardamos los botones en variables
let botonListarClientes = document.getElementById("lista");
let botonMostrarCliente = document.getElementById("cliente");
let botonAñadirCliente = document.getElementById("aniade");
let botonResetFormulario = document.getElementById("reset");
let botonEliminarCliente = document.getElementById("delete");
let botonActualizarCliente = document.getElementById("update");
// Guardamos el div donde vamos a mostrar los resultados en una variable
let divResultados = document.getElementById("resultado");

// Funcionamiento del botón para mostrar la lista de los clientes
botonListarClientes.addEventListener('click', (e) => {
    // Evitamos el comportamiento por defecto
    e.preventDefault();
    // Eliminamos la tabla con el listado de clientes
    eliminaTabla();
    // Borramos los datos del formulario
    resetFormulario();
    // Recuperamos los datos del servidor
    fetch('http://127.0.0.1:8080/api/customer')
        .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
        .then(res => res.json())
        .then(res => {
            // Creamos la tabla y le añadimos un id
            let tabla = document.createElement("table");
            tabla.id = "clientes";
            // Añadimos la cabecera de la tabla
            aniadePrimeraFila(tabla);
            // Creamos una variable en la que vamos a guardar la posición en la que hay que añadir la siguiente fila
            let pos = 1;
            // Recorremos los datos que hemos recuperado del servidor, los añadimos a la tabla e incrementamos la posición de la siguiente fila
            res.forEach(customer => {
                aniadeFila(customer, pos, tabla);
                pos++;
            });
            // Cuando hemos terminado de crear la tabla la añadimos al HTML
            divResultados.appendChild(tabla);
        })
        // Vamos a controlar los errores
        .catch(error => {
            // Si nos devuelve un 404 es que no existen clientes
            if (error.status == 404) {
                alert("No existen clientes");
                // Cualquier otro error es inesperado
            } else {
                alert("Se ha producido un error inesperado");
            }
        })
});

// Funcionamiento del botón para mostrar el cliente con el id que se le pase
botonMostrarCliente.addEventListener('click', (e) => {
    // Evitamos el comportamiento por defecto
    e.preventDefault();
    // Eliminamos la tabla con el listado de clientes
    eliminaTabla();
    // Obtenemos el id que ha introducido el usuario
    let id = document.getElementById("idGet").value;
    // Si no se ha introducido ningún id mostramos un mensaje de error
    if (id == "") {
        alert("Debe introducir un id");
    } else {
        // Si se ha introducido un id, recuperamos el cliente del servidor
        fetch(`http://127.0.0.1:8080/api/customer/${id}`)
            .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
            .then(res => res.json())
            .then(res => {
                // Mostramos los datos del cliente en sus correspondientes campos del formulario
                document.getElementById("fullName").value = res.fullName;
                document.getElementById("address").value = res.address;
                document.getElementById("phoneNumber").value = res.phoneNumber;
                document.getElementById("birthDate").value = res.birthDate;
                document.getElementById("dni").value = res.dni;
                // Borramos el id que ha introducido el usuario
                document.getElementById("idGet").value = "";
            })
            // Vamos a controlar los errores
            .catch(res => {
                // Si nos devuelve un 404 es que no existe ningún cliente con el id introducido
                if (error.status == 404) {
                    alert(`No existe ningún cliente con el id ${id}`);
                    document.getElementById("idGet").value = "";
                    // Cualquier otro error es inesperado
                } else {
                    alert("Se ha producido un error inesperado");
                    document.getElementById("idGet").value = "";
                }
            })
    }
});

// Comportamiento del botón para añadir un cliente
botonAñadirCliente.addEventListener('click', (e) => {
    // Evitamos el comportamiento por defecto
    e.preventDefault();
    // Eliminamos la tabla con el listado de clientes
    eliminaTabla();
    // Recuperamos el dni que ha introducido el usuario
    let dni = document.getElementById("dni").value;
    // Si el dni está vacío mostramos un mensaje de error
    if (dni == "") {
        alert("El campo dni no puede estar vacío");
    } else {
        // Si se ha introducido un dni, recuperamos el resto de campos del formulario
        let fullName = document.getElementById("fullName").value;
        let address = document.getElementById("address").value;
        let phoneNumber = document.getElementById("phoneNumber").value;
        let birthDate = document.getElementById("birthDate").value;
        // Realizamos la petición POST al servidor
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
                // Si el cliente se crea, mostramos un mensaje indicándolo
                alert("El cliente se ha añadido de forma correcta");
                resetFormulario();
            })
            // Si no se puede crear el cliente
            .catch(error => {
                // Si el error es el 409 quiere decir que ya existe un cliente con ese dni
                if (error.status == 409) {
                    alert('Ya existe un cliente con ese dni');
                    // Cualquier otro error es inesperado
                } else {
                    alert('Se ha producido un error inesperado.');
                }
            })
    }
})

// Comportamiento del botón para borrar los datos del formulario
botonResetFormulario.addEventListener('click', (e) => {
    // Evitamos el comportamiento por defecto
    e.preventDefault();
    // Llamamos a la función que va a borrar los datos del formulario
    resetFormulario();
})

// Comportamiento del botón para eliminar un cliente
botonEliminarCliente.addEventListener('click', (e) => {
    // Evitamos el comportamiento por defecto
    e.preventDefault();
    // Eliminamos la tabla con el listado de clientes
    eliminaTabla();
    // Eliminamos los datos del fromulario
    resetFormulario();
    // Recuperamos el id que ha introducido el usuario
    let id = document.getElementById("idDelete").value;
    // Si no se ha introducido ningún id mostraremos un mensaje de error
    if (id == "") {
        alert("Debe introducir un id");
    } else {
        // Si se ha introducido un id, realizamos la petición DELETE al serrvidor
        fetch(`http://127.0.0.1:8080/api/customer/${id}`, {
                method: 'DELETE'
            })
            .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
            // Si se elimina el cliente mostramos un mensaje y borramos el id introducido
            .then(res => {
                alert("Se ha eliminado el cliente");
                document.getElementById("idDelete").value = "";
            })
            // Si no se ha podido eliminar el cliente
            .catch(error => {
                // Si devuelve el error 404 es que no existe ningún cliente con ese id
                if (error.status == 404) {
                    alert(`No existe ningún cliente con el id ${id}`);
                    document.getElementById("idDelete").value = "";
                    // Cualquier otro error es inesperado
                } else {
                    alert('Se ha producido un error inesperado.');
                    document.getElementById("idDelete").value = "";
                }
            })
    }
})

// Comportamiento del botón para actualizar un cliente
botonActualizarCliente.addEventListener('click', (e) => {
    // Evitamos el comportamiento por defecto
    e.preventDefault();
    // Eliminamos la tabla con los datos de todos los clientes
    eliminaTabla();
    // Recuperamos el id que ha introducido el usuario
    let id = document.getElementById("idUpdate").value;
    // Si no se introduce ningún id, mostramos un mensaje de error
    if (id == "") {
        alert("Debe introducir un id");
    } else {
        // Comprobamos el valor del todos los campos. Si no se ha introducido nada almacenamos "null"
        // No comprobamos el valor del dni porque no se permite que se modifique
        let fullName = document.getElementById("fullName").value;
        if (fullName == "") {
            fullName = null;
        }
        let address = document.getElementById("address").value;
        if (address == "") {
            address = null;
        }
        let phoneNumber = document.getElementById("phoneNumber").value;
        if (phoneNumber == "") {
            phoneNumber = null;
        }
        let birthDate = document.getElementById("birthDate").value;
        if (birthDate == "") {
            birthDate = null;
        }
        // Realizamos una petición PUT al servidor
        fetch(`http://127.0.0.1:8080/api/customer/${id}`, {
                method: 'PUT',
                body: JSON.stringify({
                    id: id,
                    fullName: fullName,
                    address: address,
                    phoneNumber: phoneNumber,
                    birthDate: birthDate
                }),
                headers: {
                    "Content-type": "application/json"
                }
            })
            .then(res => res.ok ? Promise.resolve(res) : Promise.reject(res))
            .then(res => {
                // Si se actualiza el cliente mostramos un mensaje
                alert("El cliente se ha actualizado de forma correcta");
                // Borramos los datos del formulario
                resetFormulario();
                // Borramos el id que ha introducido el usuario
                document.getElementById("idUpdate").value = "";
            })
            // Si no se puede actualizar el cliente
            .catch(error => {
                // Si devuelve el error 404 es que no existe ningún cliente con ese id
                if (error.status == 404) {
                    alert(`No existe ningún cliente con el id ${id}`);
                    document.getElementById("idUpdate").value = "";
                    // Cualquier otro error es inesperado
                } else {
                    alert('Se ha producido un error inesperado.');
                }
            })
    }
})

// Función para eliminar la tabla con los datos de todos los clientes
const eliminaTabla = () => {
    // Recuperamos el elemento que tiene el id "clientes"
    let tabla = document.getElementById("clientes");
    // Si la tabla existe, se elimina
    if (tabla != null) {
        tabla.parentNode.removeChild(tabla);
    }
}

// Función que añade la cabecera de la tabla
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

// Función que añade una fila de datos a la tabla
// Hay que pasarle el objeto que vamos a añadir, la posición de la fila y la tabla
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

// Función que va a eliminar los campos del formulario
const resetFormulario = () => {
    // Para cada uno de los input del formulario le ponemos su valor a ""
    document.getElementById("fullName").value = "";
    document.getElementById("address").value = "";
    document.getElementById("phoneNumber").value = "";
    document.getElementById("birthDate").value = "";
    document.getElementById("dni").value = "";
}