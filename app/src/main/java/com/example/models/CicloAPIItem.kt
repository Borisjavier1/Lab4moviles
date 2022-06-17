package com.example.models

import java.io.Serializable
class CicloAPIItem : Serializable{
    var actual: Int = 0
    var anio: Int = 0
    var fecha_fin: String = ""
    var fecha_inicio: String = ""
    var id: Int = 0
    var numero: Int = 0
    var position: Int? = null

    internal constructor(actual: Int, anio: Int, fecha_fin: String, fecha_inicio: String, id: Int, numero: Int) {
        this.actual = actual
        this.anio = anio
        this.fecha_fin = fecha_fin
        this.fecha_inicio = fecha_inicio
        this.id = id
        this.numero = numero
    }
}