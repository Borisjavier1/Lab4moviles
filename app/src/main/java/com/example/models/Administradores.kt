package com.example.models

import com.example.peopleapp.R
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Administradores private constructor() {
    private var administradores: ArrayList<Administrador> = ArrayList<Administrador>()
    private var administradoresAPI : ArrayList<AdministradorAPIItem> = ArrayList<AdministradorAPIItem>()
    var client = OkHttpClient()
    var url = "http://192.168.0.3:8080/backend_moviles/api/sistema/"

    init{


    }

    private object HOLDER {
        val INSTANCE = Administradores()
    }

    companion object {
        val instance: Administradores by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addAdministrador(Administrado: Administrador){
        administradores?.add(Administrado)
    }

    fun getAdministrador(cedula: String): Administrador? {
        for (p: Administrador in administradores!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getAdministradores(): ArrayList<AdministradorAPIItem> {
        get()
        return this.administradoresAPI
    }


    fun deleteAdministrador(position: Int){
        administradores!!.removeAt(position)
    }

    fun editAdministrador(p: Administrador, position: Int){
        var aux = administradores!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
    }
    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerAdministrador")
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
                var entidadJson = gson?.fromJson<AdministradorAPI>(valor, AdministradorAPI::class.java)
                administradoresAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

}