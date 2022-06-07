package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.Curso
import com.example.models.Cursos
import com.google.android.material.snackbar.Snackbar

class CreateCourseFragment : FragmentUtils() {

    var cursos: Cursos = Cursos.instance

    lateinit var curso: Curso
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
            curso = sentPerson as Curso
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Curso")
            view.findViewById<EditText>(R.id.editText_Code5).setText(curso.codigo)
            view.findViewById<EditText>(R.id.editText_est).setText(curso.nombre)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(curso.creditos.toString())
            view.findViewById<EditText>(R.id.editText_fecha_i).setText(curso.horas.toString())
            view.findViewById<EditText>(R.id.editText_fecha_f).setText(curso.carreraCodigo)
            view.findViewById<EditText>(R.id.exitText_Career).setText(curso.cicloCodigo)




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
            curso = Curso(code, name, credits.toInt(), hours.toInt(), carrer, cycle)
            cursos.addCurso(curso)
            message = "Curso Agregado"
        }
        else{
            curso.nombre = name
            curso.codigo = code
            curso.creditos = credits.toInt()
            curso.horas = hours.toInt()
            curso.carreraCodigo = carrer
            curso.cicloCodigo = cycle
            var index = curso.position as Int
            cursos.editAlumnos(curso, index)
            message = "Curso Modificado"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextCode?.setText("")
            editTextCredits?.setText("")
            editTextHours?.setText("")
            editTextCareer?.setText("")
            editTextCycle?.setText("")

        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CourseFragment())
    }
}

