package com.example.models
import java.io.Serializable
 class AlumnoAPIItem : Serializable{
   var carrera: String = ""
   var cedula: String = ""
   var email: String = ""
   var fecha_nac: String = ""
   var id: Int = 0
   var nombre: String = ""
   var telefono: String = ""
   var position: Int? = null

    internal constructor(carrera: String, cedula: String, email: String, fecha_nac: String, id: Int, nombre: String, telefono: String) {
       this.cedula = cedula
       this.nombre = nombre
       this.telefono = telefono
       this.email = email
      this.carrera = carrera
       this.fecha_nac = fecha_nac
       this.id = id
    }
}