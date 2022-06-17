package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Ciclos {
    var client = OkHttpClient()
    var url = "http://192.168.0.102:8080/backend_moviles/api/sistema/"
    private var ciclos: ArrayList<Ciclo> = ArrayList<Ciclo>()
    private var ciclosAPI : ArrayList<CicloAPIItem> = ArrayList<CicloAPIItem>()
    init{
        get()

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

    fun getCiclos(): ArrayList<CicloAPIItem>{
        return this.ciclosAPI
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

    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerCiclo")
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
                var entidadJson = gson?.fromJson<CicloAPI>(valor, CicloAPI::class.java)
                ciclosAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}