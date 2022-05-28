package com.example.models

class Profesores {
    private var profesores: ArrayList<Profesor> = ArrayList<Profesor>()
    init{
        addProfesor(Profesor("555","Susana","8909-9876","susana@gmail.com"))
        addProfesor(Profesor("222","Maria","8765-8798","maria@gmail.com"))

    }

    private object HOLDER {
        val INSTANCE = Cursos()
    }

    companion object {
        val instance: Cursos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addProfesor(profesor: Profesor){
        profesores?.add(profesor)
    }

    fun getProfesor(cedula: String): Profesor? {
        for (p: Profesor in profesores!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getProfesor(): ArrayList<Profesor>{
        return this.profesores!!
    }


    fun deleteProfesor(position: Int){
        profesores!!.removeAt(position)
    }

    fun editProfesor(p: Profesor, position: Int){
        var aux = profesores!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
        aux.telefono = p.telefono
        aux.email = p.email
    }
}