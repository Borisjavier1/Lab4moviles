package com.example.models

class Alumnos {
    private var alumnos: ArrayList<Alumno> = ArrayList<Alumno>()
    init{
        addAlumno(Alumno("000", "Pedro","2234-7865","pedro@gmail.com","22-02-2000","123"))
        addAlumno(Alumno("010", "Yoschua","5432-1123","yoschua@gmail.com","10-05-1999","123"))

    }

    private object HOLDER {
        val INSTANCE = Alumnos()
    }

    companion object {
        val instance: Alumnos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun  addAlumno(alumno: Alumno){
        alumnos?.add(alumno)
    }

    fun getAlumno(cedula: String): Alumno? {
        for (p: Alumno in alumnos!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getAlumnos(): ArrayList<Alumno>{
        return this.alumnos!!
    }


    fun deleteAlumno(position: Int){
        alumnos!!.removeAt(position)
    }

    fun editAlumnos(p: Alumno, position: Int){
        var aux = alumnos!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
        aux.telefono = p.telefono
        aux.email = p.email
        aux.fecha = p.fecha
        aux.CarreraCodigo = p.CarreraCodigo
    }
}