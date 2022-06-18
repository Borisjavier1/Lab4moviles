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
import com.example.models.Carrera
import com.example.models.CarreraAPIItem
import com.example.models.Carreras
import com.example.models.VolleySingleton
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class CreateCareerFragment : FragmentUtils() {

    var carreras: Carreras = Carreras.instance

    lateinit var carrera: CarreraAPIItem
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_career, container, false)

        var sentPerson  = arguments?.getSerializable("carrera")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            carrera = sentPerson as CarreraAPIItem
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Carrera")
            view.findViewById<EditText>(R.id.editText_Group).setText(carrera.codigo)
            view.findViewById<EditText>(R.id.editText_est).setText(carrera.nombre)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(carrera.titulo)

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
        var editTextCode = view?.findViewById<EditText>(R.id.editText_Group)
        var editTextName = view?.findViewById<EditText>(R.id.editText_est)
        var editTextTittle = view?.findViewById<EditText>(R.id.exitText_Nota)

        var code = editTextCode?.text.toString()
        var name = editTextName?.text.toString()
        var tittle = editTextTittle?.text.toString()
        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            //carrera = Carrera(code, name, tittle)
            //carreras.addCarrera(carrera)
            //message = "Carrera Agregada"
            post(code,name,tittle)
        }
        else{
            carrera.nombre = name
            carrera.codigo = code
            carrera.titulo = tittle
            var index = carrera.position as Int
            //carreras.editCarrera(carrera, index)
            //message = "Carrera Modificada"
            put(carrera.id,code,name,tittle)
        }
       /*Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextCode?.setText("")
            editTextTittle?.setText("")

       /* }
        else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CareerFragment())
    }

    @SuppressLint("SetTextI18n")
    fun post(codigo:String, nombre:String,titulo:String) {

        val jsonObject = JSONObject()
        jsonObject.put("codigo",codigo)
        jsonObject.put("nombre",nombre)
        jsonObject.put("titulo",titulo)
        jsonObject.put("id",88)

        val request = JsonObjectRequest(
            Request.Method.POST,getString(R.string.url)+"insertarCarrera",jsonObject,
            { response->
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
                    Toast.makeText(activity, "Error al insertar.", Toast.LENGTH_LONG).show()
                }
                if(it.message?.contains("true") == true){
                    Toast.makeText(activity, "Carrera insertada.", Toast.LENGTH_LONG).show()

                }
            })

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Add the volley post request to the request queue
        VolleySingleton.getInstance(activity).addToRequestQueue(request)
    }

    @SuppressLint("SetTextI18n")
    fun put(id:Int,codigo:String, nombre:String,titulo:String) {
        val jsonObject = JSONObject()
        jsonObject.put("codigo",codigo)
        jsonObject.put("nombre",nombre)
        jsonObject.put("titulo",titulo)
        jsonObject.put("id",id)

        val request = JsonObjectRequest(Request.Method.PUT,getString(R.string.url)+"actualizarCarrera/"+id,jsonObject,
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
                    Toast.makeText(activity, "Error: El código de carrera no existe.", Toast.LENGTH_LONG).show()
                }
                if(it.message?.contains("true") == true){
                    Toast.makeText(activity, "Carrera modificada.", Toast.LENGTH_LONG).show()
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
}

