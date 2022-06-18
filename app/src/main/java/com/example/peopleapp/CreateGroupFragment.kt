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

class CreateGroupFragment : FragmentUtils() {

    var grupos: Grupos = Grupos.instance

    lateinit var grupo: GrupoAPIItem
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_group, container, false)

        var curso  = arguments?.getSerializable("curso") as CursoAPIItem
        view.findViewById<EditText>(R.id.editText_est).setText(curso.id.toString())
        var sentPerson  = arguments?.getSerializable("grupo")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            grupo = sentPerson as GrupoAPIItem
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Grupo")
            //view.findViewById<EditText>(R.id.editText_Group).setText(grupo.id)
            view.findViewById<EditText>(R.id.editText_est).setText(grupo.curso.toString())
            view.findViewById<EditText>(R.id.exitText_Nota).setText(grupo.numero.toString())
            view.findViewById<EditText>(R.id.exitText_horario).setText(grupo.horario)
            view.findViewById<EditText>(R.id.exitText_profesor).setText(grupo.profesor.toString())

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
        //var editTextId = view?.findViewById<EditText>(R.id.editText_Group)
        var editTextCourse = view?.findViewById<EditText>(R.id.editText_est)
        var editTextNumber = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextHorario = view?.findViewById<EditText>(R.id.exitText_horario)
        var editTextProfesor = view?.findViewById<EditText>(R.id.exitText_profesor)

        //var code = editTextId?.text.toString()
        var course = editTextCourse?.text.toString()
        var number = editTextNumber?.text.toString()
        var horario = editTextHorario?.text.toString()
        var profesor = editTextProfesor?.text.toString()


        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            var curso  = arguments?.getSerializable("curso") as CursoAPIItem
             post(curso.ciclo,curso.id,horario,number.toInt(),profesor.toInt())
            //grupos.addGrupo(grupo)
            //message = "Grupo Agregado"
        }
        else{
            var curso  = arguments?.getSerializable("curso") as CursoAPIItem
            //grupo.id = code.toInt()
            grupo.curso = course.toInt()
            grupo.numero = number.toInt()
            grupo.horario = horario
            grupo.profesor = profesor.toInt()
            var index = grupo.position as Int
            //grupos.editGrupo(grupo, index)
            //message = "Grupo Modificado"
            put(grupo.id,curso.ciclo,curso.id,horario,number.toInt(),profesor.toInt())
        }
        /*Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){
            editTextCourse?.setText("")
            //editTextId?.setText("")
            editTextNumber?.setText("")
            editTextHorario?.setText("")
            editTextProfesor?.setText("")

        /*}
        else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Oferta acádemica")
        changeFragment(OfertaFragment())
    }

    @SuppressLint("SetTextI18n")
    fun post(ciclo:Int,curso:Int,horario:String,numero:Int,profesor:Int) {

        val jsonObject = JSONObject()
        jsonObject.put("ciclo",ciclo)
        jsonObject.put("curso",curso)
        jsonObject.put("horario",horario)
        jsonObject.put("numero",numero)
        jsonObject.put("profesor",profesor)
        jsonObject.put("id",88)

        val request = JsonObjectRequest(
            Request.Method.POST,getString(R.string.url)+"insertarGrupo",jsonObject,
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
                    Toast.makeText(activity, "Curso insertado.", Toast.LENGTH_LONG).show()

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
    fun put(id: Int, ciclo:Int,curso:Int,horario:String,numero:Int,profesor:Int) {
        val jsonObject = JSONObject()
        jsonObject.put("ciclo",ciclo)
        jsonObject.put("curso",curso)
        jsonObject.put("horario",horario)
        jsonObject.put("numero",numero)
        jsonObject.put("profesor",profesor)
        jsonObject.put("id",id)

        val request = JsonObjectRequest(Request.Method.PUT,getString(R.string.url)+"actualizarGrupo/"+id,jsonObject,
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

