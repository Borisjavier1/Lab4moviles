package com.example.models

import java.io.Serializable

class Carrera : Serializable{

    var codigo:String = ""
    var nombre:String = ""
    var titulo:String = ""
    var position: Int? = null

    constructor(codigo: String, nombre: String, titulo: String) {
        this.codigo = codigo
        this.nombre = nombre
        this.titulo = titulo
    }
}