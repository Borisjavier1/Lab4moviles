package com.example.models

import java.io.Serializable

class UsuarioAPIItem : Serializable {
    var cedula:String = ""
    var clave:String = ""
    var id:Int = 0
    var rol:Int = 0
    var position: Int? = null

    internal constructor(cedula: String, clave: String, rol: Int, id:Int) {
        this.cedula = cedula
        this.clave = clave
        this.rol = rol
        this.id = id
    }
}