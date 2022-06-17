package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Profesores {
    var client = OkHttpClient()
    var url = "http://192.168.0.102:8080/backend_moviles/api/sistema/"
    private var profesores: ArrayList<Profesor> = ArrayList<Profesor>()
    private var profesoresAPI : ArrayList<ProfesorAPIItem> = ArrayList<ProfesorAPIItem>()
    init{
        get()

    }

    private object HOLDER {
        val INSTANCE = Profesores()
    }

    companion object {
        val instance: Profesores by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addProfesor(profesor: Profesor){
        profesores?.add(profesor)
    }

    fun getProfesor(cedula: String): Profesor? {
        for (p: Profesor in profesores!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getProfesor(): ArrayList<ProfesorAPIItem>{
        return this.profesoresAPI
    }


    fun deleteProfesor(position: Int){
        profesores!!.removeAt(position)
    }

    fun editProfesor(p: Profesor, position: Int){
        var aux = profesores!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
        aux.telefono = p.telefono
        aux.email = p.email
    }

    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerProfesor")
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
                var entidadJson = gson?.fromJson<ProfesorAPI>(valor, ProfesorAPI::class.java)
                profesoresAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}