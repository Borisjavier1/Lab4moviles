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
import org.json.JSONObject

class CreateAlumnoFragment : FragmentUtils() {
    var url = "http://192.168.0.2:8080/backend_moviles/api/sistema/"
    var alumnos: Alumnos = Alumnos.instance

    lateinit var alumno: AlumnoAPIItem
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_alumno, container, false)

        var sentPerson  = arguments?.getSerializable("alumno")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            alumno = sentPerson as AlumnoAPIItem
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Alumno")
            view.findViewById<EditText>(R.id.editText_Code5).setText(alumno.cedula)
            view.findViewById<EditText>(R.id.editText_est).setText(alumno.nombre)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(alumno.telefono)
            view.findViewById<EditText>(R.id.editText_fecha_i).setText(alumno.email)
            view.findViewById<EditText>(R.id.editText_fecha_f).setText(alumno.fecha_nac)
            view.findViewById<EditText>(R.id.exitText_Career).setText(alumno.carrera)

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
        var editTextCed = view?.findViewById<EditText>(R.id.editText_Code5)
        var editTextName = view?.findViewById<EditText>(R.id.editText_est)
        var editTextTele = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextEmail = view?.findViewById<EditText>(R.id.editText_fecha_i)
        var editTextDate = view?.findViewById<EditText>(R.id.editText_fecha_f)
        var editTextCareer = view?.findViewById<EditText>(R.id.exitText_Career)

        var cedula = editTextCed?.text.toString()
        var name = editTextName?.text.toString()
        var tele = editTextTele?.text.toString()
        var email = editTextEmail?.text.toString()
        var date = editTextDate?.text.toString()
        var career = editTextCareer?.text.toString()
        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            //alumno = Alumno(cedula, name, tele,email,date,career)
            //alumnos.addAlumno(alumno)
            message = "Alumno Agregado"
            post(career,cedula,email,date,name,tele)

        }
        else{
            alumno.cedula = cedula
            alumno.nombre = name
            alumno.telefono = tele
            alumno.email = email
            alumno.fecha_nac = date
            alumno.carrera = career

            var index = alumno.position as Int
           // alumnos.editAlumnos(alumno, index)
            //message = "Alumno Modificado"
            put(alumno.id,career,cedula,email,date,name,tele)
        }
        /*Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextCed?.setText("")
            editTextName?.setText("")
            editTextTele?.setText("")
            editTextEmail?.setText("")
            editTextDate?.setText("")
            editTextCareer?.setText("")

       /* }
        else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(StudentFragment())
    }

    @SuppressLint("SetTextI18n")
    fun post(carrera:String,cedula:String,email:String,fecha_nac:String,nombre:String,telefono:String) {

        val jsonObject = JSONObject()
        jsonObject.put("carrera",carrera)
        jsonObject.put("cedula",cedula)
        jsonObject.put("email",email)
        jsonObject.put("fecha_nac",fecha_nac)
        jsonObject.put("id",88)
        jsonObject.put("nombre",nombre)
        jsonObject.put("telefono",telefono)

        val request = JsonObjectRequest(
            Request.Method.POST,url+"insertarAlumno",jsonObject,
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
                    Toast.makeText(activity, "Error: El código de carrera no existe.", Toast.LENGTH_LONG).show()
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
    fun put(id:Int, carrera:String,cedula:String,email:String,fecha_nac:String,nombre:String,telefono:String) {

        val jsonObject = JSONObject()
        jsonObject.put("carrera",carrera)
        jsonObject.put("cedula",cedula)
        jsonObject.put("email",email)
        jsonObject.put("fecha_nac",fecha_nac)
        jsonObject.put("id",id)
        jsonObject.put("nombre",nombre)
        jsonObject.put("telefono",telefono)

        val request = JsonObjectRequest(Request.Method.PUT,url+"actualizarAlumno/"+id,jsonObject,
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

