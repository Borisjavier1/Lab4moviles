package com.example.models

import com.example.peopleapp.R

class Matriculadores private constructor() {
    private var matriculadores: ArrayList<Matriculador> = ArrayList<Matriculador>()

    init{
        addMatriculador(Matriculador("444","Pablo"))

    }

    private object HOLDER {
        val INSTANCE = Matriculadores()
    }

    companion object {
        val instance: Matriculadores by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addMatriculador(Matriculador: Matriculador){
        matriculadores?.add(Matriculador)
    }

    fun getMatriculador(cedula: String): Matriculador? {
        for (p: Matriculador in matriculadores!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getMatriculadores(): ArrayList<Matriculador>{
        return this.matriculadores!!
    }


    fun deleteMatriculador(position: Int){
        matriculadores!!.removeAt(position)
    }

    fun editMatriculador(p: Matriculador, position: Int){
        var aux = matriculadores!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
    }
}