package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.Persona
import com.example.models.Personas
import com.google.android.material.snackbar.Snackbar

class CreatePersonFragment : FragmentUtils() {

    var personas: Personas = Personas.instance
    lateinit var persona: Persona
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_person, container, false)

        var sentPerson  = arguments?.getSerializable("persona")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            persona = sentPerson as Persona
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Persona")
            view.findViewById<EditText>(R.id.editText_Curso).setText(persona.user)
            view.findViewById<EditText>(R.id.editText_Cod).setText(persona.nombre)
            view.findViewById<EditText>(R.id.exitText_Number).setText(persona.password)

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
        var editTextName = view?.findViewById<EditText>(R.id.editText_Cod)
        var editTextUser = view?.findViewById<EditText>(R.id.editText_Curso)
        var editTextPassword = view?.findViewById<EditText>(R.id.exitText_Number)

        var name = editTextName?.text.toString()
        var user = editTextUser?.text.toString()
        var password = editTextPassword?.text.toString()
        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            persona = Persona(user, password, name, image)
            personas.addPersona(persona)
            message = "Persona Agregada"
        }
        else{
            persona.nombre = name
            persona.user = user
            persona.password = password
            persona.foto = image
            var index = persona.position as Int
            personas.editPerson(persona, index)
            message = "Persona Modificada"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextUser?.setText("")
            editTextPassword?.setText("")

        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Personas")
        changeFragment(PersonasFragment())
    }
}