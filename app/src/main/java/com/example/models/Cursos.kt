package com.example.models

import com.example.peopleapp.R
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Cursos {

    var client = OkHttpClient()
    var url = "http://192.168.0.3:8080/backend_moviles/api/sistema/"
    private var cursos: ArrayList<Curso> = ArrayList<Curso>()
    private var cursoAPI : ArrayList<CursoAPIItem> = ArrayList<CursoAPIItem>()
    private var cicloGlobal: Int = 0
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

    fun getCursosCarrera(id: Int): ArrayList<CursoAPIItem>{
        getCicloActual()
        getPorCarrera(id,cicloGlobal)
        return this.cursoAPI
    }

    fun getCursosCicloCarrera(carrera: Int?, ciclo: Int?):ArrayList<CursoAPIItem> {
        get()
        var grupos2: ArrayList<CursoAPIItem> = ArrayList<CursoAPIItem>()
        for( item in this.cursoAPI ){
            if(item.carrera == carrera && item.ciclo == ciclo){
                grupos2.add(item)
            }
        }
        return grupos2
    }



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



    fun getPorCarrera(carrera: Int, ciclo: Int) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        println( "carrera"+carrera)
        println( "ciclo"+ciclo)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarCursoPorCarreraCiclo/"+carrera+"/"+ciclo)
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
                if(valor?.contains("Error report") == false){
                    var entidadJson = gson?.fromJson<CursoAPI>(valor, CursoAPI::class.java)
                    cursoAPI = entidadJson
                    countDownLatch.countDown();
                }else{
                    cursoAPI.clear()
                    cursoAPI.add(CursoAPIItem(1,1,"No hay cursos en esta carrera",1,1,1,"No hay cursos en esta carrera"))
                    countDownLatch.countDown();
                }

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getCicloActual() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = okhttp3.Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerCicloActivo/")
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error en grupos getciclo"+e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                var entidadJson = gson?.fromJson<CicloAPIItem>(valor, CicloAPIItem::class.java)
                cicloGlobal = entidadJson.id
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}