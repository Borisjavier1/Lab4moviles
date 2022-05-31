package com.example.models
import java.io.Serializable
class Grupo : Serializable {
    var codigo:String = ""
    var cursoCodigo:String = ""
    var numero:Int = 0
    var horario:String = ""
    var cedulaProfesor:String = ""
    var position: Int? = null

    constructor(codigo: String, cursoCodigo: String, numero: Int, horario: String, cedulaProfesor: String) {
        this.codigo = codigo
        this.cursoCodigo = cursoCodigo
        this.numero = numero
        this.horario = horario
        this.cedulaProfesor = cedulaProfesor
    }
}