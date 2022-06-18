package com.example.models

import java.io.Serializable

class AdministradorAPIItem : Serializable {
    var cedula:String = ""
    var nombre:String = ""
    var telefono:String = ""
    var email:String = ""
    var id:Int = 0
    var position: Int? = null

    internal constructor(id: Int, cedula: String, nombre: String, telefono: String, email: String) {
        this.cedula = cedula
        this.nombre = nombre
        this.telefono = telefono
        this.email = email
        this.id = id

    }
}