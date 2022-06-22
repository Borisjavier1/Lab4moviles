package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Usuarios {
    var client = OkHttpClient()
    var url = "http://192.168.0.102:8080/backend_moviles/api/sistema/"
    private var usuariosAPI : ArrayList<UsuarioAPIItem> = ArrayList<UsuarioAPIItem>()

    init{
        /*addUsuario(Usuario("123","123",1)) //Est
        addUsuario(Usuario("900","900",1)) //Est
        addUsuario(Usuario("555","555",2)) //Prof
        addUsuario(Usuario("000","000",3)) //Adminin
        addUsuario(Usuario("444","444",4)) //Matri*/
    }



    private object HOLDER {
        val INSTANCE = Usuarios()
    }

    companion object {
        val instance: Usuarios by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addUsuario(usuario: UsuarioAPIItem){
        usuariosAPI?.add(usuario)
    }

    fun getUsuario(cedula: String): UsuarioAPIItem? {
        for (p: UsuarioAPIItem in usuariosAPI!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getUsuario(): ArrayList<UsuarioAPIItem>{
        return this.usuariosAPI
    }


    fun deleteUsuario(position: Int){
        usuariosAPI!!.removeAt(position)
    }

    fun editProfesor(p: UsuarioAPIItem, position: Int){
        var aux = usuariosAPI!!.get(position)
        aux.cedula = p.cedula
        aux.clave = p.clave
        aux.rol = p.rol
        aux.id = p.id
    }

    fun login(user: String?, password: String?): Boolean{
        get()
        for(p: UsuarioAPIItem in usuariosAPI!!){
            if(p.cedula.equals(user) && p.clave.equals(password)){
                return true
            }
        }
        return false
    }
    fun loginP(user: String?, password: String?): UsuarioAPIItem?{
        for(p: UsuarioAPIItem in usuariosAPI!!){
            if(p.cedula.equals(user) && p.clave.equals(password)){
                return p
            }
        }
        return null
    }

    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerUsuario")
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
                var entidadJson = gson?.fromJson<UsuarioAPI>(valor, UsuarioAPI::class.java)
                usuariosAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}