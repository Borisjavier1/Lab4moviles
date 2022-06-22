package com.example.models

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Matriculas private constructor() {
    var client = OkHttpClient()
    private var matriculas: ArrayList<Matricula> = ArrayList<Matricula>()
    var url = "http://192.168.0.102:8080/backend_moviles/api/sistema/"
    private var matriculasAPI : ArrayList<MatriculaAPIItem> = ArrayList<MatriculaAPIItem>()
    private var grupoConsultas : ArrayList<GrupoConsultaItem> = ArrayList<GrupoConsultaItem>()
    private var idEst : Int = 0
    init{

    }

    private object HOLDER {
        val INSTANCE = Matriculas()
    }

    companion object {
        val instance: Matriculas by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addMatricula(matricula: Matricula){
        matriculas?.add(matricula)
    }



    fun getMatriculas(): ArrayList<MatriculaAPIItem>{
        return this.matriculasAPI
    }

    fun getMatriculasGroup(grupo: Int?): ArrayList<MatriculaAPIItem>{
        var grupos2: ArrayList<MatriculaAPIItem> = ArrayList<MatriculaAPIItem>()
        for( item in this.matriculasAPI){
            if(item.id_grupo == grupo){
                grupos2.add(item)
            }
        }
        return grupos2
    }

    fun getMatriculasStudent(estudiante: String?): ArrayList<MatriculaAPIItem>{
        get()
        if (estudiante != null) {
            getId(estudiante)
        }
        var grupos2: ArrayList<MatriculaAPIItem> = ArrayList<MatriculaAPIItem>()
        for( item in this.matriculasAPI){
            if(item.id_alumno == idEst){
                grupos2.add(item)
            }
        }
        return grupos2
    }

    fun getMatriculasStudentCiclo(estudiante: Int?, ciclo: Int?): ArrayList<MatriculaAPIItem> {
        /*get()

        var grupos2: ArrayList<MatriculaAPIItem> = ArrayList<MatriculaAPIItem>()

        for( item in this.matriculasAPI){
            if(item.id_alumno == estudiante && item.id_ciclo == ciclo){
                grupos2.add(item)
            }

        }
        //println(grupos2)
        return grupos2*/
        getGrupos(estudiante,ciclo)
        var grupos2: ArrayList<MatriculaAPIItem> = ArrayList<MatriculaAPIItem>()
        for (item in this.grupoConsultas) {
            println(item.id)
            grupos2.add(MatriculaAPIItem(item.id_matricula, item.estudiante_id, item.curso, item.id, item.ciclo))
        }
        return grupos2
    }

    fun deleteMatricula(position: Int){
        matriculas!!.removeAt(position)
    }

    fun editMatricula(p: Matricula, position: Int){
        var aux = matriculas!!.get(position)
        //aux.cedEstudiante = p.cedEstudiante
       // aux.codGrupo = p.codGrupo
        aux.nota = p.nota
        aux.estado = p.estado
       // aux.codCiclo = p.codCiclo
    }

    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerMatricula")
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
                var entidadJson = gson?.fromJson<MatriculaAPI>(valor, MatriculaAPI::class.java)
                matriculasAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getGrupos(estudiante: Int?, ciclo: Int?) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarGrupoCicloAlumno/"+estudiante+"/"+ciclo)
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
                var entidadJson = gson?.fromJson<GrupoConsulta>(valor, GrupoConsulta::class.java)
                grupoConsultas = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getId(ced:String) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarAlumnoCedula/"+ced)
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
                var entidadJson = gson?.fromJson<AlumnoAPIItem>(valor, AlumnoAPIItem::class.java)
                idEst = entidadJson.id
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}