package com.example.models

class Matriculas private constructor() {
    private var matriculas: ArrayList<Matricula> = ArrayList<Matricula>()

    init{
        addMatricula(Matricula("666","123",0,"Reprobado","123"))
        addMatricula(Matricula("777","888",0,"Reprobado","123"))
        addMatricula(Matricula("777","900",0,"Reprobado","123"))
        addMatricula(Matricula("666","123",0,"Reprobado","456"))

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

    fun getMatriculasStudent(estudiante: String?): ArrayList<Matricula>{
        var grupos2: ArrayList<Matricula> = ArrayList<Matricula>()
        for( item in this.matriculas){
            if(item.cedEstudiante == estudiante){
                grupos2.add(item)
            }
        }
        return grupos2
    }

    fun getMatriculasStudentCiclo(estudiante: String?, ciclo: String?): ArrayList<Matricula>{
        var grupos2: ArrayList<Matricula> = ArrayList<Matricula>()
        for( item in this.matriculas){
            if(item.cedEstudiante == estudiante && item.codCiclo == ciclo){
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
        //aux.cedEstudiante = p.cedEstudiante
       // aux.codGrupo = p.codGrupo
        aux.nota = p.nota
        aux.estado = p.estado
       // aux.codCiclo = p.codCiclo
    }
}