package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.models.Grupo
import com.example.models.Grupos
import com.google.android.material.snackbar.Snackbar

class CreateGroupFragment : FragmentUtils() {

    var grupos: Grupos = Grupos.instance

    lateinit var grupo: Grupo
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_group, container, false)

        var curso  = arguments?.getString("curso")
        view.findViewById<EditText>(R.id.editText_est).setText(curso)
        var sentPerson  = arguments?.getSerializable("grupo")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            grupo = sentPerson as Grupo
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Grupo")
            view.findViewById<EditText>(R.id.editText_Group).setText(grupo.codigo)
            view.findViewById<EditText>(R.id.editText_est).setText(grupo.cursoCodigo)
            view.findViewById<EditText>(R.id.exitText_Nota).setText(grupo.numero.toString())
            view.findViewById<EditText>(R.id.exitText_horario).setText(grupo.horario)
            view.findViewById<EditText>(R.id.exitText_profesor).setText(grupo.cedulaProfesor)

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
        var editTextCourse = view?.findViewById<EditText>(R.id.editText_est)
        var editTextNumber = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextHorario = view?.findViewById<EditText>(R.id.exitText_horario)
        var editTextProfesor = view?.findViewById<EditText>(R.id.exitText_profesor)

        var code = editTextCode?.text.toString()
        var course = editTextCourse?.text.toString()
        var number = editTextNumber?.text.toString()
        var horario = editTextHorario?.text.toString()
        var profesor = editTextProfesor?.text.toString()


        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            grupo = Grupo(code, course, number.toInt(), horario,profesor)
            grupos.addGrupo(grupo)
            message = "Grupo Agregado"
        }
        else{
            grupo.codigo = code
            grupo.cursoCodigo = course
            grupo.numero = number.toInt()
            grupo.horario = horario
            grupo.cedulaProfesor = profesor
            var index = grupo.position as Int
            grupos.editGrupo(grupo, index)
            message = "Grupo Modificado"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextCourse?.setText("")
            editTextCode?.setText("")
            editTextNumber?.setText("")
            editTextHorario?.setText("")
            editTextProfesor?.setText("")

        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Oferta acádemica")
        changeFragment(OfertaFragment())
    }
}

