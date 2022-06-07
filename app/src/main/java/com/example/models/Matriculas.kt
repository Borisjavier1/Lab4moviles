package com.example.models

import com.example.peopleapp.R

class Matriculas private constructor() {
    private var matriculas: ArrayList<Matricula> = ArrayList<Matricula>()

    init{
        addMatricula(Matricula("666","000",0,"Reprobado"))
        addMatricula(Matricula("777","ppp",0,"Reprobado"))

    }

    private object HOLDER {
        val INSTANCE = Matriculas()
    }

    companion object {
        val instance: Matriculas by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addMatricula(matricula: Matricula){
        matriculas?.add(matricula)
    }



    fun getMatriculas(): ArrayList<Matricula>{
        return this.matriculas!!
    }

    fun getMatriculasGroup(grupo: String?): ArrayList<Matricula>{
        var grupos2: ArrayList<Matricula> = ArrayList<Matricula>()
        for( item in this.matriculas){
            if(item.codGrupo == grupo){
                grupos2.add(item)
            }
        }
        return grupos2
    }

    fun deleteMatricula(position: Int){
        matriculas!!.removeAt(position)
    }

    fun editMatricula(p: Matricula, position: Int){
        var aux = matriculas!!.get(position)
        aux.cedEstudiante = p.cedEstudiante
        aux.codGrupo = p.codGrupo
        aux.nota = p.nota
        aux.estado = p.estado
    }
}