package com.example.models

import java.io.Serializable

class CursoAPIItem : Serializable {
    var anio_ciclo: Int = 0
    var carrera: Int = 0
    var ciclo: Int = 0
    var codigo: String = ""
    var creditos: Int = 0
    var horas_sem: Int = 0
    var id: Int = 0
    var nombre: String = ""
    var nombre_carrera: String = ""
    var numero_ciclo: Int = 0
    var position: Int? = null

    constructor(
        anio_ciclo: Int,
        carrera: Int,
        ciclo: Int,
        codigo: String,
        creditos: Int,
        horas_sem: Int,
        id: Int,
        nombre: String,
        nombre_carrera: String,
        numero_ciclo: Int
    ) {
        this.anio_ciclo = anio_ciclo
        this.carrera = carrera
        this.ciclo = ciclo
        this.codigo = codigo
        this.creditos = creditos
        this.horas_sem = horas_sem
        this.id = id
        this.nombre = nombre
        this.nombre_carrera = nombre_carrera
        this.numero_ciclo = numero_ciclo
    }

    constructor(
        carrera: Int,
        ciclo: Int,
        codigo: String,
        creditos: Int,
        horas_sem: Int,
        id: Int,
        nombre: String,

    ) {
        this.carrera = carrera
        this.ciclo = ciclo
        this.codigo = codigo
        this.creditos = creditos
        this.horas_sem = horas_sem
        this.id = id
        this.nombre = nombre
    }
}