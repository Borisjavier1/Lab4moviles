package com.example.peopleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.models.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch

class CreateMatriculaFragment : FragmentUtils() {

    var matriculas: Matriculas = Matriculas.instance
    var client = OkHttpClient()
    lateinit var estudianteNota: EstudianteNota
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar
    lateinit var historial: Historial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_matricula, container, false)

        var sentPerson  = arguments?.getSerializable("estudianteNota")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            estudianteNota = sentPerson as EstudianteNota
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Matricula")
            view.findViewById<EditText>(R.id.editText_Group).setText(estudianteNota.grupo.toString())
            view.findViewById<EditText>(R.id.editText_est).setText(estudianteNota.cedula)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(estudianteNota.nota.toString())
            view.findViewById<EditText>(R.id.exitText_Estado2).setText(estudianteNota.nombre)

        }

        view.findViewById<Button>(R.id.btn_guardarPersona).setOnClickListener{
            createPerson()
        }

        view.findViewById<Button>(R.id.btn_volver).setOnClickListener {
            volver()
        }

        return view
    }

    private fun createPerson(){
        var message:String? = null
        var editTextGroup = view?.findViewById<EditText>(R.id.editText_Group)
        var editTextNota = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextest = view?.findViewById<EditText>(R.id.editText_est)
        var editTextNom = view?.findViewById<EditText>(R.id.exitText_Estado2)

        var group = editTextGroup?.text.toString()
        var cedula = editTextest?.text.toString()
        var nota = editTextNota?.text.toString()
        var nombre = editTextNom?.text.toString()

        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            //estudianteNota= EstudianteNota(group, cedula, nota.toInt(), estado, ciclo)
            //matriculas.addMatricula(matricula)
            //message = "Matricula Agregada"
        }
        else{
            estudianteNota.grupo = group.toInt()
            estudianteNota.cedula = cedula
            estudianteNota.nota = nota.toInt()
            estudianteNota.nombre = nombre
            var index = estudianteNota.position as Int
            //matriculas.editMatricula(matricula, index)
            //message = "Matricula Modificada"
            getHistorial(estudianteNota.id_historial)
            putNota(nota.toInt())
        }
        /*Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){

            editTextNota?.setText("")


        /*}
        else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Matricula")
        changeFragment(HomeFragment())
    }

    @SuppressLint("SetTextI18n")
    fun putNota(nota: Int) {
     println("nota "+nota+" id "+id)
        val jsonObject = JSONObject()
        jsonObject.put("anio_ciclo",historial.anio_ciclo)
        jsonObject.put("ciclo",historial.ciclo)
        jsonObject.put("curso",historial.curso)
        jsonObject.put("estado",historial.estado)
        jsonObject.put("grupo",historial.grupo)
        jsonObject.put("id",historial.id)
        jsonObject.put("nota",nota)

        val request = JsonObjectRequest(
            Request.Method.PUT,getString(R.string.url)+"actualizarHistorial/"+historial.id,jsonObject,
            { response ->
                // Process the json
                try {
                    // etCord.setText("Response: $response")
                    println(response)
                }catch (e:Exception){
                    //etClima.setText("Exception: $e")
                    println(e)
                }

            }, {
                // Error in request
                //  etHumedad.setText("Volley error: $it")
                println("Error request:"+it)
                if(it.message?.contains("false") == true){
                    Toast.makeText(activity, "Error: No se pudo actualizar la nota.", Toast.LENGTH_LONG).show()
                }
                if(it.message?.contains("true") == true){
                    Toast.makeText(activity, "Nota actualizada.", Toast.LENGTH_LONG).show()
                }
            })

        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Add the volley post request to the request queue
        VolleySingleton.getInstance(activity).addToRequestQueue(request)
    }

    fun getHistorial(id:Int) {

        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = okhttp3.Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(getString(R.string.url)+"buscarHistorialId/"+id)
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
                var entidadJson = gson?.fromJson<Historial>(valor, Historial::class.java)
                historial = entidadJson
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

}

