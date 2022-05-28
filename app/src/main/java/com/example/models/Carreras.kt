package com.example.models

class Carreras private constructor() {

    private var carreras: ArrayList<Carrera> = ArrayList<Carrera>()

    init{
        addCarrera(Carrera("123","informatica","ing sistemas"))

    }

    private object HOLDER {
        val INSTANCE = Carreras()
    }

    companion object {
        val instance: Carreras by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addCarrera(carrera: Carrera){
        carreras?.add(carrera)
    }

    fun getCarrera(codigo: String): Carrera? {
        for (p: Carrera in carreras!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getCarreras(): ArrayList<Carrera>{
        return this.carreras!!
    }


    fun deleteCarrera(position: Int){
        carreras!!.removeAt(position)
    }

    fun editCarrera(p: Carrera, position: Int){
        var aux = carreras!!.get(position)
        aux.codigo = p.codigo
        aux.nombre = p.nombre
        aux.titulo = p.titulo
    }
}