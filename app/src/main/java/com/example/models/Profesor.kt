package com.example.models
import java.io.Serializable
class Profesor : Serializable {

    var cedula:String = ""
    var nombre:String = ""
    var telefono:String = ""
    var email:String = ""
    var guia:Int = 0
    var position: Int? = null

    constructor(cedula: String, nombre: String, telefono: String, email: String, guia: Int) {
        this.cedula = cedula
        this.nombre = nombre
        this.telefono = telefono
        this.email = email
        this.guia = guia
    }
}