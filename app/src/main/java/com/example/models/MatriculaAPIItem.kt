package com.example.models
import java.io.Serializable
class MatriculaAPIItem : Serializable{

    var id:Int = 0
    var id_alumno:Int = 0
    var id_ciclo: Int = 0
    var id_curso:Int = 0
    var id_grupo: Int = 0
    var position: Int? = null


    internal constructor(id: Int, estudiante: Int, curso: Int, grupo: Int, ciclo: Int) {
        this.id = id
        this.id_alumno = estudiante
        this.id_ciclo = ciclo
        this.id_curso = curso
        this.id_grupo = grupo
    }
}