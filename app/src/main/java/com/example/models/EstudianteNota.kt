package com.example.models
import java.io.Serializable
class EstudianteNota : Serializable {
    var cedula:String = ""
    var grupo:Int = 0
    var id_alumno:Int = 0
    var id_historial:Int = 0
    var nombre:String = ""
    var nota:Int = 0
    var position: Int? = null

    constructor(cedula: String, grupo: Int, id_alumno: Int, id_historial: Int, nombre: String, nota: Int) {
        this.cedula = cedula
        this.grupo = grupo
        this.id_alumno = id_alumno
        this.id_historial = id_historial
        this.nombre = nombre
        this.nota = nota
    }
}