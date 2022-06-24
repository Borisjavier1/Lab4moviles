package com.example.peopleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
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

class CreateAdminFragment : FragmentUtils() {

    var profesores: Profesores = Profesores.instance

    lateinit var profesor: AdministradorAPIItem
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_admin, container, false)

        var sentPerson  = arguments?.getSerializable("administrador")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            profesor = sentPerson as AdministradorAPIItem
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Profesor")
            view.findViewById<EditText>(R.id.editText_Group).setText(profesor.cedula)
            view.findViewById<EditText>(R.id.editText_est).setText(profesor.nombre)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(profesor.telefono)
            view.findViewById<EditText>(R.id.exitText_horario).setText(profesor.email)


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
        var editTextCedula = view?.findViewById<EditText>(R.id.editText_Group)
        var editTextName = view?.findViewById<EditText>(R.id.editText_est)
        var editTextTele = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextEmail = view?.findViewById<EditText>(R.id.exitText_horario)
        var editTextGuia = view?.findViewById<EditText>(R.id.exitText_profesor)


        var cedula = editTextCedula?.text.toString()
        var nombre = editTextName?.text.toString()
        var tele = editTextTele?.text.toString()
        var email = editTextEmail?.text.toString()
        var guia = editTextGuia?.text.toString()

        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            //profesor = Profesor(cedula, nombre, tele, email, guia.toInt())
            //profesores.addProfesor(profesor)
           // message = "Profesor Agregado"
           post(cedula,email,nombre,tele)
            postUsuario(cedula,cedula)
        }
        else{
            profesor.cedula = cedula
            profesor.nombre = nombre
            profesor.telefono = tele
            profesor.email =email



            var index = profesor.position as Int
            //profesores.editProfesor(profesor, index)
            message = "Profesor Modificado"
            put(profesor.id,cedula,email,nombre,tele)
        }
        /*Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){
            editTextCedula?.setText("")
            editTextName?.setText("")
            editTextTele?.setText("")
            editTextEmail?.setText("")
           editTextGuia?.setText("")


        /*}
        else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Profesores")
        changeFragment(AdministradorFragment())
    }

    @SuppressLint("SetTextI18n")
    fun post(cedula: String,email: String,nombre:String,telefono:String) {

        val jsonObject = JSONObject()
        jsonObject.put("cedula",cedula)
        jsonObject.put("email",email)
        jsonObject.put("id",99)
        jsonObject.put("nombre",nombre)
        jsonObject.put("telefono",telefono)

        val request = JsonObjectRequest(
            Request.Method.POST,getString(R.string.url)+"insertarAdministrador",jsonObject,
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
                    Toast.makeText(activity, "Administrador insertado.", Toast.LENGTH_LONG).show()

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
    fun put(id:Int, cedula: String,email: String,nombre:String,telefono:String) {
        val jsonObject = JSONObject()
        jsonObject.put("cedula",cedula)
        jsonObject.put("email",email)
        jsonObject.put("id",id)
        jsonObject.put("nombre",nombre)
        jsonObject.put("telefono",telefono)

        val request = JsonObjectRequest(Request.Method.PUT,getString(R.string.url)+"actualizarAdministrador/"+id,jsonObject,
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
                    Toast.makeText(activity, "Error: No se pudo modificar.", Toast.LENGTH_LONG).show()
                }
                if(it.message?.contains("true") == true){
                    Toast.makeText(activity, "Administrador modificado.", Toast.LENGTH_LONG).show()
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

    fun postUsuario(cedula:String,clave:String) {

        val jsonObject = JSONObject()
        jsonObject.put("cedula",cedula)
        jsonObject.put("clave",clave)
        jsonObject.put("rol",3)
        jsonObject.put("id",88)

        val request = JsonObjectRequest(
            Request.Method.POST,getString(R.string.url)+"insertarUsuario",jsonObject,
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
                    //Toast.makeText(activity, "Error: No se pudo crear usuario.", Toast.LENGTH_LONG).show()
                }
                if(it.message?.contains("true") == true){
                    // Toast.makeText(activity, "Usuario insertado.", Toast.LENGTH_LONG).show()

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
}

