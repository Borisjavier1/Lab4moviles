package com.example.models
import java.io.Serializable
class Profesor : Serializable {

    var cedula:String = ""
    var nombre:String = ""
    var telefono:String = ""
    var email:String = ""


    constructor(cedula: String, nombre: String, telefono: String, email: String) {
        this.cedula = cedula
        this.nombre = nombre
        this.telefono = telefono
        this.email = email
    }
}