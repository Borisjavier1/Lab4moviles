package com.example.models

class Cursos {
    private var cursos: ArrayList<Curso> = ArrayList<Curso>()
    init{
        addCurso(Curso("123","Paradigmas",4,8,"123","123"))

    }

    private object HOLDER {
        val INSTANCE = Cursos()
    }

    companion object {
        val instance: Cursos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun  addCurso(curso: Curso){
        cursos?.add(curso)
    }

    fun getCurso(codigo: String): Curso? {
        for (p: Curso in cursos!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getCursos(): ArrayList<Curso>{
        return this.cursos!!
    }

    fun getCursosCicloCarrera(carrera: String?, ciclo: String?):ArrayList<Curso> {
        var grupos2: ArrayList<Curso> = ArrayList<Curso>()
        for( item in this.cursos ){
            if(item.carreraCodigo == carrera && item.cicloCodigo == ciclo){
                grupos2.add(item)
            }
        }
        return grupos2
    }



    fun deleteCurso(position: Int){
        cursos!!.removeAt(position)
    }

    fun editAlumnos(p: Curso, position: Int){
        var aux = cursos!!.get(position)
        aux.codigo = p.codigo
        aux.nombre = p.nombre
        aux.creditos = p.creditos
        aux.horas = p.horas
        aux.carreraCodigo = p.carreraCodigo
        aux.cicloCodigo = p.cicloCodigo
    }
}