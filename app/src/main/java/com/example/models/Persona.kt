package com.example.models

import java.io.Serializable


class Persona : Serializable {

    var user:String = ""
    var password:String = ""
    var nombre:String = ""
    var foto:Int = 0
    var position: Int? = null

    internal constructor(user:String, password:String, nombre:String, foto:Int){
        this.user = user
        this.password = password
        this.nombre = nombre
        this.foto = foto
    }

}