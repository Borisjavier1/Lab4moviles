package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.Ciclo
import com.example.models.Ciclos
import com.google.android.material.snackbar.Snackbar

class CreateCycleFragment : FragmentUtils() {

    var ciclos: Ciclos = Ciclos.instance

    lateinit var ciclo: Ciclo
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_cycle, container, false)

        var sentPerson  = arguments?.getSerializable("ciclo")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            ciclo = sentPerson as Ciclo
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Editar Ciclo")
            view.findViewById<EditText>(R.id.editText_Code5).setText(ciclo.codigo)
            view.findViewById<EditText>(R.id.editText_est).setText(ciclo.numero.toString())
            view.findViewById<EditText>(R.id.exitText_Nota).setText(ciclo.anio.toString())
            view.findViewById<EditText>(R.id.editText_fecha_i).setText(ciclo.fechaInicio)
            view.findViewById<EditText>(R.id.editText_fecha_f).setText(ciclo.fechaFin)





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
        var editTextNumber = view?.findViewById<EditText>(R.id.editText_est)
        var editTextYear = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextFechaI = view?.findViewById<EditText>(R.id.editText_fecha_i)
        var editTextFechaF = view?.findViewById<EditText>(R.id.editText_fecha_f)

        var code = editTextCode?.text.toString()
        var number = editTextNumber?.text.toString()
        var year = editTextYear?.text.toString()
        var dateB = editTextFechaI?.text.toString()
        var dateF = editTextFechaF?.text.toString()

        var image = R.drawable.foto01

        if(tipoAgregado == 0){
            ciclo = Ciclo(code, number.toInt(), year.toInt(), dateB, dateF,0)
            ciclos.addCiclo(ciclo)
            message = "Ciclo Agregado"
        }
        else{
            ciclo.codigo = code
            ciclo.numero = number.toInt()
            ciclo.anio = year.toInt()
            ciclo.fechaInicio =dateB
            ciclo.fechaFin = dateF

            var index = ciclo.position as Int
            ciclos.editAlumnos(ciclo, index)
            message = "Ciclo Modificado"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextCode?.setText("")
            editTextNumber?.setText("")
            editTextYear?.setText("")
            editTextFechaI?.setText("")
            editTextFechaF?.setText("")


        }
        else{
            volver()
        }
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CycleFragment())
    }
}

