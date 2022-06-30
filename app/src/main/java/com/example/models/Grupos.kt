package com.example.models

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class Grupos {
    var client = OkHttpClient()
    var url = "http://192.168.0.3:8080/backend_moviles/api/sistema/"
    private var grupos: ArrayList<Grupo> = ArrayList<Grupo>()
    private var gruposAPI : ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
    private var GrupoConsulta: ArrayList<GrupoConsulta2Item> = ArrayList<GrupoConsulta2Item>()
   private var idGlobal: Int = 0
    private var cicloActual: Int = 0
    private var flag: Int = 0
    init{

    }

    private object HOLDER {
        val INSTANCE = Grupos()
    }

    companion object {
        val instance: Grupos by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addGrupo(grupo: GrupoAPIItem){
        gruposAPI?.add(grupo)
    }

    fun getGrupo(codigo: String): Grupo? {
        for (p: Grupo in grupos!!){
            if(p.codigo.equals(codigo)){
                return p;
            }
        }
        return null;
    }

    fun getGrupos(): ArrayList<GrupoAPIItem> {
        get()
        return this.gruposAPI
    }

    fun getGruposProfesor(ced: String?): ArrayList<GrupoAPIItem> {
        /*get()
        if (ced != null) {
            getCedProf(ced)
        }
         var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
         for( item in this.gruposAPI){
             if(item.profesor == idGlobal){
                 grupos2.add(item)
             }
         }
        return grupos2*/
        getCicloActual()
        getCedProf(ced)
        getGruposProf(idGlobal,cicloActual)
        var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
        for( item in this.GrupoConsulta){
            grupos2.add(GrupoAPIItem(item.ciclo, item.curso, item.numero, item.id,item.horario, item.profesor))
        }
        return grupos2
    }
    fun getGruposCurso(Id: Int):ArrayList<GrupoAPIItem> {
        get()
        var grupos2: ArrayList<GrupoAPIItem> = ArrayList<GrupoAPIItem>()
        for( item in this.gruposAPI){
            if(item.curso == Id){
                grupos2.add(item)
            }
        }
        return grupos2
    }

    fun deleteGrupo(position: Int){
        grupos!!.removeAt(position)
    }

    fun editGrupo(p: Grupo, position: Int){
        var aux = grupos!!.get(position)
        aux.codigo = p.codigo
        aux.cursoCodigo = p.cursoCodigo
        aux.numero = p.numero
        aux.horario = p.horario
        aux.cedulaProfesor = p.cedulaProfesor
    }
    fun get() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"obtenerGrupo")
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error en grupos get"+e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                var entidadJson = gson?.fromJson<GrupoAPI>(valor, GrupoAPI::class.java)
                gruposAPI = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getCedProf(ced:String?) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarProfesorCedula/"+ced)
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error en grupos getCed"+e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                var entidadJson = gson?.fromJson<ProfesorAPIItem>(valor, ProfesorAPIItem::class.java)
                idGlobal = entidadJson.id
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getCicloActual() {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
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
                cicloActual = entidadJson.id
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getGruposProf(prof: Int?,ciclo: Int?) {
        println(prof)
        println(ciclo)
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(url+"buscarGrupoProfesor/"+prof+"/"+ciclo)
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error en grupos getProfs"+e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                if(valor?.contains("Error report") == false){
                    var entidadJson = gson?.fromJson<GrupoConsulta2>(valor, GrupoConsulta2::class.java)
                    GrupoConsulta = entidadJson
                    //println(valor)
                    countDownLatch.countDown();
                }else{
                    GrupoConsulta.clear()
                    GrupoConsulta.add(GrupoConsulta2Item(1,1,1,"",1,"El profesor no tiene grupos a cargo",0,1,1))
                    countDownLatch.countDown();
                }
                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

    fun getFlag(id: Int?): Int {
        if (id != null) {
            getValido(id)
        }
        return flag
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