package com.example.models

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class EstudiantesNotas private constructor() {
    var client = OkHttpClient()
    private var matriculas: ArrayList<EstudianteNota> = ArrayList<EstudianteNota>()
    var url = "http://192.168.0.9:8080/backend_moviles/api/sistema/"
    private var EstudianteNotas : ArrayList<EstudianteNota> = ArrayList<EstudianteNota>()
    private var grupoConsultas : ArrayList<GrupoConsultaItem> = ArrayList<GrupoConsultaItem>()
    private var idEst : Int = 0
    private var flag : Int = 0
    init{

    }

    private object HOLDER {
        val INSTANCE = EstudiantesNotas()
    }

    companion object {
        val instance: EstudiantesNotas by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addMatricula(EstudianteNota: EstudianteNota){
        matriculas?.add(EstudianteNota)
    }
    fun getFlag(id: Int?): Int {
        if (id != null) {
            getValido(id)
        }
        return flag
    }


    fun getMatriculas(id: Int?): ArrayList<EstudianteNota> {
        if (id != null) {
            get(id)
        }
        println("size"+EstudianteNotas.size.toString())
        return this.EstudianteNotas
    }


    fun get(id:Int) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarNotaGrupo/"+id)
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
                    var entidadJson = gson?.fromJson<EstudianteNotaAPI>(valor, EstudianteNotaAPI::class.java)
                    EstudianteNotas = entidadJson
                    countDownLatch.countDown();
                }else{
                    EstudianteNotas.clear()
                    EstudianteNotas.add(EstudianteNota("EL grupo no tiene estudiantes",0,0,0,"d",0))
                    countDownLatch.countDown();
                }

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getValido(id:Int) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarNotaGrupo/"+id)
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                if(valor?.contains("Error report") == true){
                   flag = 1
                }

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

}