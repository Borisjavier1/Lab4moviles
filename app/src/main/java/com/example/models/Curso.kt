package com.example.models
import java.io.Serializable
class Curso : Serializable{

    var codigo:String = ""
    var nombre:String = ""
    var creditos:Int = 0
    var horas: Int = 0
    var carreraCodigo:String = ""
    var cicloCodigo:String = ""

    internal constructor(codigo: String, nombre: String, creditos: Int, horas: Int, carreraCodigo: String, cicloCodigo: String) {
        this.codigo = codigo
        this.nombre = nombre
        this.creditos = creditos
        this.horas = horas
        this.carreraCodigo = carreraCodigo
        this.cicloCodigo = cicloCodigo
    }
}