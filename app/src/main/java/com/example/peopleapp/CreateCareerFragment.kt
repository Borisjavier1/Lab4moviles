package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.Carrera
import com.example.models.Carreras
import com.google.android.material.snackbar.Snackbar

class CreateCareerFragment : FragmentUtils() {

    var carreras: Carreras = Carreras.instance

    lateinit var carrera: Carrera
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_career, container, false)

        var sentPerson  = arguments?.getSerializable("carrera")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            carrera = sentPerson as Carrera
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Carrera")
            view.findViewById<EditText>(R.id.editText_Code).setText(carrera.codigo)
            view.findViewById<EditText>(R.id.editText_Name).setText(carrera.nombre)
            view.findViewById<EditText>(R.id.exitText_Credits).setText(carrera.titulo)

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
        var editTextCode = view?.findViewById<EditText>(R.id.editText_Code)
        var editTextName = view?.findViewById<EditText>(R.id.editText_Name)
        var editTextTittle = view?.findViewById<EditText>(R.id.exitText_Credits)

        var code = editTextCode?.text.toString()
        var name = editTextName?.text.toString()
        var tittle = editTextTittle?.text.toString()
        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            carrera = Carrera(code, name, tittle)
            carreras.addCarrera(carrera)
            message = "Carrera Agregada"
        }
        else{
            carrera.nombre = name
            carrera.codigo = code
            carrera.titulo = tittle
            var index = carrera.position as Int
            carreras.editCarrera(carrera, index)
            message = "Carrera Modificada"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextName?.setText("")
            editTextCode?.setText("")
            editTextTittle?.setText("")

        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CareerFragment())
    }
}

