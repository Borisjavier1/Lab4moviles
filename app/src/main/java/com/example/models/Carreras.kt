package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Carreras {
    var client = OkHttpClient()
    var url = "http://192.168.0.9:8080/backend_moviles/api/sistema/"
    private var carreras: ArrayList<Carrera> = ArrayList<Carrera>()
    private var carrerasAPI : ArrayList<CarreraAPIItem> = ArrayList<CarreraAPIItem>()
    init{


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

    fun getCarreras(): ArrayList<CarreraAPIItem>{
        get()
        return this.carrerasAPI
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

    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerCarrera")
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
                var entidadJson = gson?.fromJson<CarreraAPI>(valor, CarreraAPI::class.java)
                carrerasAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}