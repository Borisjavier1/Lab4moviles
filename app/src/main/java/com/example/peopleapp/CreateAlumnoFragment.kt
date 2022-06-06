package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.Alumno
import com.example.models.Alumnos
import com.google.android.material.snackbar.Snackbar

class CreateAlumnoFragment : FragmentUtils() {

    var alumnos: Alumnos = Alumnos.instance

    lateinit var alumno: Alumno
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
            alumno = sentPerson as Alumno
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Alumno")
            view.findViewById<EditText>(R.id.editText_Code5).setText(alumno.cedula)
            view.findViewById<EditText>(R.id.editText_Curso).setText(alumno.nombre)
            view.findViewById<EditText>(R.id.exitText_Number).setText(alumno.telefono)
            view.findViewById<EditText>(R.id.editText_fecha_i).setText(alumno.email)
            view.findViewById<EditText>(R.id.editText_fecha_f).setText(alumno.fecha)
            view.findViewById<EditText>(R.id.exitText_Career).setText(alumno.CarreraCodigo)

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
        var editTextName = view?.findViewById<EditText>(R.id.editText_Curso)
        var editTextTele = view?.findViewById<EditText>(R.id.exitText_Number)
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
            alumno = Alumno(cedula, name, tele,email,date,career)
            alumnos.addAlumno(alumno)
            message = "Alumno Agregado"
        }
        else{
            alumno.cedula = cedula
            alumno.nombre = name
            alumno.telefono = tele
            alumno.email = email
            alumno.fecha = date
            alumno.CarreraCodigo = career

            var index = alumno.position as Int
            alumnos.editAlumnos(alumno, index)
            message = "Alumno Modificado"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextCed?.setText("")
            editTextName?.setText("")
            editTextTele?.setText("")
            editTextEmail?.setText("")
            editTextDate?.setText("")
            editTextCareer?.setText("")

        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(StudentFragment())
    }
}

