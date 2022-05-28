package com.example.models

class Grupos {
    private var grupos: ArrayList<Grupo> = ArrayList<Grupo>()
    init{
        addGrupo(Grupo("666","123",10,"martes 3 pm","555"))
        addGrupo(Grupo("777","123",11,"martes 7 pm","555"))

    }

    private object HOLDER {
        val INSTANCE = Cursos()
    }

    companion object {
        val instance: Cursos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addGrupo(grupo: Grupo){
        grupos?.add(grupo)
    }

    fun getGrupo(codigo: String): Grupo? {
        for (p: Grupo in grupos!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getGrupos(): ArrayList<Grupo>{
        return this.grupos!!
    }


    fun deleteGrupo(position: Int){
        grupos!!.removeAt(position)
    }

    fun editGrupo(p: Grupo, position: Int){
        var aux = grupos!!.get(position)
        aux.codigo = p.codigo
        aux.cursoCodigo = p.cursoCodigo
        aux.numero = p.numero
        aux.horario = p.horario
        aux.cedulaProfesor = p.cedulaProfesor
    }
}