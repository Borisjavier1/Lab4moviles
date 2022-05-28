package com.example.models

import com.example.peopleapp.R

class Administradores private constructor() {
    private var administradores: ArrayList<Administrador> = ArrayList<Administrador>()

    init{
        addAdministrador(Administrador("123","juan"))

    }

    private object HOLDER {
        val INSTANCE = Administradores()
    }

    companion object {
        val instance: Administradores by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addAdministrador(Administrado: Administrador){
        administradores?.add(Administrado)
    }

    fun getAdministrador(cedula: String): Administrador? {
        for (p: Administrador in administradores!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getAdministradores(): ArrayList<Administrador>{
        return this.administradores!!
    }


    fun deleteAdministrador(position: Int){
        administradores!!.removeAt(position)
    }

    fun editAdministrador(p: Administrador, position: Int){
        var aux = administradores!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
    }
}