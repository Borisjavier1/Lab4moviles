package com.example.models
import java.io.Serializable
class HistorialAlumno : Serializable{

    var anio_ciclo:Int = 0
    var ciclo:Int = 0
    var curso:Int = 0
    var estado:Int = 0
    var grupo:Int = 0
    var id:Int = 0
    var nombre_curso: String = ""
    var nota: Int = 0
    var numero_ciclo: String = ""
    var numero_grupo: String = ""
    var position: Int? = null


    internal constructor(anio_ciclo: Int, ciclo: Int, curso: Int, estado: Int, grupo: Int, id: Int, nombre_curso: String, nota: Int, numero_ciclo: String,numero_grupo: String ) {
        this.anio_ciclo = anio_ciclo
        this.ciclo = ciclo
        this.curso = curso
        this.estado = estado
        this.grupo = grupo
        this.id = id
        this.nombre_curso = nombre_curso
        this.nota = nota
        this.numero_ciclo = numero_ciclo
        this.numero_grupo = numero_grupo
    }
}