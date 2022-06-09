package com.example.models

class Ciclos {
    private var ciclos: ArrayList<Ciclo> = ArrayList<Ciclo>()
    init{
        addCiclo(Ciclo("123",1,2020,"10-02-2020","10-06-2020",1))
        addCiclo(Ciclo("456",2,2021,"10-02-2021","10-06-2021",0))

    }

    private object HOLDER {
        val INSTANCE = Ciclos()
    }

    companion object {
        val instance: Ciclos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun  addCiclo(ciclo: Ciclo){
        ciclos?.add(ciclo)
    }

    fun getCiclo(codigo: String): Ciclo? {
        for (p: Ciclo in ciclos!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getCiclos(): ArrayList<Ciclo>{
        return this.ciclos!!
    }


    fun deleteCiclo(position: Int){
        ciclos!!.removeAt(position)
    }

    fun editAlumnos(p: Ciclo, position: Int){
        var aux = ciclos!!.get(position)
        aux.codigo = p.codigo
        aux.numero = p.numero
        aux.anio = p.anio
        aux.fechaInicio = p.fechaInicio
        aux.fechaFin = p.fechaFin
        aux.actual = p.actual
    }
}