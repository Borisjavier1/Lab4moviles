package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Grupos {
    var client = OkHttpClient()
    var url = "http://192.168.0.2:8080/backend_moviles/api/sistema/"
    private var grupos: ArrayList<Grupo> = ArrayList<Grupo>()
    private var gruposAPI : ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
    init{

    }

    private object HOLDER {
        val INSTANCE = Grupos()
    }

    companion object {
        val instance: Grupos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addGrupo(grupo: GrupoAPIItem){
        gruposAPI?.add(grupo)
    }

    fun getGrupo(codigo: String): Grupo? {
        for (p: Grupo in grupos!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getGrupos(): ArrayList<GrupoAPIItem> {
        get()
        return this.gruposAPI
    }

    fun getGruposProfesor(ced: String?): ArrayList<GrupoAPIItem> {
        get()
         var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
         for( item in this.gruposAPI){
             if(item.nombre_profesor == ced){
                 grupos2.add(item)
             }
         }
        return grupos2
    }
    fun getGruposCurso(Id: Int):ArrayList<GrupoAPIItem> {
        get()
        var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
        for( item in this.gruposAPI){
            if(item.curso == Id){
                grupos2.add(item)
            }
        }
        return grupos2
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
    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerGrupo")
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                var entidadJson = gson?.fromJson<GrupoAPI>(valor, GrupoAPI::class.java)
                gruposAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}