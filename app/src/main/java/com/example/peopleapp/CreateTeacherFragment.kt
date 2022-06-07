package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.Profesor
import com.example.models.Profesores
import com.google.android.material.snackbar.Snackbar

class CreateTeacherFragment : FragmentUtils() {

    var profesores: Profesores = Profesores.instance

    lateinit var profesor: Profesor
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_teacher, container, false)

        var sentPerson  = arguments?.getSerializable("profesor")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            profesor = sentPerson as Profesor
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Profesor")
            view.findViewById<EditText>(R.id.editText_Group).setText(profesor.cedula)
            view.findViewById<EditText>(R.id.editText_est).setText(profesor.nombre)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(profesor.telefono)
            view.findViewById<EditText>(R.id.exitText_horario).setText(profesor.email)
            view.findViewById<EditText>(R.id.exitText_profesor).setText(profesor.guia.toString())





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
            profesor = Profesor(cedula, nombre, tele, email, guia.toInt())
            profesores.addProfesor(profesor)
            message = "Profesor Agregado"
        }
        else{
            profesor.cedula = cedula
            profesor.nombre = nombre
            profesor.telefono = tele
            profesor.email =email
            profesor.guia = guia.toInt()


            var index = profesor.position as Int
            profesores.editProfesor(profesor, index)
            message = "Profesor Modificado"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextCedula?.setText("")
            editTextName?.setText("")
            editTextTele?.setText("")
            editTextEmail?.setText("")
           editTextGuia?.setText("")


        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Profesores")
        changeFragment(ProfessorFragment())
    }
}

