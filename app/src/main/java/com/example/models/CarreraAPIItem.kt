package com.example.models

import java.io.Serializable
class CarreraAPIItem : Serializable{
    var codigo: String = ""
    var id: Int = 0
    var nombre: String = ""
    var titulo: String = ""
    var position: Int? = null

    internal constructor(codigo: String,  id: Int, nombre: String, titulo: String) {
        this.codigo = codigo
        this.nombre = nombre
        this.titulo = titulo
        this.id = id
    }
}