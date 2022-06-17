package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Alumnos {
    var client = OkHttpClient()
    var url = "http://192.168.0.2:8080/backend_moviles/api/sistema/"
    private var alumnos: ArrayList<Alumno> = ArrayList<Alumno>()
    private var alumnosAPI : ArrayList<AlumnoAPIItem> = ArrayList<AlumnoAPIItem>()
    init{
      get()

    }

    private object HOLDER {
        val INSTANCE = Alumnos()
    }

    companion object {
        val instance: Alumnos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun  addAlumno(alumno: Alumno){
        alumnos?.add(alumno)
    }

    fun getAlumno(cedula: String): Alumno? {
        for (p: Alumno in alumnos!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getAlumnos(): ArrayList<AlumnoAPIItem> {
        return this.alumnosAPI
    }


    fun deleteAlumno(position: Int){
        alumnos!!.removeAt(position)
    }

    fun editAlumnos(p: Alumno, position: Int){
        var aux = alumnos!!.get(position)
        aux.cedula = p.cedula
        aux.nombre = p.nombre
        aux.telefono = p.telefono
        aux.email = p.email
        aux.fecha = p.fecha
        aux.CarreraCodigo = p.CarreraCodigo
    }

    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerAlumno")
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
                var entidadJson = gson?.fromJson<AlumnoAPI>(valor, AlumnoAPI::class.java)
                alumnosAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}