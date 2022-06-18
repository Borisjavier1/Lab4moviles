package com.example.models

import java.io.Serializable

class GrupoAPIItem : Serializable {
    var anio_ciclo: Int = 0
    var ciclo: Int = 0
    var curso: Int = 0
    var horario: String = ""
    var id: Int = 0
    var nombre_curso: String = ""
    var nombre_profesor: String = ""
    var numero: Int = 0
    var numero_ciclo: Int = 0
    var profesor: Int = 0

    var position: Int? = null

    constructor(
        anio_ciclo: Int,
        ciclo: Int,
        curso: Int,
        horario: String,
        id: Int,
        nombre_curso: String,
        nombre_profesor: String,
        numero: Int,
        numero_ciclo: Int,
        profesor: Int
    ) {
        this.anio_ciclo = anio_ciclo
        this.ciclo = ciclo
        this.curso = curso
        this.horario = horario
        this.id = id
        this.nombre_curso = nombre_curso
        this.nombre_profesor = nombre_profesor
        this.numero = numero
        this.numero_ciclo = numero_ciclo
        this.profesor = profesor
    }

    constructor(
        ciclo: Int,
        curso: Int,
        numero: Int,
        id: Int,
        horario: String,
        profesor: Int,

    ) {
        this.ciclo = ciclo
        this.curso = curso
        this.numero = numero
        this.id = id
        this.horario = horario
        this.profesor = profesor
    }

    constructor(
        curso: Int,
        numero: Int,
        horario: String,
        profesor: Int,

        ) {
        this.curso = curso
        this.numero = numero
        this.id = id
        this.horario = horario
        this.profesor = profesor
    }
}