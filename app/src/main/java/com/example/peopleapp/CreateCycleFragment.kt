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
import com.example.models.Ciclo
import com.example.models.CicloAPIItem
import com.example.models.Ciclos
import com.example.models.VolleySingleton
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class CreateCycleFragment : FragmentUtils() {

    var ciclos: Ciclos = Ciclos.instance

    lateinit var ciclo: CicloAPIItem
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_cycle, container, false)

        var sentPerson  = arguments?.getSerializable("ciclo")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            ciclo = sentPerson as CicloAPIItem
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Ciclo")
            view.findViewById<EditText>(R.id.editText_Code5).setText(ciclo.actual.toString())
            view.findViewById<EditText>(R.id.editText_est).setText(ciclo.numero.toString())
            view.findViewById<EditText>(R.id.exitText_Nota).setText(ciclo.anio.toString())
            view.findViewById<EditText>(R.id.editText_fecha_i).setText(ciclo.fecha_inicio)
            view.findViewById<EditText>(R.id.editText_fecha_f).setText(ciclo.fecha_fin)


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
        var editTextCode = view?.findViewById<EditText>(R.id.editText_Code5)
        var editTextNumber = view?.findViewById<EditText>(R.id.editText_est)
        var editTextYear = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextFechaI = view?.findViewById<EditText>(R.id.editText_fecha_i)
        var editTextFechaF = view?.findViewById<EditText>(R.id.editText_fecha_f)

        var code = editTextCode?.text.toString()
        var number = editTextNumber?.text.toString()
        var year = editTextYear?.text.toString()
        var dateB = editTextFechaI?.text.toString()
        var dateF = editTextFechaF?.text.toString()

        var image = R.drawable.foto01

        if(tipoAgregado == 0){
           // ciclo = Ciclo(code, number.toInt(), year.toInt(), dateB, dateF,0)
            //ciclos.addCiclo(ciclo)
            //message = "Ciclo Agregado"
          post(code,year.toInt(),dateF,dateB,number)
        }
        else{
            ciclo.actual = code.toInt()
            ciclo.numero = number.toInt()
            ciclo.anio = year.toInt()
            ciclo.fecha_inicio =dateB
            ciclo.fecha_fin = dateF

            var index = ciclo.position as Int
            //ciclos.editAlumnos(ciclo, index)
            //message = "Ciclo Modificado"
            put(ciclo.id, code,year.toInt(),dateF,dateB,number)
        }
       /* Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){
            editTextCode?.setText("")
            editTextNumber?.setText("")
            editTextYear?.setText("")
            editTextFechaI?.setText("")
            editTextFechaF?.setText("")


        //}
        /*else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CycleFragment())
    }
    @SuppressLint("SetTextI18n")
    fun post(actual:String, anio:Int,fecha_fin:String,fecha_inicio:String,numero:String) {

        val jsonObject = JSONObject()
        jsonObject.put("actual",actual)
        jsonObject.put("id",88)
        jsonObject.put("anio",anio)
        jsonObject.put("fecha_fin",fecha_fin)
        jsonObject.put("fecha_inicio",fecha_inicio)
        jsonObject.put("numero",numero)

        val request = JsonObjectRequest(
            Request.Method.POST,getString(R.string.url)+"insertarCiclo",jsonObject,
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
                    Toast.makeText(activity, "Error: Debe desactivar el ciclo actual.", Toast.LENGTH_LONG).show()
                }
                if(it.message?.contains("true") == true){
                    Toast.makeText(activity, "Ciclo insertado.", Toast.LENGTH_LONG).show()

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
    fun put(id:Int,actual:String, anio:Int,fecha_fin:String,fecha_inicio:String,numero:String) {
        val jsonObject = JSONObject()
        jsonObject.put("actual",actual)
        jsonObject.put("id",id)
        jsonObject.put("anio",anio)
        jsonObject.put("fecha_fin",fecha_fin)
        jsonObject.put("fecha_inicio",fecha_inicio)
        jsonObject.put("numero",numero)

        val request = JsonObjectRequest(Request.Method.PUT,getString(R.string.url)+"actualizarCiclo/"+id,jsonObject,
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
                    Toast.makeText(activity, "Error: Debe desactivar el ciclo actual.", Toast.LENGTH_LONG).show()
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

