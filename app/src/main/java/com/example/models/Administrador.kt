package com.example.models
import java.io.Serializable
class Administrador : Serializable{

    var cedula:String = ""
    var nombre:String = ""
    var position: Int? = null


    internal constructor(cedula: String, nombre: String) {
        this.cedula = cedula
        this.nombre = nombre
    }
}