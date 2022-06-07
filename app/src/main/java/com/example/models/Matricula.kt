package com.example.models
import java.io.Serializable
class Matricula : Serializable{

    var codGrupo:String = ""
    var cedEstudiante:String = ""
    var nota:Int = 0
    var estado:String = ""
    var position: Int? = null


    internal constructor(codGrupo: String, cedEstudiante: String, nota: Int, estado: String) {
        this.codGrupo = codGrupo
        this.cedEstudiante = cedEstudiante
        this.nota = nota
        this.estado = estado
    }
}