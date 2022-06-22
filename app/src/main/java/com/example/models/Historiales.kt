package com.example.models

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Historiales {
    var client = OkHttpClient()
    var url = "http://192.168.0.102:8080/backend_moviles/api/sistema/"
    private var historiales: ArrayList<HistorialAlumno> = ArrayList<HistorialAlumno>()
    private var gruposAPI : ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
    private var GrupoConsulta: ArrayList<GrupoConsulta2Item> = ArrayList<GrupoConsulta2Item>()
    private var idGlobal: Int = 0
    private var cicloActual: Int = 0
    private var flag: Int = 0
    init{

    }

    private object HOLDER {
        val INSTANCE = Historiales()
    }

    companion object {
        val instance: Historiales by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addGrupo(grupo: GrupoAPIItem){
        gruposAPI?.add(grupo)
    }



    fun getHistoriales(id: String?): ArrayList<HistorialAlumno> {
        if (id != null) {
            get(id)
        }
        return this.historiales
    }

    /*fun getGruposProfesor(ced: String?): ArrayList<GrupoAPIItem> {
        /*get()
        if (ced != null) {
            getCedProf(ced)
        }
         var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
         for( item in this.gruposAPI){
             if(item.profesor == idGlobal){
                 grupos2.add(item)
             }
         }
        return grupos2*/
        getCicloActual()
        getCedProf(ced)
        getGruposProf(idGlobal,cicloActual)
        var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
        for( item in this.GrupoConsulta){
            grupos2.add(GrupoAPIItem(item.ciclo, item.curso, item.numero, item.id,item.horario, item.profesor))
        }
        return grupos2
    }*/
    /*fun getGruposCurso(Id: Int):ArrayList<GrupoAPIItem> {
        get()
        var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
        for( item in this.gruposAPI){
            if(item.curso == Id){
                grupos2.add(item)
            }
        }
        return grupos2
    }*/

    fun deleteGrupo(position: Int){
        historiales!!.removeAt(position)
    }


    fun get(id:String) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarAlumnoConCursosCedula/"+id)
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error en grupos get"+e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                var entidadJson = gson?.fromJson<HistorialAlumnos>(valor, HistorialAlumnos::class.java)
                historiales = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

}