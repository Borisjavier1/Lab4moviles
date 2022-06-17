package com.example.models

import java.io.Serializable
class ProfesorAPIItem : Serializable{
    var cedula: String = ""
    var email: String = ""
    var guia: Int = 0
    var id: Int = 0
    var nombre: String = ""
    var telefono: String = ""
    var position: Int? = null

    internal constructor(cedula: String, email: String, guia: Int, id: Int, nombre: String, telefono: String) {
        this.cedula = cedula
        this.email = email
        this.guia = guia
        this.id = id
        this.nombre = nombre
        this.telefono = telefono
    }
}