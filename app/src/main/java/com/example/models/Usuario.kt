package com.example.models
import java.io.Serializable
class Usuario : Serializable{
    var cedula:String = ""
    var contrasena:String = ""
    var rol:Int = 0

    constructor(cedula: String, contrasena: String, rol: Int) {
        this.cedula = cedula
        this.contrasena = contrasena
        this.rol = rol
    }
}