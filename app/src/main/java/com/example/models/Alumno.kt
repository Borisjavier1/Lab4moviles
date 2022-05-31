package com.example.models
import java.io.Serializable
class Alumno : Serializable {
    var cedula:String = ""
    var nombre:String = ""
    var telefono:String = ""
    var email:String = ""
    var fecha:String = ""
    var CarreraCodigo:String = ""
    var position: Int? = null

    internal constructor(cedula: String, nombre: String, telefono: String, email: String, fecha: String, CarreraCodigo: String) {
        this.cedula = cedula
        this.nombre = nombre
        this.telefono = telefono
        this.email = email
        this.fecha = fecha
        this.CarreraCodigo = CarreraCodigo
    }
}