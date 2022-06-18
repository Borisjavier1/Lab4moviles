package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Cursos {

    var client = OkHttpClient()
    var url = "http://192.168.0.2:8080/backend_moviles/api/sistema/"
    private var cursos: ArrayList<Curso> = ArrayList<Curso>()
    private var cursoAPI : ArrayList<CursoAPIItem> = ArrayList<CursoAPIItem>()
    init{

    }

    private object HOLDER {
        val INSTANCE = Cursos()
    }

    companion object {
        val instance: Cursos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun  addCurso(curso: Curso){
        cursos?.add(curso)
    }

    fun getCurso(codigo: String): Curso? {
        for (p: Curso in cursos!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getCursos(): ArrayList<CursoAPIItem>{
        get()
        return this.cursoAPI
    }

    /*fun getCursosCicloCarrera(carrera: String?, ciclo: String?):ArrayList<CursoAPIItem> {
        var grupos2: ArrayList<CursoAPIItem> = ArrayList<CursoAPIItem>()
        for( item in this.cursos ){
            if(item.carreraCodigo == carrera && item.cicloCodigo == ciclo){
                grupos2.add(item)
            }
        }
        return grupos2
    }*/



    fun deleteCurso(position: Int){
        cursos!!.removeAt(position)
    }

    fun editAlumnos(p: Curso, position: Int){
        var aux = cursos!!.get(position)
        aux.codigo = p.codigo
        aux.nombre = p.nombre
        aux.creditos = p.creditos
        aux.horas = p.horas
        aux.carreraCodigo = p.carreraCodigo
        aux.cicloCodigo = p.cicloCodigo
    }


    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerCurso")
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
                var entidadJson = gson?.fromJson<CursoAPI>(valor, CursoAPI::class.java)
                cursoAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}