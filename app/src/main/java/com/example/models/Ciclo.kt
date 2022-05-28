package com.example.models
import java.io.Serializable
class Ciclo : Serializable{
    var codigo:String = ""
    var numero:Int = 0
    var anio:Int = 0
    var fechaInicio:String =""
    var fechaFin:String =""

    internal constructor(codigo: String, numero: Int, anio: Int, fechaInicio: String, fechaFin: String) {
        this.codigo = codigo
        this.numero = numero
        this.anio = anio
        this.fechaInicio = fechaInicio
        this.fechaFin = fechaFin
    }
}