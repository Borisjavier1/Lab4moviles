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
import com.example.models.Curso
import com.example.models.CursoAPIItem
import com.example.models.Cursos
import com.example.models.VolleySingleton
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class CreateCourseFragment : FragmentUtils() {

    var cursos: Cursos = Cursos.instance

    lateinit var curso: CursoAPIItem
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_course, container, false)

        var sentPerson  = arguments?.getSerializable("curso")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            curso = sentPerson as CursoAPIItem
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Curso")
            view.findViewById<EditText>(R.id.editText_Code5).setText(curso.codigo)
            view.findViewById<EditText>(R.id.editText_est).setText(curso.nombre)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(curso.creditos.toString())
            view.findViewById<EditText>(R.id.editText_fecha_i).setText(curso.horas_sem.toString())
            view.findViewById<EditText>(R.id.editText_fecha_f).setText(curso.carrera.toString())
            view.findViewById<EditText>(R.id.exitText_Career).setText(curso.ciclo.toString())


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
        var editTextName = view?.findViewById<EditText>(R.id.editText_est)
        var editTextCredits = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextHours = view?.findViewById<EditText>(R.id.editText_fecha_i)
        var editTextCareer = view?.findViewById<EditText>(R.id.editText_fecha_f)
        var editTextCycle = view?.findViewById<EditText>(R.id.exitText_Career)

        var code = editTextCode?.text.toString()
        var name = editTextName?.text.toString()
        var credits = editTextCredits?.text.toString()
        var hours = editTextHours?.text.toString()
        var carrer = editTextCareer?.text.toString()
        var cycle = editTextCycle?.text.toString()

        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            //curso = Curso(code, name, credits.toInt(), hours.toInt(), carrer, cycle)
            //cursos.addCurso(curso)
            //message = "Curso Agregado"
            post(code,name,credits.toInt(),hours.toInt(),carrer.toInt(),cycle.toInt())
        }
        else{
            curso.nombre = name
            curso.codigo = code
            curso.creditos = credits.toInt()
            curso.horas_sem = hours.toInt()
            curso.carrera = carrer.toInt()
            curso.ciclo = cycle.toInt()
            var index = curso.position as Int
            //cursos.editAlumnos(curso, index)
            //message = "Curso Modificado"
            put(curso.id,code,name,credits.toInt(),hours.toInt(),carrer.toInt(),cycle.toInt())
        }
        /*Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()*/

        //if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextCode?.setText("")
            editTextCredits?.setText("")
            editTextHours?.setText("")
            editTextCareer?.setText("")
            editTextCycle?.setText("")

        //}
        /*else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CourseFragment())
    }

    @SuppressLint("SetTextI18n")
    fun post(code:String,name:String,credits:Int,hours:Int,carrer:Int,ciclo:Int) {

        val jsonObject = JSONObject()
        jsonObject.put("codigo",code)
        jsonObject.put("nombre",name)
        jsonObject.put("creditos",credits)
        jsonObject.put("horas",hours)
        jsonObject.put("carrera",carrer)
        jsonObject.put("ciclo",ciclo)
        jsonObject.put("id",88)

        val request = JsonObjectRequest(
            Request.Method.POST,getString(R.string.url)+"insertarCurso",jsonObject,
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
    fun put(id:Int, code:String,name:String,credits:Int,hours:Int,carrer:Int,ciclo:Int) {
        val jsonObject = JSONObject()
        jsonObject.put("codigo",code)
        jsonObject.put("nombre",name)
        jsonObject.put("creditos",credits)
        jsonObject.put("horas",hours)
        jsonObject.put("carrera",carrer)
        jsonObject.put("ciclo",ciclo)
        jsonObject.put("id",id)

        val request = JsonObjectRequest(Request.Method.PUT,getString(R.string.url)+"actualizarCurso/"+id,jsonObject,
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

